package com.sc.per.time_line.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 水平滑动的viewPager
 */
public class HorizontalScrollViewPager extends ViewPager {
    public HorizontalScrollViewPager(@NonNull Context context) {
        super(context);
    }

    public HorizontalScrollViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 起始坐标
     */
    private float startX;
    private float startY;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                //请求父层视图，不拦截当前控件
                getParent().requestDisallowInterceptTouchEvent(true);
                //1.起始坐标
                startX = ev.getX();
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                //2.来到新的坐标
                float endX = ev.getX();
                float endY = ev.getY();
                //3.计算偏移量
                float distanceX = endX - startX;
                float distanceY = endY - startY;
                //判断滑动方向 (绝对值)
                if (Math.abs(distanceX) > Math.abs(distanceY)){
                    //水平方向
                    if (getCurrentItem() == 0 && distanceX > 0){
                        //从左到右滑动
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }else if (getCurrentItem() == (getAdapter().getCount() - 1) && distanceX < 0){
                        //从右到左滑动
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }else{
                        //中间部分
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                }else {
                    //竖直方向
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
                default:
                    break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
