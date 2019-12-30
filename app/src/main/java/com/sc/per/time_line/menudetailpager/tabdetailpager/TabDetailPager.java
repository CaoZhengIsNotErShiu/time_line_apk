package com.sc.per.time_line.menudetailpager.tabdetailpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.Gson;
import com.sc.per.time_line.R;
import com.sc.per.time_line.adapter.MyTabDetailImagePagerAdapter;
import com.sc.per.time_line.base.MenuDetailBasePager;
import com.sc.per.time_line.entity.Article;
import com.sc.per.time_line.entity.Menu;
import com.sc.per.time_line.utils.CacheUtils;
import com.sc.per.time_line.utils.Constants;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


/**
 * 页签详情页面
 */
public class TabDetailPager extends MenuDetailBasePager {

    @ViewInject(R.id.viewpager)
    private ViewPager viewpager;

    private Menu menu;

    public TabDetailPager(Context context,Menu menu) {
        super(context);
        this.menu = menu;
    }

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.tabdetail_pager, null);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        System.out.println("url:" + menu.getUrl());
        String saveJson = CacheUtils.getString(context, menu.getUrl());
        if (!TextUtils.isEmpty(saveJson)){
            processData(saveJson);
        }
        getMenuDetailByMenuUrl(menu.getUrl());

    }

    private void getMenuDetailByMenuUrl(final String url){
        RequestParams params = new RequestParams(Constants.TIME_LINE_MENU_INFO_URL);
        params.addBodyParameter("index",url);
        params.addBodyParameter("pageNum","1");
        x.http().post(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                //1.缓存菜单数据
                CacheUtils.putString(context,url,result);
                //联网请求数据
                processData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void processData(String json) {
        Gson gson = new Gson();
        Article article = gson.fromJson(json, Article.class);
        System.out.println(article.toString());


    }


}
