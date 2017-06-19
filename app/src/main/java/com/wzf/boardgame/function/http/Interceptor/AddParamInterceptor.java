package com.wzf.boardgame.function.http.Interceptor;

import com.wzf.boardgame.function.http.dto.request.HeaderParams;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @Description:
 * @author: wangzhenfei
 * @date: 2017-06-19 11:54
 *
 * 基于retrofit的机制，向request里添加额外共同参数, 添加t参数到header中，避免request的url改变使缓存失效
 */

public class AddParamInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        request = request.newBuilder()
                .headers(HeaderParams.getInstance().refreshData())
                .build();
        return chain.proceed(request);
    }
}
