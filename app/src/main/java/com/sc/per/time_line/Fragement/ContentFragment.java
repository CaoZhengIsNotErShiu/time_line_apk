package com.sc.per.time_line.Fragement;


import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioGroup;

import com.sc.per.time_line.R;
import com.sc.per.time_line.base.BaseFragment;
import com.sc.per.time_line.entity.Article;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class ContentFragment extends BaseFragment {

    private List<Article> arts = new ArrayList<>();
    
    private ListView listView_mian;

    @ViewInject(R.id.viewpager)
    private ViewPager viewPager;
    //底部导航
    @ViewInject(R.id.rg_main)
    private RadioGroup rg_main;
    
    @Override
    public View initView() {

        View view = View.inflate(context, R.layout.context_fragment, null);
        //1.将视图注入到框架中，让ContentFragment和view关联
        x.view().inject(this,view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        //设置默认选中首页
        rg_main.check(R.id.rb_btn);
        //主页数据初始化
//        init();
//        ArticleAdapter list_main = new ArticleAdapter(context, R.layout.article_item, arts);
//        listView_mian.setAdapter(list_main);
        //点击事件
//        listView_mian.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                System.out.println("当前点："+ position);
//                Article article = arts.get(position);
//                Toast.makeText(context, article.getUserName() , Toast.LENGTH_LONG).show();
//            }
//        });
    }

    /**
     * 初始化数据
     */
    private void init() {
        arts.add(new Article("暹罗猫", R.drawable.wifi01));
        arts.add(new Article("布偶猫", R.drawable.wifi02));
        arts.add(new Article("苏格兰折耳猫", R.drawable.wifi03));
        arts.add(new Article("暹罗猫", R.drawable.wifi01));
        arts.add(new Article("布偶猫", R.drawable.wifi02));
        arts.add(new Article("苏格兰折耳猫", R.drawable.wifi03));
        arts.add(new Article("暹罗猫", R.drawable.wifi01));
        arts.add(new Article("布偶猫", R.drawable.wifi02));
        arts.add(new Article("苏格兰折耳猫", R.drawable.wifi03));
    }
}
