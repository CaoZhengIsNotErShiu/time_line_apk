package com.sc.per.time_line.page;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.widget.TextView;

import com.sc.per.time_line.R;
import com.sc.per.time_line.adapter.RecyclerViewAdapter;
import com.sc.per.time_line.base.BasePager;
import com.sc.per.time_line.entity.Menu;

import org.xutils.common.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * vip
 */
public class InformPager extends BasePager {

    public InformPager(Context context) {
        super(context);
    }

    private List<Menu> data;
    private RecyclerView recyclerView;//声明RecyclerView
    private RecyclerViewAdapter adapterDome;//声明适配器

    @Override
    public void initData() {
        super.initData();
        //1.设置标题
        tv_View.setText("通知页面");

        //2.获取数据
        recyclerView = new RecyclerView(context);
        data = new ArrayList<>();
        for (int i=0;i<10;i++){
            Menu menu = new Menu();
            menu.setMenu("这是第"+i+"个测试");
            data.add(menu);
        }
        adapterDome = new RecyclerViewAdapter(context,data);

        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapterDome);
        //3.将子视图添加到BasePager的FrameLayout中
        frameLayout.addView(recyclerView);

    }




}
