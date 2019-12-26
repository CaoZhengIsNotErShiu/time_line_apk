package com.sc.per.time_line.page;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sc.per.time_line.Fragement.LeftMenuFragment;
import com.sc.per.time_line.activity.MainActivity;
import com.sc.per.time_line.base.BasePager;
import com.sc.per.time_line.entity.HttpResult;
import com.sc.per.time_line.entity.MenuBean;
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
        RequestParams params = new RequestParams(Constants.TIME_LINE_MENU_URL);
//        params.addQueryStringParameter("userName","虹猫");
//        params.addQueryStringParameter("index","index");
//        params.addQueryStringParameter("pageNum","1");
//        System.out.println(params);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("数据："+result);
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

        MainActivity activity = (MainActivity) context;
        LeftMenuFragment leftMenuFragment = (LeftMenuFragment) activity.getLeftMenuFragment();
        //把数据传递给左侧菜单
        leftMenuFragment.setData(result.getData());

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


}
