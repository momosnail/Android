package com.wgl.mylibrary.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.wgl.mylibrary.R;
import com.wgl.mylibrary.utils.ActivityUtil;
import com.wgl.mylibrary.utils.NetBroadcastReceiver;
import com.wgl.mylibrary.utils.PermissionTool;
import com.wgl.mylibrary.utils.RepeatClickUtils;
import com.wgl.mylibrary.utils.ToastUtils;

import es.dmoral.toasty.Toasty;

/**
 * BaseActivity是所有Activity的基类，把一些公共的方法放到里面，如基础样式设置，权限封装，网络状态监听等
 * <p>
 */


public abstract class BaseActivity extends Activity implements NetBroadcastReceiver.NetChangeListener, PermissionTool.IPermissionsResult, View.OnTouchListener, View.OnClickListener {
    public static NetBroadcastReceiver.NetChangeListener netEvent;// 网络状态改变监听事件
    public Activity mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
        hideBottomUIMenu();
        // 隐藏标题栏
     /*   if (getSupportActionBar() != null)
            getSupportActionBar().hide();*/

        /*// 沉浸效果
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }*/

        // 添加到Activity工具类
        ActivityUtil.getInstance().addActivity(this);

        // 初始化netEvent
        netEvent = this;

        // 执行初始化方法
        init();
    }

    // 抽象 - 初始化方法，可以对数据进行初始化
    protected abstract void init();

    public void singleOnclick(View view) {
    }

    private long mLastClickTime = 0;
    public static final int TIME_INTERVAL = 300;

    @Override
    public void onClick(View view) {
        if (RepeatClickUtils.avoidRepeatClick(view)){
            singleOnclick(view);
        }else {
            toastWarning(R.string.quick_onclick_msg);
        }
    }


    final float scale = 0.9f;
    final long duration = 150;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                v.animate().scaleX(scale).scaleY(scale).setDuration(duration).start();
                break;
            case MotionEvent.ACTION_UP:

            case MotionEvent.ACTION_CANCEL:
                v.animate().scaleX(1).scaleY(1).setDuration(duration).start();
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 设置为横屏
       /* if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        super.onResume();
        Resources resources = this.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.fontScale = 1;
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());*/
    }


    @Override
    protected void onDestroy() {
        // Activity销毁时，提示系统回收
        // System.gc();
        netEvent = null;
        // 移除Activity
        ActivityUtil.getInstance().removeActivity(this);
        super.onDestroy();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 点击手机上的返回键，返回上一层
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 移除Activity
            ActivityUtil.getInstance().removeActivity(this);
            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 权限检查方法，false代表没有该权限，ture代表有该权限
     */
    public void hasPermission(String... permissions) {
//        for (String permission : permissions) {
//            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
//                return false;
//            }
//        }
//        return true;
        PermissionTool.getInstance().chekPermissions(this, permissions, this);
    }


    /**
     * 权限请求方法
     */
    public void requestPermission(int code, String... permissions) {
        ActivityCompat.requestPermissions(this, permissions, code);
    }

    /**
     * 处理请求权限结果事件
     *
     * @param requestCode  请求码
     * @param permissions  权限组
     * @param grantResults 结果集
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        doRequestPermissionsResult(requestCode, grantResults);
        PermissionTool.getInstance().onRequestPermissionsResult(this, requestCode, permissions, grantResults);

    }

    /**
     * 处理请求权限结果事件
     *
     * @param requestCode  请求码
     * @param grantResults 结果集
     */
    public void doRequestPermissionsResult(int requestCode, @NonNull int[] grantResults) {
    }

    /**
     * 网络状态改变时间监听
     *
     * @param netWorkState true有网络，false无网络
     */
    @Override
    public void onNetChange(boolean netWorkState) {
    }

    // 隐藏虚拟按键，并且全屏
    @SuppressLint("ObsoleteSdkInt")
    protected void hideBottomUIMenu() {
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            // for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    // Toast
    public void toast(String msg) {
        ToastUtils.showToast(mContext, msg);
    }

    public void toastError(String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toasty.error(mContext, msg, Toast.LENGTH_SHORT, true).show();
            }
        });
    }

    public void toastError(int msgId) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String msg = getResources().getString(msgId);
                Toasty.error(mContext, msg, Toast.LENGTH_SHORT, true).show();
            }
        });

    }

    public void toastWarning(String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toasty.warning(mContext, msg, Toast.LENGTH_SHORT, true).show();
            }
        });
    }

    public void toastWarning(int msgId) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String msg = getResources().getString(msgId);
                Toasty.warning(mContext, msg, Toast.LENGTH_SHORT, true).show();
            }
        });

    }

    public void toastInfo(String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toasty.info(mContext, msg, Toast.LENGTH_SHORT, true).show();

            }
        });
    }

    public void toastInfo(int msgId) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String msg = getResources().getString(msgId);
                Toasty.info(mContext, msg, Toast.LENGTH_SHORT, true).show();
            }
        });

    }

    public void toastNormal(String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toasty.normal(mContext, msg).show();

            }
        });
    }

    public void toastNormal(int msgId) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String msg = getResources().getString(msgId);
                Toasty.normal(mContext, msg).show();
            }
        });

    }

    public void toastNormalIcon(String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Drawable icon = getResources().getDrawable(R.drawable.ic_pets_white_48dp);
                Toasty.normal(mContext, msg, icon).show();
            }
        });

    }

    public void toastNormalIcon(int msgId) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String msg = getResources().getString(msgId);
                Drawable icon = getResources().getDrawable(R.drawable.ic_pets_white_48dp);
                Toasty.normal(mContext, msg, icon).show();
            }
        });

    }

}