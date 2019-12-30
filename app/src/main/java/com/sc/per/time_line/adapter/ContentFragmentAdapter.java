package com.sc.per.time_line.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.sc.per.time_line.base.BasePager;

import java.util.ArrayList;

/**
 * //ContentFragment适配器
 */
public class ContentFragmentAdapter extends PagerAdapter {

    private final ArrayList<BasePager> pagers;

    public ContentFragmentAdapter(ArrayList<BasePager> basePagers){
        this.pagers = basePagers;
    }
    /**
     * 页面总数
     * @return
     */
    @Override
    public int getCount() {
        return pagers.size();
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        //各个页面的实例
        BasePager basePager = pagers.get(position);
        View rootView = basePager.root_view;//各个子页面
        // basePager.initData();//各个页面的数据
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