package com.wgl.mylibrary.utils;

import android.view.View;

import com.wgl.mylibrary.application.BaseApplication;

/**
 * 防重复点击工具类
 */
public class RepeatClickUtils {
    public static boolean avoidRepeatClick(View view){
        boolean flag = false;
        long lastTime = view.getTag(-1)==null?0:(long)view.getTag(-1);
        if (System.currentTimeMillis()-lastTime> BaseApplication.TIME_INTERVAL){
            flag = true;
            view.setTag(-1,System.currentTimeMillis());
        }

        return flag;
    }
}
