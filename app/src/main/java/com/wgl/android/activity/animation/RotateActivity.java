package com.wgl.android.activity.animation;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wgl.android.R;
import com.wgl.tdlib.activity.BaseActivity;

public class RotateActivity extends BaseActivity {

    private ImageView mIv_return;
    private TextView mTv_title;
    private ImageView mIv_loading;
    private ImageView mIv_demonstration_bg;
    private TextView mTv_demonstration;

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

        mTv_demonstration.setOnClickListener(this);
        mTv_demonstration.setOnTouchListener(this);
    }

    private void initView() {
        mIv_return = findViewById(R.id.iv_return);
        mTv_title = findViewById(R.id.tv_title);
        mIv_loading = findViewById(R.id.iv_loading);
        mIv_demonstration_bg = findViewById(R.id.iv_demonstration_bg); //演示gif
        mTv_demonstration = findViewById(R.id.tv_demonstration);
    }


    /**
     * 初始化动画
     */
    private void initAnimation() {
        Glide.with(RotateActivity.this).load(R.drawable.home_loading).into(mIv_loading);
        // 设置旋转动画1
        Animation animation1 = AnimationUtils.loadAnimation(this, R.anim.loading_animation);
        LinearInterpolator lin1 = new LinearInterpolator();//设置动画匀速运动
        animation1.setInterpolator(lin1);
        mIv_loading.startAnimation(animation1);

        // 设置旋转动画2
        Glide.with(this).load(R.drawable.home_demonstration).into(mIv_demonstration_bg);
        Animation animation2 = AnimationUtils.loadAnimation(this, R.anim.loading_animation);
        LinearInterpolator lin2 = new LinearInterpolator();//设置动画匀速运动
        animation2.setInterpolator(lin2);
        mIv_demonstration_bg.startAnimation(animation2);

    }

    @Override
    public void singleOnclick(View view) {
        int id = view.getId();
        if (id == R.id.iv_return) {
            finish();
        } else if (id == R.id.tv_demonstration) {
            toastInfo("演示");
        }
    }
}
