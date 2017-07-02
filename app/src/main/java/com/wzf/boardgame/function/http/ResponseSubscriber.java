package com.wzf.boardgame.function.http;

import android.app.Activity;
import android.os.Looper;

import com.wzf.boardgame.function.http.dto.response.BaseResponse;
import com.wzf.boardgame.utils.DebugLog;

import java.lang.ref.WeakReference;

import rx.Subscriber;


/**
 * @Description:
 * @author: wangzhenfei
 * @date: 2017-04-17 17:06
 */

public class ResponseSubscriber<T> extends Subscriber<BaseResponse<T>> {
    public static final int REQUEST_SUCCESS = 200;
    public static int NET_OR_SERVER_ERROR = 0X4562;
    private NetRequestWaitDialog dialog;
    private WeakReference<Activity> contextWeakReference;
    private boolean showDialog;

    public ResponseSubscriber() {
    }

    public ResponseSubscriber(Activity activity, boolean showDialog) {
        contextWeakReference = new WeakReference<Activity>(activity);
        this.showDialog = showDialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Looper.myLooper() == Looper.getMainLooper()) {
            if (showDialog && contextWeakReference.get() != null) {
                dialog = new NetRequestWaitDialog(contextWeakReference.get(), "数据加载中...");
                dialog.show();
            }
        }
    }

    @Override
    public void onCompleted() {
        if (dialog != null) {
            dialog.dismiss();
        }
        contextWeakReference = null;
    }

    @Override
    public final void onError(Throwable e) {
        if (dialog != null) {
            dialog.dismiss();
        }
        try {
//            onFailure(NET_OR_SERVER_ERROR, e.toString());
            DebugLog.e("OKHTTP",e.toString());
            onFailure(NET_OR_SERVER_ERROR, "请求出了点小状况哦，请稍后重试");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public final void onNext(BaseResponse<T> t) {
        if (t != null) {
            if (t.getResultCode() == REQUEST_SUCCESS) {
                try {
                    onSuccess(t.getResponse());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    onFailure(t.getResultCode(), t.getMsg());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void onSuccess(T t) throws Exception {
    }

    public void onFailure(int code, String message) throws Exception {
    }
}
