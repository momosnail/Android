package com.wgl.mylibrary.application;

import android.app.Application;
import android.graphics.Typeface;
import android.os.Handler;
import android.view.Gravity;

import androidx.core.content.res.ResourcesCompat;

import com.wgl.mylibrary.R;

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
        AutoSizeConfig.getInstance()
                .setDesignWidthInDp(711)
                .setDesignHeightInDp(1138);
        Typeface tf = ResourcesCompat.getFont(this, R.font.pingfang_regular);
        Toasty.Config.getInstance()
                .tintIcon(false)
                .setToastTypeface(tf)
                .setTextSize(14)
                .allowQueue(false)
                .setGravity(Gravity.CENTER,0,0)
                .apply();

    }
}
