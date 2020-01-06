package com.example.refreshlisview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 自定义下拉刷新
 */
public class RefreshListView extends ListView {

    /**
     * 顶部下拉刷新和顶部轮播图
     */
    private LinearLayout headerView;

    //下拉刷新控件
    private View ll_pull_down_refresh;

    private ImageView iv_arrow;

    private ProgressBar pb_status;

    private TextView tv_status;
    private TextView tv_time;

    //下拉刷新控件的高
    private int pullDownRefreshHeight;

    /**
     * 下拉刷新
     * @param context
     */
    public static final int PULL_DOWN_REFRESH = 0;

    /**
     * 手松刷新
     * @param context
     */
    public static final int RELEASE_REFRESH = 1;

    /**
     * 正在刷新
     */
    public static final int REFRESHING = 2;

    /**
     * 当前状态
     */
    private int currentStatus = PULL_DOWN_REFRESH;

    /**
     * 加载更多
     */
    private View footerView;
    /**
     * 加载更多控件的高
     */
    private int footerViewHeight;
    /**
     * 是否已经加载更多
     */
    private boolean isLoadMore;
    /**
     * 顶部轮播图部分
     */
    private View topPager;
    /**
     * listView在Y轴上的坐标
     */
    private int listVeiwOnScreenY = -1;


    public RefreshListView(Context context) {
        this(context,null);
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHeaderView(context);
        initAnimation();//动画效果

        initFooterView(context);
    }

    //底部刷新
    private void initFooterView(Context context) {
        footerView = View.inflate(context,R.layout.refresh_foot,null);
        footerView.measure(0,0);
        footerViewHeight = footerView.getMeasuredHeight();
        footerView.setPadding(0,-footerViewHeight,0,0);
        //添加到listView中
        addFooterView(footerView);

        //监听listView的滚动
        setOnScrollListener(new MyOnRefreshListener());
    }

    /**
     * 将下拉刷新和顶部轮播图放到一个布局中，形成一个整体，加入到头部中
     * @param topPager
     */
    public void addTopTadView(View topPager) {
        if (topPager != null){
            this.topPager = topPager;
            headerView.addView(topPager);
        }
    }


    class MyOnRefreshListener implements OnScrollListener{

        @Override
        public void onScrollStateChanged(AbsListView absListView, int i) {
            //当静止或惯性滚动到底部
            if (i == OnScrollListener.SCROLL_STATE_IDLE || i == OnScrollListener.SCROLL_STATE_FLING ){
                //并且是最后一条可见
                if (getLastVisiblePosition() >= getCount()-2){
                    //1.显示加载更多布局
                    footerView.setPadding(8,8,8,8);
                    //2.状态改变
                    isLoadMore = true;
                    //3.回调接口
                    if(mOnRefreshListener != null){
                        mOnRefreshListener.onLoadMore();
                    }
                }
            }
        }

        @Override
        public void onScroll(AbsListView absListView, int i, int i1, int i2) {

        }
    }


    private Animation upAnimation;
    private Animation downAnimation;

    //下拉刷新动画
    private void initAnimation() {
        upAnimation = new RotateAnimation(0,-180,
                RotateAnimation.RELATIVE_TO_SELF,0.5f,
                RotateAnimation.RELATIVE_TO_SELF,0.5f);

        upAnimation.setDuration(500);
        upAnimation.setFillAfter(true);

        downAnimation = new RotateAnimation(-180,-360,
                RotateAnimation.RELATIVE_TO_SELF,0.5f,
                RotateAnimation.RELATIVE_TO_SELF,0.5f);

        downAnimation.setDuration(500);
        downAnimation.setFillAfter(true);
    }

    private void initHeaderView(Context context){
        headerView = (LinearLayout) View.inflate(context, R.layout.refresh_header,null);
        //下拉刷新控件
        ll_pull_down_refresh = headerView.findViewById(R.id.ll_pull_down_refresh);
        iv_arrow = headerView.findViewById(R.id.iv_arrow);
        pb_status = headerView.findViewById(R.id.pb_status);
        tv_status = headerView.findViewById(R.id.tv_status);
        tv_time = headerView.findViewById(R.id.tv_time);

        //测量
        ll_pull_down_refresh.measure(0,0);
        pullDownRefreshHeight = ll_pull_down_refresh.getMeasuredHeight();
        //默认隐藏下拉刷新控件
        //View.setPadding(0,-控件高，0，0)//完全隐藏
        //View.setPadding(0,0,0,0) //完全显示
        ll_pull_down_refresh.setPadding(0,-pullDownRefreshHeight,0,0);

        //添加头
        RefreshListView.this.addHeaderView(headerView);
    }

    private float startY = -1;
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                //1.记录起始坐标
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (startY == -1){
                    startY = ev.getY();
                }

                //判断顶部轮播图是否完全显示，只有完全显示才会有下拉刷新
                boolean isDisPlayTopScr =  isDisplayTopScr();
                if (!isDisPlayTopScr){
                    //加载更多
                    break;
                }

                //如果正在刷新，直接放回
                if (currentStatus == REFRESHING){
                    break;
                }
                //2.新的坐标
                float endY = ev.getY();
                //3.记录滑动的距离
                float distanceY = endY -startY;
                if (distanceY > 0){//下拉
                    //int paddingTop = -控件高 +distanceY ;
                    int paddingTop = (int) (pullDownRefreshHeight + distanceY);

                    if (paddingTop < 0 && currentStatus != PULL_DOWN_REFRESH){
                        //下拉刷新状态
                        currentStatus = PULL_DOWN_REFRESH;
                        //更新状态
                        refreshViewState();
                    }else if (paddingTop > 0 && currentStatus != RELEASE_REFRESH){
                        //手松刷新状态
                        currentStatus = RELEASE_REFRESH;
                        //更新状态
                        refreshViewState();

                    }

                    ll_pull_down_refresh.setPadding(0,paddingTop,0,0);

                }
                break;
            case MotionEvent.ACTION_UP:
                startY = -1;
                if (currentStatus == PULL_DOWN_REFRESH){
                    //完全隐藏
                    ll_pull_down_refresh.setPadding(0,-pullDownRefreshHeight,0,0);
                }else if (currentStatus == RELEASE_REFRESH){
                    currentStatus = REFRESHING;
                    refreshViewState();
                    //显示
                    ll_pull_down_refresh.setPadding(0,0,0,0);

                    //回调接口，设置下拉刷新
                    //2.调用接口，在事件源，在up的时候
                    if(mOnRefreshListener != null){
                        mOnRefreshListener.onPullDownRefresh();
                    }
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 是否显示顶部轮播图
     * 当listView在屏幕上的Y轴坐标小于或者等于顶部轮播图在Y轴的坐标时候，顶部轮播图完全显示
     * @return
     */
    private boolean isDisplayTopScr() {
        if (topPager != null){
            //1.得到listView在屏幕上的坐标
            int[] location = new int[2];
            if (listVeiwOnScreenY == -1){
                getLocationOnScreen(location);
                listVeiwOnScreenY = location[1];
            }
            //2.得到顶部轮播图在屏幕上的坐标
            topPager.getLocationOnScreen(location);
            int topTabViewOnScreen = location[1];
            return listVeiwOnScreenY <= topTabViewOnScreen;
        }else {
            return true;
        }

    }


    private void refreshViewState() {
        switch (currentStatus){
            case PULL_DOWN_REFRESH://下拉状态
                iv_arrow.setAnimation(downAnimation);
                tv_status.setText("下拉刷新...");
                break;
            case RELEASE_REFRESH://松手状态
                iv_arrow.setAnimation(upAnimation);
                tv_status.setText("手松刷新...");
                break;
            case REFRESHING://正在刷新
                tv_status.setText("正在刷新...");
                pb_status.setVisibility(VISIBLE);
                iv_arrow.clearAnimation();
                iv_arrow.setVisibility(GONE);
                break;
        }
    }

    /**
     * 当联网成功和失败的时候回调该方法
     * @param success
     */
    public void onRefreshFinish(boolean success) {

        if (success){
            //加载更多
            isLoadMore = false;
            //隐藏控件
            footerView.setPadding(0,-footerViewHeight,0,0);

        }else {
            //下拉刷新
            tv_status.setText("下拉刷新...");
            currentStatus = PULL_DOWN_REFRESH;
            iv_arrow.clearAnimation();
            pb_status.setVisibility(GONE);
            iv_arrow.setVisibility(VISIBLE);
            ll_pull_down_refresh.setPadding(0,-pullDownRefreshHeight,0 , 0);
            if (success){
                //设置更新时间
                tv_time.setText("上次更新时间：" + getSystemTime());
            }
        }


    }

    /**
     * 得到当前android系统的时间
     * @return
     */
    private String getSystemTime(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date());
    }

    //1.定义接口
    public interface OnRefreshListener{
        /**
         * 当下拉刷新的时候回调这个方法
         */
        void onPullDownRefresh();

        /**
         * 加载更多
         */
        void onLoadMore();
    }

    private OnRefreshListener mOnRefreshListener;

    /**
     * 设置监听刷新
     * @param l
     */
    public void setOnRefreshListener(OnRefreshListener l){
        this.mOnRefreshListener = l;
    }


}
