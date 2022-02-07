package com.wgl.mylibrary.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * 权限请求工具类
 */
public class PermissionTool {

    private final int     mRequestCode      = 100;// 权限请求码
    private       boolean showSystemSetting = true;

    public boolean isShowSystemSetting() {
        return showSystemSetting;
    }

    public void setShowSystemSetting(boolean showSystemSetting) {
        this.showSystemSetting = showSystemSetting;
    }


    private PermissionTool() {
    }

    private static PermissionTool     permissionsUtils;
    private        IPermissionsResult mPermissionsResult;

    public static PermissionTool getInstance() {
        if (permissionsUtils == null) {
            permissionsUtils = new PermissionTool();
        }
        return permissionsUtils;
    }

    /**
     * 检测权限
     *
     * @param context           上下文
     * @param permissions       是我们请求的权限名称数组
     * @param permissionsResult 实现对象
     */
    public void chekPermissions(Activity context, String[] permissions, @NonNull IPermissionsResult permissionsResult) {
        mPermissionsResult = permissionsResult;

        if (Build.VERSION.SDK_INT < 23) {// 6.0才用动态权限
            permissionsResult.passPermissons();
            return;
        }

        // 创建一个mPermissionList，逐个判断哪些权限未授予，未授予的权限存储到mPerrrmissionList中
        List<String> mPermissionList = new ArrayList<>();
        // 逐个判断你要的权限是否已经通过
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(context, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                Timber.e("chekPermissions: ---------------------------------添加还未授予的权限");
                mPermissionList.add(permissions[i]);// 添加还未授予的权限
            }
        }

        // 申请权限
        if (mPermissionList.size() > 0) {// 有权限没有通过，需要申请
            Timber.e("chekPermissions: ---------------------------------有权限没有通过，需要申请");
            ActivityCompat.requestPermissions(context, permissions, mRequestCode);
        } else {
            // 说明权限都已经通过，可以做你想做的事情去
            Timber.e("chekPermissions: ---------------------------------说明权限都已经通过，可以做你想做的事情去");
            permissionsResult.passPermissons();
        }
    }

    /**
     * 请求权限后回调的方法
     *
     * @param context
     * @param requestCode  是我们自己定义的权限请求码
     * @param permissions  是我们请求的权限名称数组
     * @param grantResults 是我们在弹出页面后是否允许权限的标识数组，数组的长度对应的是权限名称数组的长度，数组的数据0表示允许权限，-1表示我们点击了禁止权限
     */
    public void onRequestPermissionsResult(Activity context, int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Timber.e("onRequestPermissionsResult: ---------------------------------go !");
        boolean hasPermissionDismiss = false;// 有权限没有通过
        if (mRequestCode == requestCode) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == -1) {
                    Timber.e("onRequestPermissionsResult: ---------------------------------hasPermissionDismiss = true");
                    hasPermissionDismiss = true;
                }
            }
            // 如果有权限没有被允许
            if (hasPermissionDismiss) {
                if (showSystemSetting) {
                    Timber.e("onRequestPermissionsResult: --------------------------------- 跳转到系统设置权限页面，或者直接关闭页面，不让他继续访问");
                    showSystemPermissionsSettingDialog(context);// 跳转到系统设置权限页面，或者直接关闭页面，不让他继续访问
                } else {
                    Timber.e("onRequestPermissionsResult: --------------------------------- mPermissionsResult.forbitPermissons()");
                    mPermissionsResult.forbitPermissons();
                }
            } else {
                // 全部权限通过，可以进行下一步操作。。。
                Timber.e("onRequestPermissionsResult: --------------------------------- 全部权限通过，可以进行下一步操作。。。");
                mPermissionsResult.passPermissons();
            }
        }

    }


    /**
     * 不再提示权限时的展示对话框
     */
    AlertDialog mPermissionDialog;

    private void showSystemPermissionsSettingDialog(final Activity context) {
        final String mPackName = context.getPackageName();
        if (mPermissionDialog == null) {
            mPermissionDialog = new AlertDialog.Builder(context)
                    .setMessage("已禁用权限，请手动授予")
                    .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            cancelPermissionDialog();

                            Uri packageURI = Uri.parse("package:" + mPackName);
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                            context.startActivity(intent);
                            context.finish();
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 关闭页面或者做其他操作
                            cancelPermissionDialog();
                            // mContext.finish();
                            mPermissionsResult.forbitPermissons();
                        }
                    })
                    .create();
        }
        mPermissionDialog.show();
    }

    /**
     * 关闭对话框
     */
    private void cancelPermissionDialog() {
        if (mPermissionDialog != null) {
            mPermissionDialog.cancel();
            mPermissionDialog = null;
        }

    }


    public interface IPermissionsResult {
        void passPermissons();// 权限通过

        void forbitPermissons();// 权限拒绝
    }
}
