package com.sc.per.time_line.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

/**
 * Created by KID on 2017/11/14.
 * 带滑动监听回调的WebView
 */

public class ScollListenWebView extends WebView  {

    private OnScrollChangedCallback mOnScrollChangedCallback;

    public ScollListenWebView(final Context context) {
        super(context);
    }

    public ScollListenWebView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public ScollListenWebView(final Context context, final AttributeSet attrs,
                              final int defStyle) {
        super(context, attrs, defStyle);
    }

    //重写onScrollChanged
    @Override
    protected void onScrollChanged(final int l, final int t, final int oldl,
                                   final int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        if (mOnScrollChangedCallback != null) {
            mOnScrollChangedCallback.onScroll(l - oldl, t - oldt);
        }
    }

    public OnScrollChangedCallback getOnScrollChangedCallback() {
        return mOnScrollChangedCallback;
    }

    public void setOnScrollChangedCallback(
            final OnScrollChangedCallback onScrollChangedCallback) {
        mOnScrollChangedCallback = onScrollChangedCallback;
    }
    /**
     * Impliment in the activity/fragment/view that you want to listen to the webview
     */
    public static interface OnScrollChangedCallback {
        public void onScroll(int dx, int dy);
    }

    private int downY = 0;
    private int currentY = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mOnScrollChangedCallback != null) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    downY = (int) event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    currentY = (int) event.getY();
                    mOnScrollChangedCallback.onScroll(0, downY-currentY);
                    break;
            }
            return super.onTouchEvent(event);
        }
        return false;
    }

}