package com.wzf.boardgame.function;


import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * Created by wzf on 2017/8/6.
 */

public class CountTimeDownManager {
    private static int count;
    private static TimeCountDownListener listener;
    private static Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(listener != null){
                listener.time(count);
            }
            if(count >= 0){
                count --;
                handler.sendEmptyMessageDelayed(0, 1000);
            }else{
                count = 0;
            }

        }
    };
    public static void setListener(TimeCountDownListener listener){
        CountTimeDownManager.listener = listener;
    }

    public static void start(int count){
        if(CountTimeDownManager.count <= 0){
            CountTimeDownManager.count = count;
            handler.sendEmptyMessage(0);
        }

    }

    public static void stop(){
        CountTimeDownManager.listener = null;
    }

    public interface TimeCountDownListener{
        void time(int count);
    }
}
