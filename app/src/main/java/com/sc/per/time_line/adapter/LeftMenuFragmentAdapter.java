package com.sc.per.time_line.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sc.per.time_line.R;
import com.sc.per.time_line.entity.Menu;

import java.util.List;

/**
 * 菜单适配器
 */
public class LeftMenuFragmentAdapter extends BaseAdapter {

    /**
     * 上下文
     */
    private final Context context;

    /**
     * 菜单数据
     */
    private List<Menu> data;

    /**
     * 刚点击的位置
     */
    private int prePosition;

    public LeftMenuFragmentAdapter(Context context, List<Menu> data, int prePosition){
        this.data = data;
        this.context = context;
        this.prePosition = prePosition;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = (TextView) View.inflate(context, R.layout.left_item_menu ,null );
        textView.setText(data.get(position).getMenu());
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
