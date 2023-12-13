package com.wgl.android.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.util.Pair;
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
import com.wgl.android.adapter.ProtocolAdapter;
import com.wgl.tdlib.activity.BaseActivity;

import java.util.ArrayList;

/**
 * 通讯协议相关Activity类展示列表
 */
public class ProtocolActivity extends BaseActivity {
    private Activity mContext;
    public static ArrayList<Pair<String, Class<?>>> mArrayList = new ArrayList<>();
    private RecyclerView mRl;
    ProtocolAdapter mAdapter;
    private TextView mTv_title;
    private ImageView mIv_return;

    @Override
    protected void init() {
        mContext = this;
        setContentView(R.layout.view_activity);
        initView();
        initData();
    }

    private void initView() {
        mRl = findViewById(R.id.rl);
        mTv_title = findViewById(R.id.tv_title);
        mIv_return = findViewById(R.id.iv_return);
    }


    private void initData() {
        initTitle();
        initRecycleView();
        onclickListener();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void onclickListener() {
        mIv_return.setOnClickListener(this);
        mIv_return.setOnTouchListener(this);
    }

    private void initTitle() {
        mTv_title.setText(R.string.view);
    }

    @Override
    public void singleOnclick(View view) {
        int id = view.getId();
        if (id == R.id.iv_return) {
            finish();
        }
    }



    private void initRecycleView() {
        mArrayList.clear();
        mRl.setLayoutManager(new LinearLayoutManager(this));
        mRl.setHasFixedSize(true); //确保每个item尺寸不变
        mAdapter = new ProtocolAdapter(mContext);
        mAdapter.setAnimationEnable(true);
        mAdapter.addChildClickViewIds(R.id.rl_item);
        mAdapter.setDiffCallback(new DiffUtil.ItemCallback<Pair<String, Class<?>>>() {
            @Override
            public boolean areItemsTheSame(@NonNull Pair<String, Class<?>> oldItem, @NonNull Pair<String, Class<?>> newItem) {
                return false;
            }

            @Override
            public boolean areContentsTheSame(@NonNull Pair<String, Class<?>> oldItem, @NonNull Pair<String, Class<?>> newItem) {
                return false;
            }
        });
        mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {

                switch (position) {
                    case 0:
                        Intent OnclickIntent = new Intent(ProtocolActivity.this, OnclickActivity.class);
                        startActivity(OnclickIntent);
                        break;
                    case 1:
                        Intent AnimationIntent = new Intent(ProtocolActivity.this, AnimationActivity.class);
                        startActivity(AnimationIntent);
                        break;
                    case 2:
                        Intent OnTouchIntent = new Intent(ProtocolActivity.this, OnTouchActivity.class);
                        startActivity(OnTouchIntent);
                    default:
                        break;
                }
            }
        });
        mRl.setAdapter(mAdapter);
        mAdapter.setList(getData());
    }

    public ArrayList<Pair<String, Class<?>>> getData() {
        mArrayList.add(new Pair<>("USBProtocol", USBProtocolActivity.class));
        return mArrayList;
    }
}
