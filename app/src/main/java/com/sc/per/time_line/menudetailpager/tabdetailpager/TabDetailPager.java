package com.sc.per.time_line.menudetailpager.tabdetailpager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.sc.per.time_line.base.MenuDetailBasePager;

/**
 * 页签详情页面
 */
public class TabDetailPager extends MenuDetailBasePager {

    private TextView textView;

    private String menu;

    public TabDetailPager(Context context,String menu) {
        super(context);
        this.menu = menu;
    }

    @Override
    public View initView() {
        textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(20);
        textView.setTextColor(Color.RED);
        return textView;
    }

    @Override
    public void initData() {
        super.initData();
        textView.setText(menu);
    }
}
