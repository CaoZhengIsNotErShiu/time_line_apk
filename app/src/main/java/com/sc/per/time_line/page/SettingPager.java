package com.sc.per.time_line.page;

import android.content.Context;
import android.view.View;

import com.sc.per.time_line.R;
import com.sc.per.time_line.base.BasePager;

import org.xutils.x;

/**
 * 主页
 */
public class SettingPager extends BasePager {

    public SettingPager(Context context) {
        super(context);
    }



    @Override
    public void initData() {
        super.initData();

        final View view = View.inflate(context, R.layout.setting_pager, null);
        x.view().inject(SettingPager.this,view);
//
//        //1.设置标题
        tv_View.setText("设置");
//        //2.获取数据
//        TextView t_View = new TextView(context);
//        t_View.setGravity(Gravity.CENTER);
//        t_View.setTextColor(Color.RED);
//        t_View.setTextSize(20);
        //3.将子视图添加到BasePager的FrameLayout中
        frameLayout.addView(view);
        //4.绑定数据
//        t_View.setText("设置数据1");

    }


}
