package com.sc.per.time_line.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sc.per.time_line.R;

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
