package com.sc.per.time_line.base;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

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
    public View root_view;

    /**
     * 显示标题
     */
    public TextView tv_View;

    /**
     * 菜单
     */
    public ImageButton imageButton;

    /**
     * 子页面
     */
    public FrameLayout frameLayout;

    public BasePager(Context context){
        this.context = context;
        root_view = initView();
    }

    /**
     * 用于初始化公共视图
     *
     * @return
     */
    private View initView(){
        //公共页面
        View view = View.inflate(context,R.layout.base_pager,null);
        tv_View =  view.findViewById(R.id.iv_title);
        imageButton =  view.findViewById(R.id.ib_menu);
        frameLayout =  view.findViewById(R.id.fl_content);
        return view;
    }

    /**
     * 初始化数据
     */
    public void initData(){

    }
}
