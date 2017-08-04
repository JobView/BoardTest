package com.wzf.boardgame.function.upload_qiniu;

import com.qiniu.android.common.AutoZone;
import com.qiniu.android.common.Zone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.wzf.boardgame.constant.UrlService;
import com.wzf.boardgame.function.http.ResponseSubscriber;
import com.wzf.boardgame.function.http.dto.response.QiNiuTokenResDto;
import com.wzf.boardgame.ui.model.UserInfo;
import com.wzf.boardgame.utils.DebugLog;

import org.json.JSONObject;

import java.io.File;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wzf on 2017/7/16.
 */

public class UpLoadQiNiuManager {
    private static UpLoadQiNiuManager mInstance;
    public static UpLoadQiNiuManager getInstance() {
        if (mInstance == null) {
            synchronized (UpLoadQiNiuManager.class) {
                if (mInstance == null) {
                    mInstance = new UpLoadQiNiuManager();
                }
            }
        }
        return mInstance;
    }


    private UploadManager uploadManager;
    private QiNiuTokenResDto tokenResDto;
    private File data;
    private QiNiuUpLoadListener upLoadListener;
    private UpLoadQiNiuManager(){
        Configuration config = new Configuration.Builder()
                .chunkSize(256 * 1024)  //分片上传时，每片的大小。 默认256K
                .putThreshhold(512 * 1024)  // 启用分片上传阀值。默认512K
                .connectTimeout(10) // 链接超时。默认10秒
                .responseTimeout(60) // 服务器响应超时。默认60秒
                .recorder(null)  // recorder分片上传时，已上传片记录器。默认null
//                .recorder(recorder, keyGen)  // keyGen 分片上传时，生成标识符，用于片记录器区分是那个文件的上传记录
                .zone(AutoZone.autoZone) // 设置区域，指定不同区域的上传域名、备用域名、备用IP。
                .build();
        // 重用uploadManager。一般地，只需要创建一个uploadManager对象
        uploadManager = new UploadManager(config);
    }

    public void uploadFile(File data, QiNiuUpLoadListener listener){
        if(data == null || !data.exists() || listener == null){
            throw new IllegalArgumentException("参数错误");
        }
        this.data = data;
        this.upLoadListener = listener;
        if(tokenResDto == null){
            getQiniuToken();
        }else {
            toUpLoad();
        }

    }
    private void getQiniuToken(){
        UrlService.SERVICE.getQiniuToken("")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new ResponseSubscriber<QiNiuTokenResDto>() {
                    @Override
                    public void onSuccess(QiNiuTokenResDto responseDto) throws Exception {
                        super.onSuccess(responseDto);
                        tokenResDto = responseDto;
                        toUpLoad();
                    }
                    @Override
                    public void onFailure(int code, String message) throws Exception {
                        super.onFailure(code, message);
                    }
                });
    }

    private void toUpLoad() {
        final String filePath = UserInfo.getInstance().getUid() + "/" + System.currentTimeMillis();
        uploadManager.put(data,filePath , tokenResDto.getToken(),
                new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject res) {
                        //res包含hash、key等信息，具体字段取决于上传策略的设置
                        if(info.isOK())
                        {
                           if(upLoadListener != null){
                               upLoadListener.complete(true, tokenResDto.getImgUrl() + "/" + filePath);
                           }
                        }
                        else{
                            if(upLoadListener != null){
                                upLoadListener.complete(false, "上传失败，请重试");
                            }
                        }
                    }


                }, new UploadOptions(null, null, false, new UpProgressHandler() {
                    @Override
                    public void progress(String key, double percent) {
                        if(upLoadListener != null){
                            upLoadListener.progress(percent * 100);
                        }
                    }
                }, null));
    }

    public interface QiNiuUpLoadListener{
        public void progress(double percent);
        public void complete(boolean success, String result);
    }
}
