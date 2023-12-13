package com.wgl.android;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.util.Pair;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.wgl.android.activity.ProtocolActivity;
import com.wgl.android.activity.UtilsActivity;
import com.wgl.android.activity.ViewActivity;
import com.wgl.android.adapter.MainAdapter;
import com.wgl.tdlib.activity.BaseActivity;

import java.security.Permission;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import timber.log.Timber;

/**
 * 主界面
 */
public class MainActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {
    private Activity mContext;
    public static ArrayList<Pair<String, Class<?>>> mArrayList = new ArrayList<>();
    private RecyclerView mRl;
    MainAdapter mAdapter;
    private static final int REQUEST_CODE_READ_WRITE=100;
    @Override
    protected void init() {
        mContext = this;
        setContentView(R.layout.main_activity);
        requestPermissions();
    }

    private void initView() {
        mRl = findViewById(R.id.rl);
    }



    private void initData() {
        initView();
        initRecycleView();
        onclickListener();
    }

    private void onclickListener() {

    }


    private void initRecycleView() {
        mArrayList.clear();
        mRl.setLayoutManager(new LinearLayoutManager(this));
        mRl.setHasFixedSize(true); //确保每个item尺寸不变
        mAdapter = new MainAdapter(mContext);
        mAdapter.setAnimationEnable(true);
        mAdapter.addChildClickViewIds(R.id.rl_item);

        mAdapter.setDiffCallback(new DiffUtil.ItemCallback<Pair<String,Class<?>>>() {
            @Override
            public boolean areItemsTheSame(@NonNull Pair<String,Class<?>> oldItem, @NonNull Pair<String,Class<?>> newItem) {
                return false;
            }

            @Override
            public boolean areContentsTheSame(@NonNull Pair<String,Class<?>> oldItem, @NonNull Pair<String,Class<?>> newItem) {
                return false;
            }
        });

        mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Intent intent=new Intent(mContext,mArrayList.get(position).second);
                startActivity(intent);
            }
        });
        mRl.setAdapter(mAdapter);
        mAdapter.setList(getData());
    }

    public ArrayList<Pair<String, Class<?>>> getData() {
        mArrayList.add(new Pair<String, Class<?>>("Utils", UtilsActivity.class));
        mArrayList.add(new Pair<String, Class<?>>("View", ViewActivity.class));
        mArrayList.add(new Pair<String, Class<?>>("Protocol", ViewActivity.class));

        return mArrayList;
    }

    private void requestPermissions(){
         String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

        //判断有没有权限
        if (EasyPermissions.hasPermissions(this, perms)) {
            Timber.e("所有权限申请通过");
            // 如果有权限了, 就做你该做的事情
            initData();
        } else {
            // 如果没有权限, 就去申请权限
            // this: 上下文
            // Dialog显示的正文
            // RC_CAMERA_AND_RECORD_AUDIO 请求码, 用于回调的时候判断是哪次申请
            // perms 就是你要申请的权限
            EasyPermissions.requestPermissions(this, "需要读写权限,请主人同意", REQUEST_CODE_READ_WRITE, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        for (String perm : perms) {
            if (perm.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                Timber.e("申请通过 WRITE_EXTERNAL_STORAGE");
            }else if (perm.equals(Manifest.permission.READ_EXTERNAL_STORAGE)){
                Timber.e("申请通过 READ_EXTERNAL_STORAGE");
            }
        }

        initData();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this)
                    .setTitle("权限已经被您拒绝")
                    .setRationale("如果不打开权限则无法使用该功能,点击确定去打开权限")
                    .setRequestCode(10001) //用于onActivityResult回调做其它对应相关的操作
                    .build()
                    .show();
        }


    }
}
