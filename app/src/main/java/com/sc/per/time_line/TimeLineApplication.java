package com.sc.per.time_line;

import android.app.Application;

import org.xutils.x;

/**
 * 代表整个软件
 */
public class TimeLineApplication extends Application{

    /**
     * 所有组件被创建时候执行
     */
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.setDebug(true);
        x.Ext.init(this);

    }
}
