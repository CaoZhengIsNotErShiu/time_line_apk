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
import com.sc.per.time_line.page.HomePager;
import com.sc.per.time_line.utils.DensityUtil;

import java.util.List;


/**
 * 左侧菜单Fragment
 */
public class LeftMenuFragment extends BaseFragment {


    private ListView listView;
    private List<String> data;
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
                //3.切换到对应页面，详情页面，前端，后端 ，liunx 等
                switchPager(prePosition);

            }
        });
        return listView;
    }

    /**
     * 根据不同位置，切换不同左侧菜单详情页面
     * @param position
     */
    private void switchPager(int position) {
        MainActivity mainActivity = (MainActivity) context;
        ContentFragment contentFragment = mainActivity.getContentFragment();
        HomePager homePager = contentFragment.getFrontPager();
        homePager.switchPager(position);
    }


    @Override
    public void initData() {
        super.initData();
    }

    /**
     * 接收数据
     * @param data
     */
    public void setData(List<String> data) {
        this.data = data;
        adapter = new LeftMenuFragmentAdapter();
        //设置适配器
        listView.setAdapter(adapter);

        //设置默认页面
        switchPager(prePosition);

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
            textView.setText(data.get(position));
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
