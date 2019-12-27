package com.sc.per.time_line.page;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import com.sc.per.time_line.base.BasePager;

/**
 * vip
 */
public class InformPager extends BasePager {

    public InformPager(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        super.initData();
        //1.设置标题
        tv_View.setText("通知页面");

        //2.获取数据
        TextView t_View = new TextView(context);
        t_View.setGravity(Gravity.CENTER);
        t_View.setTextColor(Color.RED);
        t_View.setTextSize(20);
        //3.将子视图添加到BasePager的FrameLayout中
        frameLayout.addView(t_View);
        //4.绑定数据
        t_View.setText("通知页面");



    }




}
