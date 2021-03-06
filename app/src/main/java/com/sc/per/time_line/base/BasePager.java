package com.sc.per.time_line.base;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sc.per.time_line.R;
import com.sc.per.time_line.activity.MainActivity;


/**
 * 公共类
 */
public class BasePager {

    /**
     * 上下文
     */
    public final Context context;

    /**
     * 视图
     * @param context
     */
    public View root_view;

    /**
     * 显示标题
     */
    public TextView tv_View;
//    public SearchView tv_View;

    /**
     * 菜单
     */
    public ImageButton imageButton;

    /**
     * 子页面
     */
    public FrameLayout frameLayout;

    /**
     * 图部布局
     */
    public RelativeLayout rlTitle;
    public BasePager(Context context){
        this.context = context;
        root_view = initView();
    }

    /**
     * 用于初始化公共视图
     *
     * @return
     */
    private View initView(){
        //公共页面
        View view = View.inflate(context,R.layout.base_pager,null);
        tv_View =  view.findViewById(R.id.iv_title);
        imageButton =  view.findViewById(R.id.ib_menu);
        rlTitle = view.findViewById(R.id.rl_title);
        frameLayout =  view.findViewById(R.id.fl_content);
        imageButton.setVisibility(View.VISIBLE);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) context;
                mainActivity.getSlidingMenu().toggle();
            }
        });
        return view;
    }

    /**
     * 初始化数据
     */
    public void initData(){

    }
}
