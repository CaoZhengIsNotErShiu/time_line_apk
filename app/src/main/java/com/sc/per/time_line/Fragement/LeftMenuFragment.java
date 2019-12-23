package com.sc.per.time_line.Fragement;


import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.sc.per.time_line.base.BaseFragment;

/**
 * 左侧菜单Fragment
 */
public class LeftMenuFragment extends BaseFragment {


    private ListView listView;

    @Override
    public View initView() {
        listView = new ListView(context);
        return listView;
    }

    private String[] data = {"随笔", "日记", "展示", "前端", "后端", "liunx", "其他",
            "留言","版本","西瓜"};

    @Override
    public void initData() {
        super.initData();
        ArrayAdapter<String> list_left = new ArrayAdapter(
                context, android.R.layout.simple_list_item_1, data);
        listView.setAdapter(list_left);

        //左侧菜单点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(context, data[position] , Toast.LENGTH_LONG).show();
            }
        });
    }
}
