package com.sc.per.time_line.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 禁止滚动
 */
public class NoScrollPager extends ViewPager {

    /**
     * 代码中使用
     * @param context
     */
    public NoScrollPager(@NonNull Context context) {
        super(context);
    }

    /**
     * 布局文件使用
     * @param context
     * @param attrs
     */
    public NoScrollPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    /**
     * 重写触摸事件，消费掉
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return true;
    }
}
