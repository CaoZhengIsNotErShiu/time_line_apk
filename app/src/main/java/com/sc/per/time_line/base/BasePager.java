package com.sc.per.time_line.base;

import android.content.Context;
import android.view.View;

import com.sc.per.time_line.R;

/**
 * 公共类
 */
public class BasePager {

    /**
     * 上下文
     */
    public final Context context;

    /**
     * 视图
     * @param context
     */
    public View view;


    public BasePager(Context context){
        this.context = context;
    }

    /**
     * 用于初始化公共视图
     *
     * @return
     */
    public View initView(){
        //公共页面
        View view = View.inflate(context, R.layout.base_pager , null);
        return view;
    }

    /**
     * 初始化数据
     */
    public void initData(){

    }
}
