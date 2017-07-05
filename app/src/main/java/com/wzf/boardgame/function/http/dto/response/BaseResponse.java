package com.wzf.boardgame.function.http.dto.response;

import android.text.TextUtils;

/**
 * @Description:
 * @author: wangzhenfei
 * @date: 2017-04-17 17:13
 */

public class BaseResponse<T> {
    private int resultCode;
    private String msg;
    private T response;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getMsg() {
        return TextUtils.isEmpty(msg) ? "" : msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "resultCode=" + resultCode +
                ", msg='" + msg + '\'' +
                ", response=" + response +
                '}';
    }
}