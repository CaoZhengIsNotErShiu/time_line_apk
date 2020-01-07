package com.sc.per.time_line.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.sc.per.time_line.entity.Menu;
import com.sc.per.time_line.menudetailpager.tabdetailpager.DiaryDetailPager;

import java.util.ArrayList;
import java.util.List;

/**
 * 随笔界面适配器
 */
public class MyDiaryPagerAdapter extends PagerAdapter {


    private final Context context;
    private final ArrayList<DiaryDetailPager> tabDetailPagers;
    private final List<Menu> menu;

    public MyDiaryPagerAdapter(Context context, ArrayList<DiaryDetailPager> tabDetailPagers, List<Menu> menu) {
        this.context = context;
        this.tabDetailPagers = tabDetailPagers;
        this.menu = menu;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return menu.get(position).getMenu();
    }

    @Override
    public int getCount() {
        return tabDetailPagers.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        DiaryDetailPager diaryDetailPager = tabDetailPagers.get(position);
        View rootView = diaryDetailPager.rootView;
        diaryDetailPager.initData();//初始化数据
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
