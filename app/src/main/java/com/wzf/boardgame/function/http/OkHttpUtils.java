package com.wzf.boardgame.function.http;

import android.content.Context;
import android.os.Environment;

import com.wzf.boardgame.MyApplication;
import com.wzf.boardgame.constant.UrlService;
import com.wzf.boardgame.function.http.Interceptor.AddParamInterceptor;
import com.wzf.boardgame.function.http.Interceptor.DecodeParamsInterceptor;
import com.wzf.boardgame.function.http.Interceptor.LoggerInterceptor;
import com.wzf.boardgame.function.http.Interceptor.OkhttpOffLineCacheInterceptor;
import com.wzf.boardgame.function.http.Interceptor.OkhttpOnLineCacheInterceptor;
import com.wzf.boardgame.function.http.requestbody.ProgressRequestListener;
import com.wzf.boardgame.function.http.requestbody.ProgressResponseListener;
import com.wzf.boardgame.utils.FileUtils;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wangzhenfei on 2017/2/28.
 */

public class OkHttpUtils {
    private static final int DISK_CACHE_SIZE = 10 * 1024 * 1024; // 100M
    private static OkHttpUtils INSTANCE;
    private static final int DEFAULT_TIMEOUT = 30;
    private Retrofit.Builder retrofitBuilder;
    private OkHttpClient.Builder client;
    private Retrofit requestRetrofit;
    public String mBaseUrl = UrlService.BASE_URL;

    private OkHttpUtils() {
        File diskCacheDir = FileUtils.getDiskCacheDir("okhttp");
        client = new OkHttpClient.Builder();
        client.cache(new Cache(diskCacheDir, DISK_CACHE_SIZE))
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(new AddParamInterceptor())//添加头部headers
                .addInterceptor(new LoggerInterceptor(null, true))
                .addInterceptor(new DecodeParamsInterceptor())//解密
                .addInterceptor(new OkhttpOffLineCacheInterceptor())//离线缓存
                .addNetworkInterceptor(new OkhttpOnLineCacheInterceptor());//在线缓存
                retrofitBuilder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create());
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .baseUrl(BoxingUrlService.BASE_REQUEST_URL);
    }


    public static OkHttpUtils getInstance(){
        if(INSTANCE == null){
            synchronized (OkHttpUtils.class){
                if(INSTANCE == null){
                    INSTANCE = new OkHttpUtils();
                }
            }
        }
        return INSTANCE;
    }



    /**
     * 获取urlservice
     * @param t
     * @param <T>
     * @return
     */
    public <T> T getUrlService(Class<T> t){
        if(requestRetrofit == null){
            requestRetrofit = retrofitBuilder.client(client.build()).baseUrl(mBaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        return requestRetrofit.create(t);
    }
    /**
     * 创建带响应进度(下载进度)回调的service
     */
    public  <T> T getDownloadService(Class<T> tClass, ProgressResponseListener listener){
        return new Retrofit.Builder()
                .client(HttpClientHelper.addProgressResponseListener(new OkHttpClient.Builder() .
                        addInterceptor(new LoggerInterceptor(null, true)),listener))
                .baseUrl(mBaseUrl)
                .build()
                .create(tClass);
    }

    /**
     * 创建带请求体进度(上传进度)回调的service
     */
    public  <T> T getUploadService(Class<T> tClass, ProgressRequestListener listener){
        return new Retrofit.Builder()
                .client(HttpClientHelper.addProgressRequestListener(new OkHttpClient.Builder() .
                        addInterceptor(new LoggerInterceptor(null, true)).connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS),listener))
                .baseUrl(mBaseUrl)
                .build()
                .create(tClass);
    }

}
