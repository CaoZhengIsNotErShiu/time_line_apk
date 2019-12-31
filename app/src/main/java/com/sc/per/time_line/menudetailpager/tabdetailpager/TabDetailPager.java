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

import com.google.gson.Gson;
import com.sc.per.time_line.R;
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

import java.util.List;


/**
 * 页签详情页面
 */
public class TabDetailPager extends MenuDetailBasePager {

    private HorizontalScrollViewPager viewpager;
    private TextView tv_title;
    private LinearLayout ll_point_group;
    private ListView list_item;

    private Menu menu;

    private List<Article.DataBean.ListBean> list;

    //之前点
    private int prePosition;

    //xutils请求图片为null，设置默认图片
    private ImageOptions imageOptions;

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
        list_item.addHeaderView(topPager);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        String saveJson = CacheUtils.getString(context, menu.getUrl());
        if (!TextUtils.isEmpty(saveJson)){
            processData(saveJson);
        }
        getMenuDetailByMenuUrl(menu.getUrl());
    }

    private void getMenuDetailByMenuUrl(final String url){
        RequestParams params = new RequestParams(Constants.TIME_LINE_MENU_INFO_URL);
        params.addBodyParameter("index",url);
        params.addBodyParameter("pageNum","1");
        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                //1.缓存菜单数据
                CacheUtils.putString(context,url,result);
                //联网请求数据
                processData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void processData(String json) {
        Gson gson = new Gson();
        Article article = gson.fromJson(json, Article.class);

        list = article.getData().getList();
        //顶部轮播图适配器
        viewpager.setAdapter(new MyTabDetailImagePagerAdapter(context,list));
        //添加红点
        addPoint();

        //监听页面的改变，设置红点变化和文本变化
        viewpager.addOnPageChangeListener(new MyTabDetailTopOnPagerChangeListener());
        //初始化轮播图顶部数据
        if (!list.isEmpty()){
            tv_title.setText(list.get(0).getTitle());
        }

        //列表数据 listView
        list_item.setAdapter(new TabDetailListAdapter());

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

            x.image().bind(viewHolder.iv_icon, thematicUrl,imageOptions);//图片
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
        for (int i = 0; i < list.size() ; i++) {
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
