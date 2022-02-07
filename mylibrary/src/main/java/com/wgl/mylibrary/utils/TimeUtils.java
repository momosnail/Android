package com.wgl.mylibrary.utils;

import android.content.Context;

/**
 * 时间工具类
 */
public class TimeUtils {

    private static TimeUtils timeUtils;
    private Context mContext;

    private TimeUtils(Context context) {
        mContext = context.getApplicationContext();
    }

    public static TimeUtils getInstance(Context context) {
        if (timeUtils == null) {
            timeUtils = new TimeUtils(context);
        }
        return timeUtils;
    }

    /**
     * 秒转换为时分秒 00：00：00
     * @param time  单位：秒
     * @return
     */
    public String timeConversion(int time) {
        int hour = 0;
        int minutes = 0;
        int sencond = 0;
        int temp = time % 3600;
        if (time > 3600) {
            hour = time / 3600;
            if (temp != 0) {
                if (temp > 60) {
                    minutes = temp / 60;
                    if (temp % 60 != 0) {
                        sencond = temp % 60;
                    }
                } else {
                    sencond = temp;
                }
            }
        } else {
            minutes = time / 60;
            if (time % 60 != 0) {
                sencond = time % 60;
            }
        }
        return (hour < 10 ? ("0" + hour) : hour) + ": " + (minutes < 10 ? ("0" + minutes) : minutes) + ": " + (sencond < 10 ? ("0" + sencond) : sencond);
    }
}
