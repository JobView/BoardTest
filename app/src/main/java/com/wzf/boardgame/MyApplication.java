package com.wzf.boardgame;

import android.support.multidex.MultiDexApplication;

import com.tencent.bugly.Bugly;
import com.wzf.boardgame.constant.UrlService;

/**
 * @Description:
 * @author: wangzhenfei
 * @date: 2017-06-19 09:15
 */

public class MyApplication extends MultiDexApplication {
    private final String TAG = getClass().getSimpleName();
    private static MyApplication application;
    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        // bug捕获
        intBugly();
    }
    //家里修改的
    private void intBugly() {
        /* Bugly SDK初始化
        * 参数1：上下文对象
        * 参数2：APPID，平台注册时得到,注意替换成你的appId
        * 参数3：是否开启调试模式，调试模式下会输出'CrashReport'tag的日志
        * 第三个参数为SDK调试模式开关，调试模式的行为特性如下：
        * 输出详细的Bugly SDK的Log；
        * 每一条Crash都会被立即上报；
        * 自定义日志将会在Logcat中输出。
        */

        if(UrlService.DEBUG){
            Bugly.init(getApplicationContext(), "900010630", false);
        }else {
            Bugly.init(getApplicationContext(), "900010630", false);
        }
//        String ui = null;
//        ui.toString();
    }

    public  static  MyApplication getAppInstance(){
        return application;
    }
}
