package com.sc.per.time_line.base;

import android.content.Context;
import android.view.View;

/**
 * 菜单详情页面基类
 */
public abstract class MenuDetailBasePager  {

    public final Context context;


    /**
     * 各个菜单详情页面试图
     */
    public View rootView;

    public MenuDetailBasePager(Context context){
        this.context = context;
        rootView = initView();
    }

    public abstract View initView();

    public void initData(){

    }

}
