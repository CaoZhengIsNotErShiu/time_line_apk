package com.sc.per.time_line.Fragement;


import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.sc.per.time_line.R;
import com.sc.per.time_line.activity.MainActivity;
import com.sc.per.time_line.base.BaseFragment;
import com.sc.per.time_line.entity.MenuBean;
import com.sc.per.time_line.utils.DensityUtil;

import java.util.List;


/**
 * 左侧菜单Fragment
 */
public class LeftMenuFragment extends BaseFragment {


    private ListView listView;
    private List<MenuBean.DataBean> data;
    private LeftMenuFragmentAdapter adapter;

    /**
     * 刚点击的位置
     */
    private int prePosition;

    @Override
    public View initView() {
        listView = new ListView(context);
        listView.setPadding(0, DensityUtil.dip2px(context,40 ), 0,0 );
        listView.setDividerHeight(0);
        listView.setCacheColorHint(Color.TRANSPARENT);
        listView.setSelector(android.R.color.transparent);

        //设置点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //1.记录点击位置,变色
                prePosition = position;
                adapter.notifyDataSetChanged();
                //2.把左侧菜单关闭
                MainActivity mainActivity = (MainActivity) context;
                mainActivity.getSlidingMenu().toggle();
                //3.切换到对应页面
            }
        });
        return listView;
    }


    @Override
    public void initData() {
        super.initData();
    }

    /**
     * 接收数据
     * @param data
     */
    public void setData(List<MenuBean.DataBean> data) {
        this.data = data;
        adapter = new LeftMenuFragmentAdapter();
        //设置适配器
        listView.setAdapter(adapter);

    }



    //菜单适配器
    class LeftMenuFragmentAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = (TextView) View.inflate(context, R.layout.left_item_menu ,null );
            textView.setText(data.get(position).getMenuText());
            textView.setEnabled(prePosition == position);
            return textView;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }


    }
}
