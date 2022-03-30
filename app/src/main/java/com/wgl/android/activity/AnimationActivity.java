package com.wgl.android.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.wgl.android.R;
import com.wgl.android.activity.animation.RotateActivity;
import com.wgl.android.adapter.UtilsAdapter;
import com.wgl.mylibrary.activity.BaseActivity;

import java.util.ArrayList;

/**
 * 工具类展示列表
 */
public class AnimationActivity extends BaseActivity {
    private Activity mContext;
    private String[] mPermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    public static ArrayList<String> arrayList = new ArrayList<>();
    private int count = 0;
    private RecyclerView mRl;
    UtilsAdapter mAdapter;
    private TextView mTv_title;
    private ImageView mIv_return;

    @Override
    protected void init() {
        mContext = this;
        setContentView(R.layout.animation_activity);
        initView();
        initData();
    }

    private void initView() {
        mRl = findViewById(R.id.rl);
        mTv_title = findViewById(R.id.tv_title);
        mIv_return = findViewById(R.id.iv_return);
    }

    @Override
    public void passPermissons() { //权限通过
        if (count == 0) {
            ++count;
            initData();
        }
    }

    private void initData() {
        initTitle();
        initRecycleView();
        onclickListener();
    }

    private void onclickListener() {
        mIv_return.setOnClickListener(this);
        mIv_return.setOnTouchListener(this);
    }

    private void initTitle() {
        mTv_title.setText(R.string.animation);
    }

    @Override
    public void singleOnclick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.iv_return:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void forbitPermissons() {

    }

    private void initRecycleView() {
        arrayList.clear();
        mRl.setLayoutManager(new LinearLayoutManager(this));
        mRl.setHasFixedSize(true); //确保每个item尺寸不变
        mAdapter = new UtilsAdapter(mContext);
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
                        Intent intent0 = new Intent(AnimationActivity.this, RotateActivity.class);
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
        arrayList.add("RotateActivity");
        return arrayList;
    }
}