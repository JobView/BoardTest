package com.wzf.boardgame.function.http.Interceptor;

import com.wzf.boardgame.constant.UrlService;
import com.wzf.boardgame.function.http.dto.response.BaseResponse;
import com.wzf.boardgame.utils.DebugLog;
import com.wzf.boardgame.utils.JsonUtils;
import com.wzf.boardgame.utils.MathUtilAndroid;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @Description:
 * @author: wangzhenfei
 * @date: 2017-06-29 09:48
 * 对参数进行加密
 */

public class DecodeParamsInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
       Response originalResponse = chain.proceed(chain.request());
        MediaType type = originalResponse.body().contentType();
        String payload = originalResponse.body().string();
        BaseResponse baseResponse = JsonUtils.fromJSON(BaseResponse.class, payload);
        if(UrlService.DEBUG){
            DebugLog.d("OKHTTP", "----->>>> before decode response params <<<<-----\n");
            DebugLog.d("OKHTTP", JsonUtils.format(JsonUtils.toJson(baseResponse)) + "\n");
        }
        baseResponse.setResponse(MathUtilAndroid.decodeAES(baseResponse.getResponse().toString()));
        String jsonStr = JsonUtils.toJson(baseResponse);
        ResponseBody responseBody = ResponseBody.create(type, jsonStr);
        return originalResponse.newBuilder().body(responseBody).build();
    }
}
