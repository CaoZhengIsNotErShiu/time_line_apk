package com.sc.per.time_line.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sc.per.time_line.R;
import com.sc.per.time_line.activity.MainActivity;
import com.sc.per.time_line.entity.ItemBean;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<ItemBean> mList = null;
    private MainActivity mainActivity = null;

    public RecyclerViewAdapter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void setData(List<ItemBean> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        ItemBean itemBean = mList.get(position);
        return itemBean.getType();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = null;
        if (viewType == ItemBean.ITEM_PT) {
            holder = new PtViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_pt, parent, false));
        } else if (viewType == ItemBean.ITEM_GG) {
            holder = new GgViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_gg, parent, false));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        switch (itemViewType) {
            case ItemBean.ITEM_GG:
                GgViewHolder ggHolder = (GgViewHolder) holder;

                mainActivity.setGGViewPosition(position, ggHolder.ggView);
                break;
            case ItemBean.ITEM_PT:

                break;
        }
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 普通
     */
    class PtViewHolder extends ViewHolder {

        public PtViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 广告
     */
    class GgViewHolder extends ViewHolder {
        ImageView ggView;
        View itemView;

        public GgViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ggView = itemView.findViewById(R.id.item_imageview);
        }
    }
}
