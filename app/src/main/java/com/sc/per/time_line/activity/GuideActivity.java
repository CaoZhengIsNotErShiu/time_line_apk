package com.sc.per.time_line.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.sc.per.time_line.R;
import com.sc.per.time_line.SplashActivity;
import com.sc.per.time_line.utils.CacheUtils;
import com.sc.per.time_line.utils.DensityUtil;

import java.util.ArrayList;

/**
 * 引导界面
 */
public class GuideActivity extends Activity {


    private ViewPager guide_viewpager;
    private Button guide_btn_start_main;
    private LinearLayout guide_ll_point_group;
    private ImageView guide_iv_red_point;

    private int widthdpi;
    //两点间距
    private int leftMax;
    
    private ArrayList<ViewPager> imageViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        guide_viewpager = findViewById(R.id.guide_viewpager);
        guide_btn_start_main = findViewById(R.id.guide_btn_start_main);
        guide_ll_point_group = findViewById(R.id.guide_ll_point_group);
        guide_iv_red_point =  findViewById(R.id.guide_iv_red_point);


        //图片数据
        int[] ids = new int[]{
                R.drawable.guide1,
                R.drawable.guide_2,
                R.drawable.guide3
        };

        //优化像素，将像素转换成dp
        widthdpi = DensityUtil.dip2px(this,10);
        System.out.println(widthdpi);
        imageViews = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            ViewPager viewPager = new ViewPager(this);
            //设置图片
            viewPager.setBackgroundResource(ids[i]);
            //添加到集合中
            imageViews.add(viewPager);

            //创建点
            ImageView point = new ImageView(this);
            point.setBackgroundResource(R.drawable.point_normal);
            //设置像素
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(widthdpi, widthdpi);
            if (i != 0){
                //不包括第0个点，剩下的点距离左边30个像素
                params.leftMargin = widthdpi;
            }

            point.setLayoutParams(params);
            //添加到页面中
            guide_ll_point_group.addView(point);
        }

        //设置viewpager适配器
        guide_viewpager.setAdapter(new MyPagerAdapter());

        //滑动逻辑，因为两点的间距，和屏幕滑动的百分比成证据，所以只需 屏幕百分比 * 间距，可求出 两点移动距离
        //间距
        guide_iv_red_point.getViewTreeObserver()
                .addOnGlobalLayoutListener(new MyOnGlobalLayoutListener());

        //屏幕滑动百分比
        guide_viewpager.addOnPageChangeListener(new MyOnPageChangeListener());

        //开始按钮点击事件
        guide_btn_start_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //1.保存进入过主界面
                CacheUtils.putBoolean(GuideActivity.this, SplashActivity.START_MAIN,true);
                //2.跳转到主界面
                startActivity(new Intent(GuideActivity.this,MainActivity.class));
                //3.关闭当前页面
                finish();
            }
        });
    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener{

        /**
         * 页面滚动，回调方法
         * @param i 当前滑动页面位置
         * @param v 页面滑动百分比
         * @param i1 滑动像素
         */
        @Override
        public void onPageScrolled(int i, float v, int i1) {
            //两点间移动的距离 = 屏幕百分比 * 间距
//            int leftMargin = (int) (v * leftMax);

            //两点滑动坐标 = 原来起始位置 + 两点间移动距离
            int leftMargin = (int) (i* leftMax + v * leftMax);
            //leftMargin  = 两点滑动距离坐标
            RelativeLayout.LayoutParams params =
                    (RelativeLayout.LayoutParams) guide_iv_red_point.getLayoutParams();
            params.leftMargin = leftMargin;
            guide_iv_red_point.setLayoutParams(params);
        }

        /**
         * 页面选中的时候回调
         * @param i 被选中页面的位置
         */
        @Override
        public void onPageSelected(int i) {
            if (i == imageViews.size() - 1){
                guide_btn_start_main.setVisibility(View.VISIBLE);
            }else {
                guide_btn_start_main.setVisibility(View.GONE);
            }
        }

        /**
         * 页面滑动状态
         * @param i
         */
        @Override
        public void onPageScrollStateChanged(int i) {

        }
    }

    //红点移动逻辑
    class MyOnGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener{

        @Override
        public void onGlobalLayout() {
            //执行不止一次
            guide_iv_red_point.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            //两点间距
            leftMax = guide_ll_point_group.getChildAt(1).getLeft() - guide_ll_point_group.getChildAt(0).getLeft();
        }
    }

    //引导接口
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
