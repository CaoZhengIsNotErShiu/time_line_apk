package com.sc.per.time_line.menudetailpager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.sc.per.time_line.R;
import com.sc.per.time_line.base.MenuDetailBasePager;
import com.sc.per.time_line.menudetailpager.tabdetailpager.TabDetailPager;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 随笔页面
 */
public class EssaysPager extends MenuDetailBasePager {


    @ViewInject(R.id.viewpager_essays)
    private ViewPager viewPager;

    private List<String> menu;

    /**
     * 页签页面集合
     */
    private ArrayList<TabDetailPager> tabDetailPagers;

    public EssaysPager(Context context, List<String> menus) {
        super(context);
        this.menu = menus;
    }



    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.essays_menu_detail_pager, null);
        x.view().inject(EssaysPager.this,view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        tabDetailPagers = new ArrayList<>();
        for (int i = 0; i < menu.size(); i++) {
            System.out.println(menu.get(i));
            tabDetailPagers.add(new TabDetailPager(context,menu.get(i)));
        }

        viewPager.setAdapter(new MyEssaysPagerAdapter());

    }

    class MyEssaysPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            System.out.println("size:"+tabDetailPagers.size());
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
}
