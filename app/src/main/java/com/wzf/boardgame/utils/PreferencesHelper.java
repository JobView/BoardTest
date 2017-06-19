package com.wzf.boardgame.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.wzf.boardgame.MyApplication;

/**
 * Created by zhenfei.wang on 2016/8/10.
 * sharePreferences 工具箱
 */
public class PreferencesHelper {
    // 通用配置
    public static final String TB_DOUQU_CONFIG = "TB_DOUQU_CONFIG";
    /**
     * 字段
     */
    public static final String HAS_OPENED = "HAS_OPENED"; // 应用是已经开启过了
    // 用户配置
    public static final String TB_USER = "TB_USER";
    // 订单配置
    public static final String TB_ORDER = "TB_ORDER";
    // 系统消息条数
    public static final String TB_UNREAD_MESSAGE = "TB_UNREAD_MESSAGE";
    // 是否有未接来电
    public static final String TB_UNREAD_PHONE = "TB_UNREAD_PHONE";
    // 需要验证的电话订单
    public static final String TB_CHECK_DIAL = "TB_CHECK_DIAL";

    // 首页的引导
    public static final String TB_GUIDE_MAIN = "TB_GUIDE_MAIN";
    // 用户资料引导
    public static final String TB_GUIDE_USER_DETIAL = "TB_GUIDE_USER_DETIAL";


    // 城市选择历史记录
    public static final String TB_CITY_CHANGE = "TB_CITY_CHANGE";
    /**
     * 字段
     */
    public static final String HISTORY_JSON = "HISTORY_JSON"; // 历史的城市选择信息

    //特定文字发布时间间隔
    public static final String TB_DESIGN_MESSAGE = "TB_DESIGN_MESSAGE";


    SharedPreferences mPreferences;
    SharedPreferences.Editor mEditor;
    Context context;
    public PreferencesHelper(Context c, String tbName) {
        context = c;
        mPreferences = context.getSharedPreferences(tbName, 0);
        mEditor = mPreferences.edit();
    }

    public PreferencesHelper(String tbName) {
        context = MyApplication.getAppInstance().getApplicationContext();
        mPreferences = context.getSharedPreferences(tbName, 0);
        mEditor = mPreferences.edit();
    }

    /**
     * 设置参数
     * @param key
     * @param value
     */
    public void setValue(String key, String value) {
        mEditor = mPreferences.edit();
        mEditor.putString(key, value);
        mEditor.commit();

    }

    /**
     * 获取参数
     * @param key
     * @return
     */
    public String getValue(String key) {
        return mPreferences.getString(key, "");
    }


    public void setIntValue(String key, int value){
        mEditor = mPreferences.edit();
        mEditor.putInt(key, value);
        mEditor.commit();
    }

    public int getIntValue(String key){
       return mPreferences.getInt(key, 0);
    }


    public void setLongValue(String key, long value){
        mEditor = mPreferences.edit();
        mEditor.putLong(key, value);
        mEditor.commit();
    }

    public long getLongValue(String key){
        return mPreferences.getLong(key, 0);
    }

    /**
     * 设置boolean值
     * @param key
     * @param value
     */
    public void setBooleanValue(String key, boolean value) {
        mEditor = mPreferences.edit();
        mEditor.putBoolean(key, value);
        mEditor.commit();
    }

    /**
     * 获取boolean值
     * @param key
     * @return
     */
    public boolean getBooleanValue(String key) {
        return mPreferences.getBoolean(key, false);
    }

    /**
     * 带默认值的获取参数
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public String getValue(String key, String defaultValue) {
        if (!mPreferences.contains(key)) {
            return defaultValue;
        }
        return mPreferences.getString(key, defaultValue);
    }

    public void remove(String name) {
        mEditor.remove(name);

    }

    public void clearHelper() {
        mEditor = mPreferences.edit();
        mEditor.clear();
        mEditor.commit();
    }
}
