package com.wgl.android.activity.ontouch;

import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.wgl.android.R;
import com.wgl.mylibrary.activity.BaseActivity;

/**
 * 扩大seekbar的触摸范围
 * 注意：seekbar的父布局不能用 wrap_content 要不就不能扩大范围了
 */
public class SeekBarOnTouchActivity extends BaseActivity {

    private RelativeLayout mLl_sb;
    private SeekBar mSb;
    private TextView mTv_title;

    @Override
    protected void init() {
        setContentView(R.layout.sb_touch_activity);
        initView();
        initTouch();
        initOnclick();
        initData();
    }

    private void initData() {
        mTv_title.setText(R.string.seekBarOnTouch);
    }

    private void initOnclick() {
        mTv_title.setOnClickListener(this);
    }

    private void initTouch() {
        mLl_sb.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Rect seekRect = new Rect();
                mSb.getHitRect(seekRect);
                if ((event.getY() >= (seekRect.top - 500)) && (event.getY() <= (seekRect.bottom + 500))) {
                    float y = seekRect.top + seekRect.height() / 2;
                    //seekBar only accept relative x
                    float x = event.getX() - seekRect.left;
                    if (x < 0) {
                        x = 0;
                    } else if (x > seekRect.width()) {
                        x = seekRect.width();
                    }
                    MotionEvent me = MotionEvent.obtain(event.getDownTime(), event.getEventTime(),
                            event.getAction(), x, y, event.getMetaState());
                    return mSb.onTouchEvent(me);
                }
                return false;
            }
        });
    }

    private void initView() {
        mTv_title = findViewById(R.id.tv_title);
        mLl_sb = findViewById(R.id.ll_sb);
        mSb = findViewById(R.id.sb_bg_light);

    }

    @Override
    public void passPermissons() {

    }

    @Override
    public void forbitPermissons() {

    }


}
