package com.sc.per.time_line.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.OverScroller;
import android.widget.RelativeLayout;

import com.sc.per.time_line.R;

public class WebLayout extends LinearLayout {
    private View mTop;
    private RelativeLayout ll_dynamic_web;
    private int mTopViewHeight;
    private OverScroller mScroller;
    public WebLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.VERTICAL);
        mScroller = new OverScroller(context);
    }


    /**
     * XML布局被加载完后，就会回调onFinshInfalte
     */
    @Override
    protected void onFinishInflate()
    {
        super.onFinishInflate();
        //这个id必须能找到
        mTop = findViewById(R.id.ll_detail_header);
        ll_dynamic_web = findViewById(R.id.rl_wv);
    }

    /**
     * 自定义View尺寸的规则
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        //不限制顶部的高度
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //将视图按照自己的意愿设置成任意的大小
        getChildAt(0).measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        //重新设置控件布局，宽和高。
        ViewGroup.LayoutParams params = ll_dynamic_web.getLayoutParams();
        //当前视图绝对大小
        params.height = getMeasuredHeight();
        //当前View的大小
        setMeasuredDimension(getMeasuredWidth(), mTop.getMeasuredHeight() + ll_dynamic_web.getMeasuredHeight());

        System.out.println("当前视图绝对大小:" + getMeasuredHeight() + "=========" + "mTop高度：" + mTop.getMeasuredHeight()
        +" ======== " + ll_dynamic_web.getMeasuredHeight());

    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        mTopViewHeight = mTop.getMeasuredHeight();
    }
    @Override
    public void scrollTo(int x, int y)
    {
        if (y < 0)
        {
            y = 0;
        }
        if (y > mTopViewHeight)
        {
            y = mTopViewHeight;
        }
        if (y != getScrollY())
        {
            super.scrollTo(x, y);
        }
    }
    @Override
    public void computeScroll()
    {
        if (mScroller.computeScrollOffset())
        {
            scrollTo(0, mScroller.getCurrY());
            invalidate();
        }
    }
}