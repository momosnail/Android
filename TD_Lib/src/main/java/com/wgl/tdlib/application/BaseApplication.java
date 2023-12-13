package com.wgl.tdlib.application;

import android.app.Application;
import android.graphics.Typeface;
import android.os.Handler;
import android.view.Gravity;

import androidx.core.content.res.ResourcesCompat;


import com.wgl.tdlib.R;
import com.wgl.tdlib.utils.TimberUtils;

import java.util.Objects;

import es.dmoral.toasty.Toasty;
import me.jessyan.autosize.AutoSizeConfig;

public class BaseApplication extends Application {

    private static BaseApplication sInstance;
    private Handler mHandler;
    public static int TIME_INTERVAL=2000;
    public static BaseApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        sInstance = this;

        // 初始化自动化布局
        AutoSizeConfig.getInstance()
                .setDesignWidthInDp(711)
                .setDesignHeightInDp(1138);

        // 初始化全局字体
        Typeface tf = ResourcesCompat.getFont(this, R.font.pingfang_regular);

        // 初始化Toast
        Toasty.Config.getInstance()
                .tintIcon(false)
                .setToastTypeface(Objects.requireNonNull(tf))
                .setTextSize(14)
                .allowQueue(false)
                .setGravity(Gravity.CENTER,0,0)
                .apply();

        // 初始化Timber日志库
        TimberUtils.configTimber();

    }
}
