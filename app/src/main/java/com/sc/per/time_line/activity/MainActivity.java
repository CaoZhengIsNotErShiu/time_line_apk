package com.sc.per.time_line.activity;

import android.os.Bundle;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.sc.per.time_line.R;
import com.sc.per.time_line.utils.DensityUtil;

public class MainActivity extends SlidingFragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //1.设置主页面
        setContentView(R.layout.activity_main);

        //2.设置左侧菜单
        setBehindContentView(R.layout.activity_leftmenu);

        //3.设置右侧菜单
        SlidingMenu slidingMenu = getSlidingMenu();
        slidingMenu.setSecondaryMenu(R.layout.activity_rightmenu);

        //4.设置显示模式 1 左边 2 左右 3 右边 菜单
        slidingMenu.setMode(SlidingMenu.LEFT);

        //5.设置滑动模式 滑动边缘，全屏滑动，不可以滑动
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

        //6.设置主页占据宽度
        slidingMenu.setBehindOffset(DensityUtil.dip2px(MainActivity.this,200));

        //7.设置淡入淡出
        slidingMenu.setFadeEnabled(true);

        //8.设置淡出比例
        slidingMenu.setFadeDegree(0.4f);

        //设置滑动时拖拽效果
        slidingMenu.setBehindScrollScale(0);




    }
}
