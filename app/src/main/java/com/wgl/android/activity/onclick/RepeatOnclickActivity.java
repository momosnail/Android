package com.wgl.android.activity.onclick;

import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.wgl.android.R;
import com.wgl.mylibrary.activity.BaseActivity;

/**
 * 方式1，简单
 * 方式2. RepeatClickUtils工具类 ，更灵活 ，可以添加Toast
 */
public class RepeatOnclickActivity extends BaseActivity {

    private Button mBt1;
    private Button mBt2;
    private LinearLayout mLl;
    private Switch mSw;
    private ImageView mIv_return;
    private TextView mTv_title;

    @Override
    protected void init() {
        setContentView(R.layout.repeat_onclick_activity);
        initView();
        initOnclick();
        initData();
    }

    private void initData() {
        initTitle();
        initButton();
        initSwitch();
    }

    private void initTitle() {
        mTv_title.setText(R.string.repeatOnclick);
    }

    private void initSwitch() {
        mSw.setClickable(false);

        mSw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    toastInfo("打开");
                } else {
                    toastInfo("关闭");
                }
            }
        });
    }

    private void initButton() {
        mBt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBt1.setClickable(false);
                toastInfo("我是方式一");
                new Handler(RepeatOnclickActivity.this.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mBt1.setClickable(true);
                    }
                }, BaseActivity.TIME_INTERVAL);
            }
        });
    }

    private void initOnclick() {
        mIv_return.setOnClickListener(this);
        mIv_return.setOnTouchListener(this);

        mBt2.setOnClickListener(this);
        mBt2.setOnTouchListener(this);

        mLl.setOnClickListener(this);

    }

    private void initView() {
        mBt1 = findViewById(R.id.bt1);
        mBt2 = findViewById(R.id.bt2);
        mLl = findViewById(R.id.ll);
        mSw = findViewById(R.id.sw);
        mIv_return = findViewById(R.id.iv_return);
        mTv_title = findViewById(R.id.tv_title);
    }

    @Override
    public void singleOnclick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.iv_return:
                finish();
                break;
            case R.id.bt2:
                toastInfo("我是方式二");
                break;

            case R.id.ll:
                mSw.setChecked(!mSw.isChecked());
                break;
            default:
                break;
        }
    }

    @Override
    public void passPermissons() {

    }

    @Override
    public void forbitPermissons() {

    }

}
