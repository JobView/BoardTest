package com.wzf.boardgame.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: jack
 * Description:管理所有的栈中的Activity
 */
public class ActivityCollector {

    /**
     * 存放activity的列表
     */
    public static List<Activity> activities = new ArrayList<>();

    /**
     * 添加Activity
     *
     * @param activity
     */
    public static void addActivity(Activity activity) {
        activities.add(activity);
        DebugLog.i("activities after add size: " + activities.size());
    }


    public static Activity getCurrentActivity() {
        Activity currentActivity = null;
       if(activities.size() > 0){
           for(int i = activities.size() - 1 ; i > -1; i-- ){
               currentActivity = activities.get(i);
               if(currentActivity.isFinishing()){
                    activities.remove(i);
               }else {
                   break;
               }
           }
       }
        return currentActivity;
    }

    /**
     * 获得指定activity实例
     *
     * @param clazz Activity 的类对象
     * @return
     */
    public static <T extends Activity> T getActivity(Class<T> clazz) {
        for(Activity activity : activities){
            if(activity.getClass().getSimpleName().equals(clazz.getSimpleName())){
                return (T)activity;
            }
        }
        return null;
    }

    /**
     * 移除activity,代替finish
     *
     * @param activity
     */
    public static void removeActivity(Activity activity) {
        if (activities.contains(activity)) {
            activities.remove(activity);
            DebugLog.i("activities after remove size: " + activities.size());
        }
    }


    /**
     * 移除所有的Activity
     */
    public static void removeAllActivity() {
        if (activities != null && activities.size() > 0) {
            for (Activity activity : activities) {
                if (!activity.isFinishing()) {
                    activity.finish();
                }
            }
        }
        activities.clear();
    }

}
