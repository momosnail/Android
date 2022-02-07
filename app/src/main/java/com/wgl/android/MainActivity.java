package com.wgl.android;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.wgl.android.activity.UtilsActivity;
import com.wgl.android.adapter.MainAdapter;
import com.wgl.mylibrary.activity.BaseActivity;
import com.wgl.mylibrary.utils.TimeUtils;

import java.util.ArrayList;

/**
 * 主界面
 */
public class MainActivity extends BaseActivity {
    private Activity mContext;
    private String[] mPermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    public static ArrayList<String> arrayList = new ArrayList<>();
    private int count = 0;
    private RecyclerView mRl;
    MainAdapter mAdapter;

    @Override
    protected void init() {
        mContext = this;
        setContentView(R.layout.main_activity);
        String s = TimeUtils.getInstance(mContext).timeConversion(40);
        System.out.println("--------------------------------" + s);
        hasPermission(mPermissions);
    }

    private void initView() {
        mRl = findViewById(R.id.rl);
    }

    @Override
    public void passPermissons() { //权限通过
        if (count == 0) {
            ++count;
            initData();
        }
    }

    private void initData() {
        initView();
        initRecycleView();
        onclickListener();
    }

    private void onclickListener() {

    }

    @Override
    public void forbitPermissons() {

    }

    private void initRecycleView() {
        arrayList.clear();
        mRl.setLayoutManager(new LinearLayoutManager(this));
        mRl.setHasFixedSize(true); //确保每个item尺寸不变
        mAdapter = new MainAdapter(mContext);
        mAdapter.setAnimationEnable(true);
        mAdapter.addChildClickViewIds(R.id.rl_item);

        mAdapter.setDiffCallback(new DiffUtil.ItemCallback<String>() {
            @Override
            public boolean areItemsTheSame(@NonNull String oldItem, @NonNull String newItem) {
                return false;
            }

            @Override
            public boolean areContentsTheSame(@NonNull String oldItem, @NonNull String newItem) {
                return false;
            }
        });

        mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                switch (position) {
                    case 0:
                        Intent intent0=new Intent(mContext, UtilsActivity.class); //跳转到工具列表页面
                        startActivity(intent0);
                        break;
                    default:
                        break;
                }
            }
        });
        mRl.setAdapter(mAdapter);
        mAdapter.setList(getData());
    }

    public ArrayList<String> getData() {
        arrayList.add("Utils");
        return arrayList;
    }

}
