package com.wgl.android.activity;

import android.view.View;
import android.widget.TextView;

import com.wgl.android.R;
import com.wgl.mylibrary.activity.BaseActivity;
import com.wgl.mylibrary.utils.TimeUtils;

import java.util.Random;

public class TimeUtilsActivity extends BaseActivity {

    private TextView mTv_time_convert;

    @Override
    protected void init() {
        setContentView(R.layout.timeutils_activity);

        initView();
        initOnclick();
        initData();
    }

    private void initOnclick() {
        mTv_time_convert.setOnClickListener(this);
        mTv_time_convert.setOnTouchListener(this);
    }

    private void initData() {

    }

    private void initView() {
        mTv_time_convert = findViewById(R.id.tv_time_convert);
    }

    @Override
    public void passPermissons() {

    }

    @Override
    public void forbitPermissons() {

    }

    @Override
    public void singleOnclick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.tv_time_convert:
//                double random = Math.random();random.nextInt(int bound);
                Random random=new Random();
                int time = random.nextInt(80);
                String timeConversion = TimeUtils.getInstance(mContext).timeConversion(time);
                mTv_time_convert.setText(timeConversion);
                break;
        }
    }
}
