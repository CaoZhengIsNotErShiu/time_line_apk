package com.sc.per.time_line.menudetailpager.tabdetailpager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.refreshlisview.RefreshListView;
import com.google.gson.Gson;
import com.sc.per.time_line.R;
import com.sc.per.time_line.activity.NewDetailActivity;
import com.sc.per.time_line.adapter.MyTabDetailImagePagerAdapter;
import com.sc.per.time_line.base.MenuDetailBasePager;
import com.sc.per.time_line.entity.Article;
import com.sc.per.time_line.entity.Menu;
import com.sc.per.time_line.utils.CacheUtils;
import com.sc.per.time_line.utils.Constants;
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
public class TabDetailPager extends MenuDetailBasePager {

    public static final String REAL_ARRAY_ID = "real_array_id";
    private HorizontalScrollViewPager viewpager;
    private TextView tv_title;
    private LinearLayout ll_point_group;
    private RefreshListView list_item;

    private Menu menu;

    private TabDetailListAdapter adapter;

    private List<Article.DataBean.ListBean> list;

    //之前点
    private int prePosition;

    //xutils请求图片为null，设置默认图片
    private ImageOptions imageOptions;
    //加载更多
    private boolean isLoadMore = false;
    private List<Article.DataBean.ListBean> imageList;

    public TabDetailPager(Context context,Menu menu) {
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
        View view = View.inflate(context, R.layout.tabdetail_pager, null);
        list_item = view.findViewById(R.id.list_item);
        View topPager = View.inflate(context, R.layout.tab_top_detail_pager, null);
        viewpager = topPager.findViewById(R.id.viewpager1);
        tv_title = topPager.findViewById(R.id.tv_title);
        ll_point_group = topPager.findViewById(R.id.ll_point_group);

        //把顶部轮播图，以头的方式添加到listView中
//        list_item.addHeaderView(topPager);
        list_item.addTopTadView(topPager);
        //设置下拉刷新监听
        list_item.setOnRefreshListener(new MyOnRefreshListener());

        //设置listView的item的监听
        list_item.setOnItemClickListener(new myOnItemClickListener());
        return view;
    }

    //设置listView的item的监听
    class myOnItemClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            int realPosition = position -1;
            Article.DataBean.ListBean listBean = list.get(realPosition);
            //1.取出id，判断是否存在
            String ids = CacheUtils.getString(context, REAL_ARRAY_ID);
            //2.判断，不存在，保存，存在，刷新适配器
            if(!ids.contains(listBean.getId())){
                CacheUtils.putString(context,REAL_ARRAY_ID , ids+listBean.getId()+",");
                //刷新适配器
                adapter.notifyDataSetChanged();
            }
            //3.跳转到新闻详情浏览页面
            Intent intent = new Intent(context,NewDetailActivity.class);
            intent.putExtra("art_id", listBean.getId());
            context.startActivity(intent);
        }
    }

    class MyOnRefreshListener implements RefreshListView.OnRefreshListener{

        @Override
        public void onPullDownRefresh() {
            //1.下拉刷新
            String pageNum = CacheUtils.getString(context,menu.getUrl()+"_list_page_num");
            Toast.makeText(context,"下拉onPullDownRefresh:"+pageNum, Toast.LENGTH_SHORT ).show();
            getMenuDetailByMenuUrl(menu.getUrl(),pageNum);

        }

        @Override
        public void onLoadMore() {
            //1.下拉刷新
            String pageNum = CacheUtils.getString(context,menu.getUrl()+"_list_page_num");
            getMoreDateForUrl(menu.getUrl(),pageNum);
        }
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
        params.addBodyParameter("pageNum",1);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //1.缓存菜单数据
                //联网请求数据
                isLoadMore = true;
                //解析数据
                processData(result,url);
                //隐藏加载更多刷新控件
                list_item.onRefreshFinish(false);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                //隐藏加载更多刷新控件
                list_item.onRefreshFinish(false);

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

        RequestParams params = new RequestParams(Constants.TIME_LINE_MENU_INFO_URL);
        params.setConnectTimeout(5000);
        params.addBodyParameter("index",url);
        params.addBodyParameter("pageNum",1);
        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                //1.缓存菜单数据
                CacheUtils.putString(context,url,result);
                System.out.println(result);
                //联网请求数据
                processData(result,url);
                //隐藏下拉刷新控件，更新时间，重写数据
                list_item.onRefreshFinish(true);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                //隐藏下拉刷新控件，更新时间
                list_item.onRefreshFinish(false);

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
            list = article.getData().getList();
            imageList = new ArrayList<>();
            if (list.size() >= 3){
                 for (int i = 0; i < list.size() ; i++) {
                     String thematicUrl = list.get(i).getThematicUrl();
                     String title = list.get(i).getTitle();
                     Article.DataBean.ListBean listBean = new Article.DataBean.ListBean();
                     listBean.setTitle(title);
                     listBean.setThematicUrl(thematicUrl);
                     imageList.add(listBean);
                 }
             }else {
                imageList =  list;
            }
            //顶部轮播图适配器
            viewpager.setAdapter(new MyTabDetailImagePagerAdapter(context,imageList));
            //添加红点
            addPoint();

            //监听页面的改变，设置红点变化和文本变化
            viewpager.addOnPageChangeListener(new MyTabDetailTopOnPagerChangeListener());
            //初始化轮播图顶部数据
            if (!this.list.isEmpty()){
                tv_title.setText(this.list.get(0).getTitle());
            }
            adapter = new TabDetailListAdapter();
            //列表数据 listView
            list_item.setAdapter(adapter);

        }else{
            //加载更多
            isLoadMore = false;
            //添加到原来的集合中
            List<Article.DataBean.ListBean> moreList = article.getData().getList();
            this.list.addAll(moreList);
            //刷新下适配器
            adapter.notifyDataSetChanged();
        }



    }

    class TabDetailListAdapter extends BaseAdapter{

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
            //请求图片
//            x.image().bind(viewHolder.iv_icon, thematicUrl,imageOptions);
            Glide.with(context)
                    .load(thematicUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(viewHolder.iv_icon);
            String title = list.get(position).getTitle();
            viewHolder.ll_tv_title.setText(title);
            //设置更新时间
            String createTime = list.get(position).getCreateTime();
            viewHolder.ll_tv_time.setText(createTime);
            String ids = CacheUtils.getString(context,REAL_ARRAY_ID );
            if (ids.contains(list.get(position).getId())){
                //设置灰色
                viewHolder.ll_tv_title.setTextColor(Color.parseColor("#CAC5C5"));
            }else {
                //设置黑色
                viewHolder.ll_tv_title.setTextColor(Color.parseColor("#000000"));
            }
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
