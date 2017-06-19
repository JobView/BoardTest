package com.wzf.boardgame.function.http.requestbody;

/**
 * @Description:
 * @author: wangzhenfei
 * @date: 2017-03-02 14:50
 */

public interface ProgressRequestListener {
    void onRequestProgress(long bytesWritten, long contentLength, boolean done);
}
