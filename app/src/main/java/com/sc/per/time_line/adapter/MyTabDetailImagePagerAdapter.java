package com.sc.per.time_line.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.sc.per.time_line.entity.Menu;
import com.sc.per.time_line.menudetailpager.tabdetailpager.TabDetailPager;

import java.util.ArrayList;
import java.util.List;

/**
 * 随笔界面适配器
 */
public class MyTabDetailImagePagerAdapter extends PagerAdapter {


    private final Context context;
    private final ArrayList<TabDetailPager> tabDetailPagers;
    private final List<String> menu;

    public MyTabDetailImagePagerAdapter(Context context, ArrayList<TabDetailPager> tabDetailPagers, List<String> menu) {
        this.context = context;
        this.tabDetailPagers = tabDetailPagers;
        this.menu = menu;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return menu.get(position);
    }

    @Override
    public int getCount() {
        return tabDetailPagers.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        TabDetailPager tabDetailPager = tabDetailPagers.get(position);
        View rootView = tabDetailPager.rootView;
        tabDetailPager.initData();//初始化数据
        container.addView(rootView);
        return rootView;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
