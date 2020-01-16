package com.sc.per.time_line.page;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sc.per.time_line.Fragement.LeftMenuFragment;
import com.sc.per.time_line.activity.MainActivity;
import com.sc.per.time_line.base.BasePager;
import com.sc.per.time_line.base.MenuDetailBasePager;
import com.sc.per.time_line.entity.Menu;
import com.sc.per.time_line.entity.MenuBean;
import com.sc.per.time_line.menudetailpager.AfterPager;
//import com.sc.per.time_line.menudetailpager.DiaryPager;
import com.sc.per.time_line.menudetailpager.DiaryPager;
import com.sc.per.time_line.menudetailpager.EssaysPager;
import com.sc.per.time_line.menudetailpager.FrontPager;
import com.sc.per.time_line.menudetailpager.LiunxPager;
import com.sc.per.time_line.menudetailpager.OtherPager;
import com.sc.per.time_line.menudetailpager.ShowPager;
import com.sc.per.time_line.utils.CacheUtils;
import com.sc.per.time_line.utils.Constants;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 主页
 */

public class HomePager extends BasePager {


    /**
     * 左侧菜单详情页面
     */
    private ArrayList<MenuDetailBasePager> menuDetailBasePagers;

    private List<Menu> menus;

    public HomePager(Context context) {
        super(context);
    }


    @Override
    public void initData() {
        super.initData();
        //1.设置标题
//        tv_View.setText("主页");

        rlTitle.setVisibility(View.GONE);

        //获取缓存菜单数据
        String menuSp = CacheUtils.getString(context,Constants.TIME_LINE_MENU_URL);
        if (!TextUtils.isEmpty(menuSp)){
            processData(menuSp);
        }
        //聯網請求
        getDataFromNet();
    }

    //1.使用xUtils3請求數據
    private void getDataFromNet() {
        RequestParams params = new RequestParams(Constants.TIME_LINE_MENU_URL);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //1.缓存菜单数据
                CacheUtils.putString(context,Constants.TIME_LINE_MENU_URL,result);
                //联网请求数据
                processData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println("失败 ："+ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 解析json
     * @param json
     */
    private void processData(String json) {

        MenuBean result = parsedJson(json);
        menus = new ArrayList<>();
        for (int i = 0; i < result.getData().size() ; i++) {
            for (int j = 0; j < result.getData().get(i).getChildren().size(); j++) {
                String menuText = result.getData().get(i).getChildren().get(j).getMenuText();
                String menuUrl = result.getData().get(i).getChildren().get(j).getMenuUrl();
                Menu menu = new Menu();
                menu.setMenu(menuText);
                menu.setUrl(menuUrl);
                menus.add(menu);
            }
        }
        MainActivity activity = (MainActivity) context;
        LeftMenuFragment leftMenuFragment = (LeftMenuFragment) activity.getLeftMenuFragment();

        //左侧菜单详情页面
        menuDetailBasePagers = new ArrayList<>();
        menuDetailBasePagers.add(new DiaryPager(context,menus));
        menuDetailBasePagers.add(new EssaysPager(context,menus));
        menuDetailBasePagers.add(new ShowPager(context));
        menuDetailBasePagers.add(new FrontPager(context));
        menuDetailBasePagers.add(new AfterPager(context));
        menuDetailBasePagers.add(new LiunxPager(context));
        menuDetailBasePagers.add(new OtherPager(context));

        //把数据传递给左侧菜单
        leftMenuFragment.setData(menus);
    }

    /**
     * 解析json数据：
     * @param json
     * @return
     */
    private MenuBean parsedJson(String json) {
        Gson gson = new Gson();
        MenuBean result = gson.fromJson(json,MenuBean.class );
        return result;
    }

    /**
     * 根据位置，切换详情页面
     * @param position
     */
    public void switchPager(int position) {
        //1.标题
        tv_View.setText(menus.get(position).getMenu());
        //2.移除之前内容
        frameLayout.removeAllViews();
        //3.替换新内容
        MenuDetailBasePager menuDetailBasePager = menuDetailBasePagers.get(position);
        View rootView = menuDetailBasePager.rootView;
        menuDetailBasePager.initData();//初始化数据
        frameLayout.addView(rootView);
    }
}
