package com.sc.per.time_line;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sc.per.time_line.activity.GuideActivity;
import com.sc.per.time_line.activity.MainActivity;
import com.sc.per.time_line.utils.CacheUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 进场动画
 */
public class SplashActivity extends Activity {

    public static final String START_MAIN = "start_main";
    private RelativeLayout splash_root;

    private int recLen = 3;//跳过倒计时提示5秒

    private TextView tv;
    Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        splash_root =  findViewById(R.id.splash);
        splash_root.setBackgroundResource(R.drawable.mouse);

        //渐变动画 ，缩放动画，旋转动画
        AlphaAnimation animation = new AlphaAnimation(0, 1);
        //持续播放时间
        animation.setFillAfter(true);

        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setFillAfter(true);

        RotateAnimation rotateAnimation = new RotateAnimation(0,360,
                RotateAnimation.RELATIVE_TO_SELF,0.5f,
                RotateAnimation.RELATIVE_TO_SELF,0.5f);
        rotateAnimation.setFillAfter(true);

        AnimationSet set = new AnimationSet(false);
        set.addAnimation(animation);
        set.addAnimation(scaleAnimation);
        set.addAnimation(rotateAnimation);
        set.setDuration(2000);
        //倒计时跳过 按钮
        tv = findViewById(R.id.tv);

        splash_root.startAnimation(set);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
                task.cancel();

            }
        });

        //监听接口
        set.setAnimationListener(new MyAnimationLister());
    }



    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() { // UI thread
                @Override
                public void run() {
                    recLen--;
                    tv.setText("跳过 " + recLen);
                    if (recLen < 0) {
                        timer.cancel();
                        tv.setVisibility(View.GONE);//倒计时到0隐藏字体
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();
                    }
                }
            });
        }
    };



    /**
     * 动画播放监听接口
     */
    class MyAnimationLister implements Animation.AnimationListener{

        /**
         * 动画开始播放
         * @param animation
         */
        @Override
        public void onAnimationStart(Animation animation) {

        }

        /**
         * 动画结束播放
         * @param animation
         */
        @Override
        public void onAnimationEnd(Animation animation) {
            //判断是否进入过主界面
            boolean flag = CacheUtils.getBoolean(SplashActivity.this, START_MAIN);
            if (flag){
                //2.定义倒数按钮事件
                timer.schedule(task, 1000, 1000);//等待时间一秒，停顿时间一秒
            }else{
                //未进入，进入引导界面
                Intent i = new Intent(SplashActivity.this,GuideActivity.class);
                startActivity(i);
                //关闭splash页面
                finish();
            }

//            Toast.makeText(SplashActivity.this,"星河清梦欢迎您 · " ,Toast.LENGTH_LONG ).show();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

}
