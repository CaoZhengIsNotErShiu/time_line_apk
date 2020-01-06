package com.sc.per.time_line.Fragement;


import android.annotation.SuppressLint;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
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

/**
 * 功能:主界面底部导航栏，切换，填充视图
 */
@SuppressLint("ValidFragment")
public class ContentFragment extends BaseFragment {

    @ViewInject(R.id.viewpager)
    private NoScrollPager viewPager;
    //底部导航
    @ViewInject(R.id.rg_main)
    private RadioGroup rg_main;

    private ArrayList<BasePager> pagers;
    //当前被点击加载的页面位置
    private int currentPoint;

    //adapter中广告控件里的ImageView
    private ImageView ggImageView;

    //广告item所在的位置
    private int ggPosition = -1;

    public ContentFragment(ImageView ggImageView, int ggPosition) {
        this.ggImageView = ggImageView;
        this.ggPosition = ggPosition;
    }

    @Override
    public View initView() {
        //填充底部导航菜单
        View view = View.inflate(context, R.layout.context_fragment, null);
        //1.将视图注入到框架中，让ContentFragment和view关联
        x.view().inject(this,view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        pagers = new ArrayList<>();
        //底部导航页面
        pagers.add(new HomePager(context));
        pagers.add(new VipPager(context));
        pagers.add(new EditorPager(context));
        pagers.add(new InformPager(context,ggImageView,ggPosition));
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

    public HomePager getFrontPager(){
        return (HomePager) pagers.get(0);
    }

    //监听某个页面被加载
    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int i, float v, int i1) {
        }

        /**
         * 当某个页面被选中，回调
         *
         * @param i
         */
        @Override
        public void onPageSelected(int i) {
            BasePager basePager = pagers.get(i);
            basePager.initData();
            currentPoint = i;
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
//                  viewPager.setCurrentItem(2);
                    upEditView();

                    android.widget.Toast.makeText(context ,"条目被点击：" + currentPoint , android.widget.Toast.LENGTH_SHORT ).show();
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

    private void upEditView(){
        View view = View.inflate(context, R.layout.first_popuwindow, null);
                            android.widget.ImageView mImg_first = view.findViewById(R.id.mImg_first);
                            //关闭按钮
                            android.widget.ImageView iv_first_close = view.findViewById(R.id.iv_first_close);
                            android.widget.LinearLayout mLine = view.findViewById(R.id.mLine);
                            final android.widget.PopupWindow mPopu = new android.widget.PopupWindow(view, android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                                    android.view.ViewGroup.LayoutParams.MATCH_PARENT);
                            mPopu.setHeight(android.view.ViewGroup.LayoutParams.MATCH_PARENT);
                            mLine.getBackground().setAlpha(80);
                            mPopu.setAnimationStyle(R.style.pop_animation);//动画
                            mPopu.showAtLocation(view, android.view.Gravity.CENTER, 0, 0);
                            bgAlpha(0.5f);
                            mImg_first.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //跳转广告新闻
                                    android.widget.Toast.makeText(context,"我还没有写内容呢",android.widget.Toast.LENGTH_SHORT).show();
                                }
                            });
                            iv_first_close.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    bgAlpha(1f);
                                    viewPager.setCurrentItem(currentPoint);
                                    mPopu.dismiss();
                                }
                            });

                            mPopu.setOutsideTouchable(true);//判断在外面点击是否有效
                            mPopu.setFocusable(true);
                            mPopu.showAsDropDown(view);
                            mPopu.isShowing();
    }

    private void bgAlpha(float bgAlpha) {
        android.view.WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        context.getWindow().addFlags(android.view.WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }



}
