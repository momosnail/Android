package com.wgl.android.activity.view.animation;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wgl.android.R;
import com.wgl.mylibrary.activity.BaseActivity;

public class RotateActivity extends BaseActivity {

    private ImageView mIv_return;
    private TextView mTv_title;
    private ImageView mIv_loading;

    @Override
    protected void init() {
        setContentView(R.layout.rotate_activity);
        initView();
        initOnclick();
        initData();
    }

    private void initData() {
        initTitle();
        initAnimation();
    }

    private void initTitle() {
        mTv_title.setText(R.string.rotate);
    }

    private void initOnclick() {
        mIv_return.setOnClickListener(this);
        mIv_return.setOnTouchListener(this);

    }

    private void initView() {
        mIv_return = findViewById(R.id.iv_return);
        mTv_title = findViewById(R.id.tv_title);
        mIv_loading = findViewById(R.id.iv_loading);
    }

    @Override
    public void passPermissons() {

    }

    @Override
    public void forbitPermissons() {

    }

    /**
     * 初始化动画
     */
    private void initAnimation() {
        Glide.with(RotateActivity.this).load(R.drawable.home_loading).into(mIv_loading);
        // 设置右上角旋转动画
        Animation animation1 = AnimationUtils.loadAnimation(this, R.anim.loading_animation);
        LinearInterpolator lin1 = new LinearInterpolator();//设置动画匀速运动
        animation1.setInterpolator(lin1);
        mIv_loading.startAnimation(animation1);

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
}
