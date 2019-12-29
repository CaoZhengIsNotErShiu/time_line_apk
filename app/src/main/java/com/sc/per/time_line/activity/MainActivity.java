package com.sc.per.time_line.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.sc.per.time_line.Fragement.ContentFragment;
import com.sc.per.time_line.Fragement.LeftMenuFragment;
import com.sc.per.time_line.R;
import com.sc.per.time_line.utils.BarUtils;
import com.sc.per.time_line.utils.DensityUtil;


public class MainActivity extends SlidingFragmentActivity {

    public static final String LEFT_MENU_TAG = "left_menu_tag";
    public static final String MAIN_ACTIVITY = "main_activity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化菜單
        initSlidingMenu();
        //初始化数据。视图
        initFragment();
    }

    private void initSlidingMenu() {
        //隐藏底部按钮
        BarUtils.hideBottomUIMenu(this);
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

    /**
     * 初始数据
     */
    private void initFragment() {
        //1.得到FragmentManger
        FragmentManager manager = getSupportFragmentManager();
        //2.开启事务
        FragmentTransaction ft = manager.beginTransaction();
        //3.替换数据
        ft.replace(R.id.left_menu_activity, new LeftMenuFragment(), LEFT_MENU_TAG);
        ft.replace(R.id.main_activity, new ContentFragment(), MAIN_ACTIVITY);
        //4.提交
        ft.commit();
    }

    /**
     * 得到左侧菜单
     * @return
     */
    public Fragment getLeftMenuFragment(){
        //1.得到FragmentManger
        FragmentManager manager = getSupportFragmentManager();
        return manager.findFragmentByTag(LEFT_MENU_TAG);
    }


    /**
     * 左侧菜单详情页面
     * @return
     */
    public ContentFragment getContentFragment() {
        //1.得到FragmentManger
        FragmentManager manager = getSupportFragmentManager();
        return (ContentFragment) manager.findFragmentByTag(MAIN_ACTIVITY);
    }
}
