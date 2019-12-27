package com.sc.per.time_line.Fragement;


import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioGroup;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.sc.per.time_line.R;
import com.sc.per.time_line.activity.MainActivity;
import com.sc.per.time_line.adapter.ContentFragmentAdapter;
import com.sc.per.time_line.base.BaseFragment;
import com.sc.per.time_line.base.BasePager;
import com.sc.per.time_line.page.EditorPager;
import com.sc.per.time_line.page.HomePager;
import com.sc.per.time_line.page.InformPager;
import com.sc.per.time_line.page.SettingPager;
import com.sc.per.time_line.page.VipPager;
import com.sc.per.time_line.view.NoScrollPager;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

public class ContentFragment extends BaseFragment {

    @ViewInject(R.id.viewpager)
    private NoScrollPager viewPager;
    //底部导航
    @ViewInject(R.id.rg_main)
    private RadioGroup rg_main;

    private ArrayList<BasePager> pagers;

    @Override
    public View initView() {

        View view = View.inflate(context, R.layout.context_fragment, null);
        //1.将视图注入到框架中，让ContentFragment和view关联
        x.view().inject(this,view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        pagers = new ArrayList<>();
        pagers.add(new HomePager(context));
        pagers.add(new VipPager(context));
        pagers.add(new EditorPager(context));
        pagers.add(new InformPager(context));
        pagers.add(new SettingPager(context));

        //设置viewpager的适配器
        viewPager.setAdapter(new ContentFragmentAdapter(pagers));

        //底部导航点击事件
        rg_main.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        //监听某个页面被加载
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
        //设置默认选中首页
        rg_main.check(R.id.rb_btn);
        //初始化首页第一次加载数据
        BasePager basePager = pagers.get(0);
        basePager.initData();

    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        /**
         * 当某个页面被选中，回调
         * @param i
         */
        @Override
        public void onPageSelected(int i) {
            BasePager basePager = pagers.get(i);
            basePager.initData();
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    }

    class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.rb_btn:
                    viewPager.setCurrentItem(0);
                    isEnableSlidingMenu(SlidingMenu.TOUCHMODE_FULLSCREEN);
                    break;
                case R.id.news_btn:
                    viewPager.setCurrentItem(1);
                    isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
                    break;
                case R.id.editor_btn:
                    viewPager.setCurrentItem(2);
                    isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
                    break;
                case R.id.author_btn:
                    viewPager.setCurrentItem(3);
                    isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
                    break;
                case R.id.step_btn:
                    viewPager.setCurrentItem(4,false);
                    isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
                    break;
            }
        }


    }

    /**
     * 根據傳入參數設置是否讓SlidingMenu可以滑動
     */
    private void isEnableSlidingMenu(int touchModel) {
        MainActivity mainActivity  = (MainActivity) context;
        mainActivity.getSlidingMenu().setTouchModeAbove(touchModel);
    }



}
