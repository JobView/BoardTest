package com.wzf.boardgame.utils;

import android.util.Log;
import android.widget.Toast;

import com.wzf.boardgame.MyApplication;
import com.wzf.boardgame.constant.UrlService;

/**
 * zhenfei.wang
 * 调试打印信息
 */

public class DebugLog {

    public static void v(String tag, String msg) {
        if (UrlService.DEBUG) {
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (UrlService.DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (UrlService.DEBUG) {
            Log.i(tag, msg);
        }
    }

    public static void i(String msg) {
        if (UrlService.DEBUG) {
            Log.i("DEBUG", msg);
        }
    }

    public static void w(String tag, String msg) {
        if (UrlService.DEBUG) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (UrlService.DEBUG) {
            Log.e(tag, msg);
        }
    }

    /**
     * 在开发阶段的测试toast
     * @param s
     */
    public static void toast(String s){
        if(UrlService.DEBUG){
            Toast.makeText(MyApplication.getAppInstance().getApplicationContext(),
                    s,
                    Toast.LENGTH_LONG).show();
        }
    }

}
