package com.sc.per.time_line.page;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import com.sc.per.time_line.base.BasePager;
import com.sc.per.time_line.utils.Constants;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * 主页
 */
public class HomePager extends BasePager {


    public HomePager(Context context) {
        super(context);
    }



    @Override
    public void initData() {
        super.initData();
        //1.设置标题
        tv_View.setText("主页");
        //2.获取数据
        TextView t_View = new TextView(context);
        t_View.setGravity(Gravity.CENTER);
        t_View.setTextColor(Color.RED);
        t_View.setTextSize(20);
        //3.将子视图添加到BasePager的FrameLayout中
        frameLayout.addView(t_View);
        //4.绑定数据
        t_View.setText("主页数据1");

        //聯網請求
        getDataFromNet();
    }

    //1.使用xUtils3請求數據
    private void getDataFromNet() {
        RequestParams params = new RequestParams(Constants.TIME_LINE_URL);
        params.addQueryStringParameter("userName","虹猫");
        params.addQueryStringParameter("index","index");
        params.addQueryStringParameter("pageNum","1");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("数据："+result);
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
}
