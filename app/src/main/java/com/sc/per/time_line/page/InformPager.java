package com.sc.per.time_line.page;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.sc.per.time_line.R;
import com.sc.per.time_line.activity.MainActivity;
import com.sc.per.time_line.adapter.RecyclerViewAdapter;
import com.sc.per.time_line.base.BasePager;
import com.sc.per.time_line.entity.ItemBean;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * vip
 */
public class InformPager extends BasePager {


    //adapter中广告控件里的ImageView
    private ImageView ggImageView;

    //广告item所在的位置
    private int ggPosition = -1;

    public InformPager(Context context,ImageView ggImageView,int ggPosition) {
        super(context);
        this.ggImageView = ggImageView;
        this.ggPosition = ggPosition;
    }


    //根据位置读取的广告图片
    private Bitmap ggBitmap;

    //滑动控件
    private RecyclerView mRecyclerView;

    //adapter
    private RecyclerViewAdapter adapter;

    //线性布局
    private LinearLayoutManager linearLayoutManager;

    //用来控制读取范围
    private final Rect mRect = new Rect();

    //读取区域bitmap的类
    private BitmapRegionDecoder mDecoder;

    //需要显示的广告图片的高
    private int imageHeight;

    //需要显示的广告的宽
    private int imageWidth;

    //滑动控件的高
    private int parentHeight;



    public  void setGGViewPosition(int ggPosition, ImageView imageView) {
        this.ggPosition = ggPosition;
        this.ggImageView = imageView;
        System.out.println(ggPosition + "========" + ggImageView);
    }

    @Override
    public void initData() {
        super.initData();

        mRecyclerView  = new RecyclerView(context);
        linearLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        MainActivity mainActivity = (MainActivity) context;

        adapter = new RecyclerViewAdapter(mainActivity);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnScrollListener(new OnScrollLisrener());


        addJia();
        addImageResources();

        tv_View.setText("消息");
        frameLayout.addView(mRecyclerView);

    }

    //假装这里是异步加载的网络图片
    private void addImageResources() {
        try {
            //gg_image2，gg_image3，gg_image4,三张图片的高度不一样，顺序为：从长到短
            @SuppressWarnings("ResourceType")
            InputStream inputStream = context.getResources().openRawResource(R.mipmap.gg_image2);
            mDecoder = BitmapRegionDecoder.newInstance(inputStream, true);
            imageHeight = mDecoder.getHeight();
            imageWidth = mDecoder.getWidth();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    class OnScrollLisrener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            //获取广告的itemView
            View ggView = linearLayoutManager.findViewByPosition(ggPosition);
            if (ggView == null) {
                return;
            }
            if (ggImageView == null) {
                return;
            }
            //获取滑动控件的高
            parentHeight = mRecyclerView.getHeight();

            //图片距离滑动控件的上下距离
            int topOrBottomPadding;

            int top = ggView.getTop();
            int left = 0;
            int right = imageWidth;
            int bottom = ggView.getBottom();

            //如果图片比滑动控件短
            if (parentHeight > imageHeight) {
                //计算图片距离顶部的距离和图片距离底部的距离
                topOrBottomPadding = (parentHeight - imageHeight) / 2;
                //获取item的高
                int itemHeight = ggView.getHeight();
                if (top >= parentHeight - itemHeight - topOrBottomPadding) {
                    //如果超出底部,就一直显示图片的底部
                    bottom = imageHeight;
                    top = bottom - itemHeight;
                } else if (top <= topOrBottomPadding) {
                    //如果超出顶部,就一直显示图片的顶部
                    top = 0;
                    bottom = top + itemHeight;
                } else {
                    //处于图片中的时候,自由滑动
                    top -= topOrBottomPadding;
                    bottom = top + itemHeight;
                }
            }

            System.out.println(left+"=="+top+"==="+right+"==="+bottom);
            mRect.set(left, top, right, bottom);
            //异步（异步时会卡...貌似是因为线程延迟的问题）
//            executorService.execute(bitmapRunnable);

            //同步→
            ggBitmap = mDecoder.decodeRegion(mRect, null);
            if (ggBitmap != null) {
                ggImageView.setImageBitmap(ggBitmap);
            }
            //←同步
        }
    }

    //添加假数据
    private void addJia() {
        List<ItemBean> list = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            list.add(new ItemBean(ItemBean.ITEM_PT, "aaa" + i));
        }
        list.add(new ItemBean(ItemBean.ITEM_GG, "ggg"));
        for (int i = 0; i < 12; i++) {
            list.add(new ItemBean(ItemBean.ITEM_PT, "bbb" + i));
        }
        adapter.setData(list);
    }

}
