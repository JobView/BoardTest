package com.wzf.boardgame.function.http.Interceptor;

import android.text.TextUtils;
import android.util.Log;


import com.wzf.boardgame.function.http.Utils;
import com.wzf.boardgame.utils.DebugLog;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

public class LoggerInterceptor implements Interceptor {
    public static final String TAG = "OkHttpUtils";
    private String tag;
    private boolean showResponse;

    public LoggerInterceptor(String tag) {
        this(tag, false);
    }

    public LoggerInterceptor(String tag, boolean showResponse) {
        if (TextUtils.isEmpty(tag)) {
            tag = TAG;
        }
        this.showResponse = showResponse;
        this.tag = tag;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        logForRequest(request);
        Response response = chain.proceed(request);
        return logForResponse(response);
    }

    private void logForRequest(Request request) {
        //收集请求参数，方便调试
        StringBuilder paramsBuilder = new StringBuilder();
        try {
            String url = request.url().toString();
            Headers headers = request.headers();

            paramsBuilder.append("---------------------request log start---------------------" + "\n");
            paramsBuilder.append("method : " + request.method() + "\n");
            paramsBuilder.append("url : " + url + "\n");
            if (headers != null && headers.size() > 0) {
                paramsBuilder.append("===========================headers===============================\n\t\t\t");
                    paramsBuilder.append(headers.toString().replaceAll("\\n","\n\t\t\t"));
                paramsBuilder.append("\n");
                paramsBuilder.append("===========================headers===============================\n");
            }
            RequestBody requestBody = request.body();
            if (requestBody != null) {
                MediaType mediaType = requestBody.contentType();
                if (mediaType != null) {
                    paramsBuilder.append("contentType : " + mediaType.toString() + "\n");
                    if (isText(mediaType)) {
                        paramsBuilder.append("\n----->>>> params <<<<-----" + "\n");
                        paramsBuilder.append("content : \n" + Utils.format(bodyToString(request)) + "\n");
                    } else {
                        paramsBuilder.append("content : " + " maybe [file part] , too large too print , ignored!" + "\n");
                        paramsBuilder.append("\n----->>>> params <<<<-----" + "\n");
                    }
                }

                if (requestBody instanceof FormBody) {
                    FormBody newBody = (FormBody) requestBody;
                    //添加原请求体
                    for (int i = 0; i < newBody.size(); i++) {
                        paramsBuilder.append(newBody.name(i));
                        paramsBuilder.append("=");
                        paramsBuilder.append(newBody.value(i));
                        paramsBuilder.append("\n");
                    }
                } else if (requestBody instanceof MultipartBody) {
                    MultipartBody newBody = (MultipartBody) requestBody;
                    for (int i = 0; i < newBody.size(); i++) {
                        paramsBuilder.append(newBody.part(i).toString() + "\n");
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            paramsBuilder.append("---------------------request log end-----------------------\n\n\n\n");
            //打印参数
            DebugLog.i("OKHTTP", paramsBuilder.toString());
        }
    }

    private Response logForResponse(Response response) {
        //收集请求参数，方便调试
        StringBuilder paramsBuilder = new StringBuilder();
        try {
            paramsBuilder.append("---------------------response log start---------------------" + "\n");
            Response.Builder builder = response.newBuilder();
            Response clone = builder.build();
            paramsBuilder.append("url : " + clone.request().url() + "\n");
            paramsBuilder.append("code : " + clone.code() + "\n");
            paramsBuilder.append("protocol : " + clone.protocol() + "\n");
            if (!TextUtils.isEmpty(clone.message())) paramsBuilder.append("message : " + clone.message() + "\n");

            if (showResponse) {
                ResponseBody body = clone.body();
                if (body != null) {
                    MediaType mediaType = body.contentType();
                    if (mediaType != null) {
                        paramsBuilder.append("contentType : " + mediaType.toString() + "\n");
                        if (isText(mediaType)) {
                            String resp = body.string();
                            paramsBuilder.append("content : " + Utils.format(resp) + "\n");
                            body = ResponseBody.create(mediaType, resp + "\n");
                            return response.newBuilder().body(body).build();
                        } else {
                            paramsBuilder.append("content : " + " maybe [file part] , too large too print , ignored!" + "\n");
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//            paramsBuilder.append("——————————————————————————-----response log end--——————————————————————————————————---\n");
            //打印参数
            DebugLog.i("OKHTTP", paramsBuilder.toString());
            DebugLog.w("OKHTTP", "——————————————————————————-----response log end--——————————————————————————————————---\n");
        }

        return response;
    }

    private boolean isText(MediaType mediaType) {
        if (mediaType.type() != null && mediaType.type().equals("text")) {
            return true;
        }
        if (mediaType.subtype() != null) {
            if (mediaType.subtype().equals("json") ||
                    mediaType.subtype().equals("xml") ||
                    mediaType.subtype().equals("html") ||
                    mediaType.subtype().equals("webviewhtml")) //
                return true;
        }
        return false;
    }

    private String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "something error when show requestBody.";
        }
    }
}
