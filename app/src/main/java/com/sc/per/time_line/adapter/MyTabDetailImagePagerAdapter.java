package com.sc.per.time_line.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sc.per.time_line.R;
import com.sc.per.time_line.entity.Article;
import com.sc.per.time_line.utils.Constants;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

/**
 * 随笔界面适配器
 */
public class MyTabDetailImagePagerAdapter extends PagerAdapter {


    private final Context context;
    private List<Article.DataBean.ListBean> article;

    private ImageOptions imageOptions;

    public MyTabDetailImagePagerAdapter(Context context,  List<Article.DataBean.ListBean> article) {
        this.context = context;
        this.article = article;
        imageOptions = new ImageOptions.Builder()
                .setSize(DensityUtil.dip2px(200), DensityUtil.dip2px(200))
                .setRadius(DensityUtil.dip2px(5))
                .setCrop(true)
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setLoadingDrawableId(R.drawable.guide1)
                .setFailureDrawableId(R.drawable.guide_2)
                .build();
    }

    @Override
    public int getCount() {
        return article.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(container.getContext());
//        imageView.setBackgroundResource(R.drawable.guide1);
//        imageView.setImageResource(mImage[position]);//ImageView设置图片
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);//铺满屏幕
        container.addView(imageView); // 添加到ViewPager容器
        String s = (String) article.get(position).getThematicUrl();
        String url = Constants.TIME_LINE_CLOUD + s;
        x.image().bind(imageView, url,imageOptions);
        return imageView;// 返回填充的View对象
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
