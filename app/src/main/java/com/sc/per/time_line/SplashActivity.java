package com.sc.per.time_line;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.sc.per.time_line.activity.GuideActivity;
import com.sc.per.time_line.activity.MainActivity;
import com.sc.per.time_line.utils.CacheUtils;

public class SplashActivity extends Activity {

    public static final String START_MAIN = "start_main";
    private RelativeLayout splash_root;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splash_root =  findViewById(R.id.splash);

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

        splash_root.startAnimation(set);

        //监听接口
        set.setAnimationListener(new MyAnimationLister());
    }

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
                //2.跳转到主界面
                startActivity(new Intent(SplashActivity.this, MainActivity.class));

            }else{
                //未进入，进入引导界面
                Intent i = new Intent(SplashActivity.this,GuideActivity.class);
                startActivity(i);
            }
            //关闭splash页面
            finish();
//            Toast.makeText(SplashActivity.this,"星河清梦欢迎您 · " ,Toast.LENGTH_LONG ).show();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

}
