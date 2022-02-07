package com.wgl.android.adapter;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.wgl.android.R;

public class MainAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    final float scale = 0.9f;
    final long duration = 150;
    private Activity mActivity;

    /*public MainAdapter(int layoutResId) {
        super(layoutResId);

    }*/

    public MainAdapter(Activity activity) {
        super(R.layout.main_item);
        mActivity = activity;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, String s) {
        baseViewHolder.getView(R.id.rl_item).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        v.animate().scaleX(scale).scaleY(scale).setDuration(duration).start();

                        break;
                    case MotionEvent.ACTION_UP:

                    case MotionEvent.ACTION_CANCEL:
                        v.animate().scaleX(1).scaleY(1).setDuration(duration).start();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

        TextView iv_main_item_text = baseViewHolder.getView(R.id.iv_main_item_text);
        iv_main_item_text.setText(s);

    }

    @Override
    public int getItemPosition(@Nullable String item) {
        return super.getItemPosition(item);
    }

    @Override
    protected int getDefItemCount() {
        return super.getDefItemCount();
    }
}
