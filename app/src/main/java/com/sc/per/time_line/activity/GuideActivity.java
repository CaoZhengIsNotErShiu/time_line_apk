package com.sc.per.time_line.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.sc.per.time_line.R;

import java.util.ArrayList;

public class GuideActivity extends Activity {


    private ViewPager guide_viewpager;
    private Button guide_btn_start_main;
    private LinearLayout guide_ll_point_group;
    
    private ArrayList<ViewPager> imageViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        guide_viewpager = findViewById(R.id.guide_viewpager);
        guide_btn_start_main = findViewById(R.id.guide_btn_start_main);
        guide_ll_point_group = findViewById(R.id.guide_ll_point_group);

        //图片数据
        int[] ids = new int[]{
                R.drawable.guide1,
                R.drawable.guide_2,
                R.drawable.guide3
        };

        imageViews = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            ViewPager viewPager = new ViewPager(this);
            //设置图片
            viewPager.setBackgroundResource(ids[i]);
            //添加到集合中
            imageViews.add(viewPager);
        }

        //设置viewpager适配器
        guide_viewpager.setAdapter(new MyPagerAdapter());
    }

    class MyPagerAdapter extends PagerAdapter{

        /**
         * 返回数据的总个数
         * @return
         */
        @Override
        public int getCount() {
            return imageViews.size();
        }

        /**
         * 作用，getView
         * @param container viewPager
         * @param position 要创建页面的位置
         * @return 返回和创建当前页面有关系的值
         */
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ViewPager viewPager = imageViews.get(position);
            //添加到容器中
            container.addView(viewPager);
            return viewPager;
        }
        /**
         * 判断
         * @param view 当前视图
         * @param o 上面instantiateItem返回的结果值
         * @return
         */
        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
//            return view == imageViews.get(Integer.parseInt((String) o));
            return view == o;
        }

        /**
         * 销毁
         * @param container viewpager
         * @param position 要销毁的页面位置
         * @param object 要销毁的页面
         */
        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//            super.destroyItem(container, position, object);
            container.removeView((View) object);
        }


    }
}
