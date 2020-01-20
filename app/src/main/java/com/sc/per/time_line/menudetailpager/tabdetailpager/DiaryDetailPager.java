package com.sc.per.time_line.menudetailpager.tabdetailpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.refreshlisview.RefreshListView;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.extras.SoundPullEventListener;
import com.sc.per.time_line.R;
import com.sc.per.time_line.adapter.MyTabDetailImagePagerAdapter;
import com.sc.per.time_line.base.MenuDetailBasePager;
import com.sc.per.time_line.entity.Article;
import com.sc.per.time_line.entity.Menu;
import com.sc.per.time_line.utils.CacheUtils;
import com.sc.per.time_line.utils.Constants;
import com.sc.per.time_line.view.GlideRoundTransform;
import com.sc.per.time_line.view.HorizontalScrollViewPager;

import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


/**
 * 页签详情页面
 */
public class DiaryDetailPager extends MenuDetailBasePager {

    private HorizontalScrollViewPager viewpager;
    private TextView tv_title;
    private LinearLayout ll_point_group;
    private ListView list_item;

    private Menu menu;

    private DiaryDetailListAdapter adapter;

    private List<Article.DataBean.ListBean> list;
    private List<Article.DataBean.ListBean> imageList;

    //之前点
    private int prePosition;

    //xutils请求图片为null，设置默认图片
    private ImageOptions imageOptions;
    //加载更多
    private boolean isLoadMore = false;
    private PullToRefreshListView mPullRefreshListView;
    /**
     * 是否下拉刷新
     */
    private boolean isDownLoad = false;

    public DiaryDetailPager(Context context, Menu menu) {
        super(context);
        this.menu = menu;
        imageOptions = new ImageOptions.Builder()
                .setSize(DensityUtil.dip2px(200), DensityUtil.dip2px(200))
                .setRadius(DensityUtil.dip2px(5))
                .setCrop(true)
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setLoadingDrawableId(R.drawable.guide1)
                .setFailureDrawableId(R.drawable.guide_2)
                .build();
    }

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.diary_detail_pager, null);
        mPullRefreshListView =  view.findViewById(R.id.pull_refresh_list);
        //获取listview
        list_item = mPullRefreshListView.getRefreshableView();

        /**
         * 添加刷新音效
         */
        SoundPullEventListener<ListView> soundListener = new SoundPullEventListener<ListView>(context);
        soundListener.addSoundEvent(PullToRefreshBase.State.PULL_TO_REFRESH, R.raw.pull_event);
        soundListener.addSoundEvent(PullToRefreshBase.State.RESET, R.raw.reset_sound);
        soundListener.addSoundEvent(PullToRefreshBase.State.REFRESHING, R.raw.refreshing_sound);
        mPullRefreshListView.setOnPullEventListener(soundListener);

        View topPager = View.inflate(context, R.layout.tab_top_detail_pager, null);
        viewpager = topPager.findViewById(R.id.viewpager1);
        tv_title = topPager.findViewById(R.id.tv_title);
        ll_point_group = topPager.findViewById(R.id.ll_point_group);

        //把顶部轮播图，以头的方式添加到listView中
        list_item.addHeaderView(topPager);
        //设置监听刷新，上拉，下拉
        mPullRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //1.下拉刷新
                String pageNum = CacheUtils.getString(context,menu.getUrl()+"_list_page_num");
                getMenuDetailByMenuUrl(menu.getUrl(),pageNum);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //1.加载更多，上拉
                String pageNum = CacheUtils.getString(context,menu.getUrl()+"_list_page_num");
                getMoreDateForUrl(menu.getUrl(),pageNum);
            }
        });
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        String saveJson = CacheUtils.getString(context, menu.getUrl());
        if (!TextUtils.isEmpty(saveJson)){
            processData(saveJson,menu.getUrl());
        }

        String pageNum = CacheUtils.getString(context,menu.getUrl()+"_list_page_num");
        if ( pageNum != "0"){
            getMenuDetailByMenuUrl(menu.getUrl(),pageNum);
        }
    }


    /**
     * 加载更多
     * @param url 请求地址
     * @param pageNum 下一页
     */
    private void getMoreDateForUrl(final String url, String pageNum) {
        RequestParams params = new RequestParams(Constants.TIME_LINE_MENU_INFO_URL);
        params.setConnectTimeout(5000);
        params.addBodyParameter("index",url);
        params.addBodyParameter("pageNum",pageNum);
        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                //1.缓存菜单数据
                //联网请求数据
                isLoadMore = true;
                //解析数据
                processData(result,url);
                //隐藏加载更多刷新控件
                mPullRefreshListView.onRefreshComplete();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                //隐藏加载更多刷新控件
                mPullRefreshListView.onRefreshComplete();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }


    /**
     * 下拉刷新
     * @param url 请求地址
     * @param pageNum 下一页
     */
    private void getMenuDetailByMenuUrl(final String url,String pageNum){

        if (pageNum.equals("0")){
            //隐藏下拉刷新控件，更新时间
            mPullRefreshListView.onRefreshComplete();
            return;
        }

        RequestParams params = new RequestParams(Constants.TIME_LINE_MENU_INFO_URL);
        params.setConnectTimeout(5000);
        params.addBodyParameter("index",url);
        params.addBodyParameter("pageNum",pageNum);
        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                //1.缓存菜单数据
                CacheUtils.putString(context,url,result);
                isDownLoad = true;//下拉刷新
                //联网请求数据
                processData(result,url);
                //隐藏下拉刷新控件，更新时间，重写数据
                mPullRefreshListView.onRefreshComplete();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                //隐藏下拉刷新控件，更新时间
                mPullRefreshListView.onRefreshComplete();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 下拉刷新加载
     * @param json
     * @param url
     */
    private void processData(String json,String url) {
        Gson gson = new Gson();
        Article article = gson.fromJson(json, Article.class);

        int nextPage = article.getData().getNextPage();
        //将下一页数据放到缓存
        CacheUtils.putString(context,url+"_list_page_num",nextPage+"");

        //默认和加载更多
        if (!isLoadMore){
            //默认
            List<Article.DataBean.ListBean> downList = article.getData().getList();
            if (list != null){
                list.addAll(downList);
            }else{
                list = article.getData().getList();
            }
            imageList = new ArrayList<>();
            //过多轮播，只显示三个
            if (list.size() >= 3 ){
                for (int i = 0; i < 3 ; i++) {
                    Article.DataBean.ListBean listBean = new Article.DataBean.ListBean();
                    String imageUrl = listBean.getThematicUrl();
                    String title = listBean.getTitle();
                    listBean.setThematicUrl(imageUrl);
                    listBean.setTitle(title);
                    imageList.add(listBean);
                }
            }else {
                imageList = list;
            }
            //顶部轮播图适配器
            viewpager.setAdapter(new MyTabDetailImagePagerAdapter(context,imageList));
            //添加红点
            addPoint();
            //监听页面的改变，设置红点变化和文本变化
            viewpager.addOnPageChangeListener(new MyTabDetailTopOnPagerChangeListener());
            //初始化轮播图顶部数据
            if (!list.isEmpty()){
                tv_title.setText(list.get(0).getTitle());
            }
            if(isDownLoad){
                isDownLoad = false;
                adapter.notifyDataSetChanged();
            }else {
                adapter = new DiaryDetailListAdapter();
                //列表数据 listView
                list_item.setAdapter(adapter);
            }
        }else{
            //加载更多
            isLoadMore = false;
            //添加到原来的集合中
            List<Article.DataBean.ListBean> moreList = article.getData().getList();
            list.addAll(moreList);
            //刷新下适配器
            adapter.notifyDataSetChanged();
        }
    }

    class DiaryDetailListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return list.size();
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null){
                convertView = View.inflate(context,R.layout.item_tab_detail_pager ,null );
                viewHolder = new ViewHolder();
                viewHolder.iv_icon =  convertView.findViewById(R.id.iv_icon);
                viewHolder.ll_tv_title =  convertView.findViewById(R.id.ll_tv_title);
                viewHolder.ll_tv_time =  convertView.findViewById(R.id.ll_tv_time);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            //根据位置得到数据
            String thematicUrl = Constants.TIME_LINE_CLOUD + list.get(position).getThematicUrl();

//            x.image().bind(viewHolder.iv_icon, thematicUrl,imageOptions);//图片
//            Glide.with(context)
//                    .load(thematicUrl).error( R.drawable.wifi03) //异常时候显示的图片
//                    .placeholder( R.drawable.default_load) //加载成功前显示的图片
//                    .fallback( R.drawable.liuyifei) //url为空的时候,显示的图片
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存
//                    .into(viewHolder.iv_icon);//在RequestBuilder 中使用自定义的ImageViewTarge

            Glide.with(context)
                    .load(thematicUrl)
                    .transform(new CenterCrop(context),new GlideRoundTransform(context))
                    .placeholder(R.drawable.default_load).error(R.drawable.wifi03)
                    .dontAnimate()
                    .crossFade()
                    .crossFade(5000)
                    .into(viewHolder.iv_icon);

            String title = list.get(position).getTitle();
            viewHolder.ll_tv_title.setText(title);
            String createTime = list.get(position).getCreateTime();
            viewHolder.ll_tv_time.setText(createTime);
            return convertView;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }
    }

    static class ViewHolder{
        ImageView iv_icon;
        TextView ll_tv_title;
        TextView ll_tv_time;
    }


    /**
     * 添加红点
     */
    private void addPoint() {
        //移除所有红点
        ll_point_group.removeAllViews();
        //轮播点数
        for (int i = 0; i < imageList.size() ; i++) {
            ImageView imageView = new ImageView(context);
            imageView.setBackgroundResource(R.drawable.point_selector);

            LinearLayout.LayoutParams params = new LinearLayout
                    .LayoutParams(DensityUtil.dip2px(8),
                    DensityUtil.dip2px(8));

            if (i == 0){
                imageView.setEnabled(true);
            }else{
                imageView.setEnabled(false);
                params.leftMargin = DensityUtil.dip2px(8);
            }
            imageView.setLayoutParams(params);
            ll_point_group.addView(imageView);
        }
    }

    class MyTabDetailTopOnPagerChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        //某个页面被选中，设置文本
        @Override
        public void onPageSelected(int i) {
            //1.设置文本
            tv_title.setText(list.get(i).getTitle());
            //2.红点高亮
            //把之前的设置灰色，当前设置红色
            ll_point_group.getChildAt(prePosition).setEnabled(false);//之前点
            ll_point_group.getChildAt(i).setEnabled(true);//当前点
            prePosition = i;
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    }


}
