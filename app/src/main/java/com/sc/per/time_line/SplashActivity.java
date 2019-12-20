package com.sc.per.time_line;

import android.app.Activity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class SplashActivity extends Activity {

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
            Toast.makeText(SplashActivity.this,"星河清梦欢迎您 · " ,Toast.LENGTH_LONG ).show();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

}
