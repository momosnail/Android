package com.wgl.android.activity;

import android.Manifest;
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
import com.wgl.android.activity.onclick.RepeatOnclickActivity;
import com.wgl.android.activity.ontouch.SeekBarOnTouchActivity;
import com.wgl.android.adapter.MainAdapter;
import com.wgl.tdlib.activity.BaseActivity;

import java.util.ArrayList;

/**
 * 触摸相关Activity类展示列表
 */
public class OnTouchActivity extends BaseActivity {
    private Activity mContext;
    private final String[] mPermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    public static ArrayList<Pair<String, Class<?>>> mArrayList = new ArrayList<>();
    private RecyclerView mRl;
    MainAdapter mAdapter;
    private ImageView mIv_return;
    private TextView mTv_title;

    @Override
    protected void init() {
        mContext = this;
        setContentView(R.layout.onclick_activity);
        initView();
        initData();
    }

    private void initView() {
        mRl = findViewById(R.id.rl);
        mIv_return = findViewById(R.id.iv_return);
        mTv_title = findViewById(R.id.tv_title);
    }


    private void initData() {
        initTitle();
        initRecycleView();
        onclickListener();
    }

    private void initTitle() {
        mTv_title.setText(R.string.onclick);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void onclickListener() {
        mIv_return.setOnClickListener(this);
        mIv_return.setOnTouchListener(this);
    }


    private void initRecycleView() {
        mArrayList.clear();
        mRl.setLayoutManager(new LinearLayoutManager(this));
        mRl.setHasFixedSize(true); //确保每个item尺寸不变
        mAdapter = new MainAdapter(mContext);
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

                if (position == 0) {
                    Intent SeekBarOnTouchIntent = new Intent(OnTouchActivity.this, SeekBarOnTouchActivity.class);
                    startActivity(SeekBarOnTouchIntent);
                }
            }
        });
        mRl.setAdapter(mAdapter);
        mAdapter.setList(getData());
    }

    public ArrayList<Pair<String, Class<?>>> getData() {
        mArrayList.add(new Pair<String, Class<?>>("SeekBarOnTouchActivity", SeekBarOnTouchActivity.class));
        return mArrayList;
    }


    @Override
    public void singleOnclick(View view) {
        int id = view.getId();
        if (id == R.id.iv_return) {
            finish();
        }
    }
}
