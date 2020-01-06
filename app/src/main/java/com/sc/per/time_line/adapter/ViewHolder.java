package com.sc.per.time_line.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ViewHolder {

    private final SparseArray<View> mViews;
    private int mPostion;
    private View mConvertView;
    private Context mContext;

    private ViewHolder(Context context, ViewGroup parent, String layoutId, int position) {
        this.mViews = new SparseArray<>();
        this.mPostion = position;
        this.mContext = context;
        mConvertView = LayoutInflater.from(context).inflate(Integer.valueOf(layoutId), parent, false);
        mConvertView.setTag(this);
    }

    /**
     * 拿到一个ViewHolder对象
     *
     * @param context
     * @param convertView
     * @param parent
     * @param layoutId
     * @param position
     * @return
     */
    public static ViewHolder get(Context context, View convertView, ViewGroup parent, String layoutId, int position) {
        if (convertView == null) {
            return new ViewHolder(context, parent, layoutId, position);
        }
        return (ViewHolder) convertView.getTag();
    }

    /**
     * 通过控件的Id获取对于的控件，如果没有则加入views
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId) {

        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return mConvertView;
    }

    /**
     * 设置文本(可以自己添加设置image bitmap color等)
     */
    public ViewHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public void setVisible(int id_tv_title, boolean b) {

        TextView view = getView(id_tv_title);
        if (b){
            view.setVisibility(View.VISIBLE);
        }else {
            view.setVisibility(View.GONE);
        }


    }
}