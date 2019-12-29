package com.sc.per.time_line.menudetailpager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.sc.per.time_line.base.MenuDetailBasePager;


/**
 * liunx
 */
public class LiunxPager extends MenuDetailBasePager {

    private TextView t_View;

    public LiunxPager(Context context) {
        super(context);
    }

    @Override
    public View initView() {
        t_View = new TextView(context);
        t_View.setGravity(Gravity.CENTER);
        t_View.setTextColor(Color.RED);
        t_View.setTextSize(20);
        return t_View;
    }

    @Override
    public void initData() {
        super.initData();
        t_View.setText("liunx详情页面");
    }
}
