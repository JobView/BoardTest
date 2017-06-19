package com.wzf.boardgame.function.http.Interceptor;

import com.wzf.boardgame.MyApplication;
import com.wzf.boardgame.function.http.Utils;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @Description:
 * @author: wangzhenfei
 * @date: 2017-06-19 14:23
 * 基于retrofit的机制，判断网络是否可用并提示，缓存的设置，添加t参数到request header中，避免t的改变影响缓存机制
 */

public class OkhttpOnLineCacheInterceptor implements Interceptor {
    private static final int MAX_AGE = 2 * 24 * 60 * 60;//最大缓存时间
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        // 缓存配置
        if (!Utils.isNetworkAvailable(MyApplication.getAppInstance())) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }

        Response response = chain.proceed(request);
        if (!Utils.isNetworkAvailable(MyApplication.getAppInstance())) {
            response = response.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + MAX_AGE)
                    .removeHeader("Pragma")
                    .build();
        } else {
            //有网的直接读取接口上@Headers的配置，实现某个请求的在线缓存
            String cacheControl = request.cacheControl().toString();
            response = response.newBuilder()
                    .header("Cache-Control", cacheControl)
                    .removeHeader("Pragma")
                    .build();
        }
        return response;
    }
}
