package com.sc.per.time_line.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.sc.per.time_line.Fragement.ContentFragment;
import com.sc.per.time_line.Fragement.LeftMenuFragment;
import com.sc.per.time_line.R;
import com.sc.per.time_line.utils.DensityUtil;


/**
 * 主界面activity
 */
public class MainActivity extends SlidingFragmentActivity {

    public static final String LEFT_MENU_TAG = "left_menu_tag";
    public static final String MAIN_ACTIVITY = "main_activity";

    //adapter中广告控件里的ImageView
    private ImageView ggImageView;

    //广告item所在的位置
    private int ggPosition = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //设置没有标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        //初始化菜單
        initSlidingMenu();
        //首页广告
        fistPopuwindow();
        //初始化数据。视图
        initFragment();
    }

    private void initSlidingMenu() {
        //隐藏底部按钮
//        BarUtils.hideBottomUIMenu(this);
        //1.设置主页面
        setContentView(R.layout.activity_main);
        //2.设置左侧菜单
        setBehindContentView(R.layout.activity_leftmenu);
        //3.设置右侧菜单
        SlidingMenu slidingMenu = getSlidingMenu();
        //4.设置显示的模式：左侧菜单+主页，左侧菜单+主页面+右侧菜单；主页面+右侧菜单
        slidingMenu.setMode(SlidingMenu.LEFT);
        //5.设置滑动模式：滑动边缘，全屏滑动，不可以滑动
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        //6.设置主页占据的宽度
        slidingMenu.setBehindOffset(DensityUtil.dip2px(MainActivity.this, 200));
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
        ft.replace(R.id.main_activity, new ContentFragment(ggImageView,ggPosition), MAIN_ACTIVITY);
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


    private void fistPopuwindow() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            View view = View.inflate(MainActivity.this, R.layout.first_popuwindow, null);
                            ImageView mImg_first = view.findViewById(R.id.mImg_first);
                            //关闭按钮
                            ImageView iv_first_close = view.findViewById(R.id.iv_first_close);
                            LinearLayout mLine = view.findViewById(R.id.mLine);
                            final PopupWindow mPopu = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.MATCH_PARENT);
                            mPopu.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
                            mLine.getBackground().setAlpha(80);
//                            mPopu.setAnimationStyle(R.style.pop_animation);//动画
                            mPopu.showAtLocation(view, Gravity.CENTER, 0, 0);
                            bgAlpha(0.5f);
                            mLine.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //跳转广告新闻
                                    Toast.makeText(MainActivity.this,"我还没有写内容呢",Toast.LENGTH_SHORT).show();
//                                    mPopu.dismiss();
                                }
                            });
                            iv_first_close.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    bgAlpha(1f);
                                    mPopu.dismiss();
                                }
                            });

                            mPopu.setOutsideTouchable(true);//判断在外面点击是否有效
                            mPopu.setFocusable(true);
                            mPopu.showAsDropDown(view);
                            mPopu.isShowing();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private void bgAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setAttributes(lp);
    }


    //用来从adapter里设置广告item的位置和ImageView（扩展：从这里传广告的图片地址,然后去加载）
    public void setGGViewPosition(int position, ImageView ggView) {
        this.ggPosition = position;
        this.ggImageView = ggView;
    }
}
