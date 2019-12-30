package com.sc.per.time_line.menudetailpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.sc.per.time_line.R;
import com.sc.per.time_line.activity.MainActivity;
import com.sc.per.time_line.adapter.MyEssaysPagerAdapter;
import com.sc.per.time_line.base.MenuDetailBasePager;
import com.sc.per.time_line.entity.Menu;
import com.sc.per.time_line.menudetailpager.tabdetailpager.TabDetailPager;
import com.viewpagerindicator.TabPageIndicator;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 随笔页面
 */
public class EssaysPager extends MenuDetailBasePager {

    @ViewInject(R.id.tabPageIndicator)
    private TabPageIndicator tabPageIndicator;

    @ViewInject(R.id.viewpager_essays)
    private ViewPager viewPager;

    @ViewInject(R.id.ib_menu_right)
    private ImageButton ibMenuRight;

    private List<Menu> menu;

    /**
     * 页签页面集合
     */
    private ArrayList<TabDetailPager> tabDetailPagers;

    public EssaysPager(Context context, List<Menu> menus) {
        super(context);
        this.menu = menus;
    }

    @Override
    public View initView() {
        final View view = View.inflate(context, R.layout.essays_menu_detail_pager, null);
        x.view().inject(EssaysPager.this,view);

        //设置点击事件
        ibMenuRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
            }
        });
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        tabDetailPagers = new ArrayList<>();
        for (int i = 0; i < menu.size(); i++) {
            tabDetailPagers.add(new TabDetailPager(context,menu.get(i)));
        }
        //设置适配器
        viewPager.setAdapter(new MyEssaysPagerAdapter(context,tabDetailPagers,menu));
        //viewPager和tabPageIndicator关联
        tabPageIndicator.setViewPager(viewPager);
        //主页监听页面变化，通过tabPageIndicator监听
        tabPageIndicator.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            if (i == 0){
                //SlidingMenu可以全屏滑动
                isEnableSlidingMenu(SlidingMenu.TOUCHMODE_FULLSCREEN);
            }else {
                isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

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
