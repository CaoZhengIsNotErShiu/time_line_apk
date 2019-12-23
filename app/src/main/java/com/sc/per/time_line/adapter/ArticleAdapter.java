package com.sc.per.time_line.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sc.per.time_line.R;
import com.sc.per.time_line.entity.Article;

import java.util.List;

public class ArticleAdapter extends ArrayAdapter<Article> {

    private int resourceId;

    public ArticleAdapter(Context context, int resource, List<Article> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        Article cat = getItem(position);//获取当前项的实例
//        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
//        ((ImageView) view.findViewById(R.id.image)).setImageResource(cat.getImage());
//        ((TextView) view.findViewById(R.id.name)).setText(cat.getUserName());
//        return view;

        Article cat = getItem(position);//获取当前项的实例
        View view;
        ViewHolder viewHolder;

        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.image =  view.findViewById(R.id.image);
            viewHolder.name =  view.findViewById(R.id.name);
            view.setTag(viewHolder);//保存
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();//取出
        }

        viewHolder.image.setImageResource(cat.getImage());
        viewHolder.name.setText(cat.getUserName());
        return view;
    }

    private class ViewHolder {
        ImageView image;
        TextView name;
    }

}
