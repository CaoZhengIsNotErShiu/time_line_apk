package com.sc.per.time_line.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.sc.per.time_line.SplashActivity;
import com.sc.per.time_line.activity.GuideActivity;

/**
 * 缓存工具类
 * des:缓存软件的一些参数和数据
 */

public class CacheUtils {

    /**
     * 得到缓存值
     * @param context 上下文
     * @param key
     * @return
     */
    public static boolean getBoolean(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("time_line", Context.MODE_PRIVATE);
        return sp.getBoolean(key,false );
    }

    /**
     * 保存缓存值
     * @param context 上下文
     * @param key 名
     * @param b
     */
    public static void putBoolean(Context context, String key, boolean b) {
        SharedPreferences sp = context.getSharedPreferences("time_line", Context.MODE_PRIVATE);
        sp.edit().putBoolean(key,b).commit();
    }
}
