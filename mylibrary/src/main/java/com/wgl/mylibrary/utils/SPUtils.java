package com.wgl.mylibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.wgl.mylibrary.application.BaseApplication;

public class SPUtils {

    // 整个工具类没有考虑过懒加载，主要因为这个工具类在App启动后基本上会马上使用，所以懒加载的实际意义不大
    private final static String            SHARED_PREFERENCES_NAME = "picture_settings";
    // 这里的BaseApplication.getInstance()就是获取Application的实例
    private static       SharedPreferences mSharedPreferences      = BaseApplication.getInstance().getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
    private static       Gson              mGson                   = new Gson();

    /**
     * 保存键值对
     *
     * @param key   键
     * @param value 值
     */
    public static void applyValue(String key, Object value) {
        if (TextUtils.isEmpty(key))
            return;
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        if (value instanceof Integer) {
            mEditor.putInt(key, (Integer) value).apply();
        } else if (value instanceof Long) {
            mEditor.putLong(key, (Long) value).apply();
        } else if (value instanceof Float) {
            mEditor.putFloat(key, (Float) value).apply();
        } else if (value instanceof Boolean) {
            mEditor.putBoolean(key, (Boolean) value).apply();
        } else if (value instanceof String) {
            mEditor.putString(key, (String) value).apply();
        } else {
            try {
                mEditor.putString(key, mGson.toJson(value)).apply();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 保存键值对(旧)
     * @param key
     * @param value
     */
    public static void commitValue(String key, Object value) {
        if (TextUtils.isEmpty(key))
            return;
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        if (value instanceof Integer) {
            mEditor.putInt(key, (Integer) value).commit();
        } else if (value instanceof Long) {
            mEditor.putLong(key, (Long) value).commit();
        } else if (value instanceof Float) {
            mEditor.putFloat(key, (Float) value).commit();
        } else if (value instanceof Boolean) {
            mEditor.putBoolean(key, (Boolean) value).commit();
        } else if (value instanceof String) {
            mEditor.putString(key, (String) value).commit();
        } else {
            try {
                mEditor.putString(key, mGson.toJson(value)).commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取对应key的value
     *
     * @param key      键
     * @param defValue 默认值
     * @return 返回存储对象
     */
    public static Object getValue(String key, Object defValue) {
        if (TextUtils.isEmpty(key))
            return null;
        if (defValue instanceof Integer) {
            return mSharedPreferences.getInt(key, (Integer) defValue);
        } else if (defValue instanceof Long) {
            return mSharedPreferences.getLong(key, (Long) defValue);
        } else if (defValue instanceof Float) {
            return mSharedPreferences.getFloat(key, (Float) defValue);
        } else if (defValue instanceof Boolean) {
            return mSharedPreferences.getBoolean(key, (Boolean) defValue);
        } else if (defValue instanceof String) {
            return mSharedPreferences.getString(key, (String) defValue);
        } else {
            try {
                return mGson.fromJson(mSharedPreferences.getString(key, ""), defValue.getClass());
            } catch (Exception e) {
                e.printStackTrace();
                return defValue;
            }
        }
    }
}
