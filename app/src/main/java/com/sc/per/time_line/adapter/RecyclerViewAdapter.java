package com.sc.per.time_line.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sc.per.time_line.R;
import com.sc.per.time_line.SplashActivity;
import com.sc.per.time_line.activity.MainActivity;
import com.sc.per.time_line.entity.Menu;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyHolder> {

    Context context;
    private List<Menu> data;
    private View inflater;

    public RecyclerViewAdapter(Context context, List<Menu> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        inflater = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.inform_item_pager, viewGroup, false);
        MyHolder myHolder = new MyHolder(inflater);
        return myHolder;
    }

    /**
     * 操作item
     * @param myHolder
     * @param i
     */
    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        //将数据和控件绑定
        myHolder.textView.setText(data.get(i).getMenu());

        final int pos = i;
        myHolder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context ,"控件被点击：" + pos , Toast.LENGTH_SHORT ).show();
            }
        });

        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context ,"条目被点击：" + pos , Toast.LENGTH_SHORT ).show();
                //v.getContext().startActivity(new Intent(context, MainActivity.class));
            }
        });
    }

    /**
     * 条数
     * @return
     */
    @Override
    public int getItemCount() {
        return data.size();
    }

    /**
     * 初始化viewHolder控件
     */
    class MyHolder extends RecyclerView.ViewHolder{
        TextView textView;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_view);
        }
    }
}
