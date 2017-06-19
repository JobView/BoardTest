package com.wzf.boardgame.utils;

import android.content.Context;
import android.os.Environment;

import com.wzf.boardgame.MyApplication;

import java.io.File;

/**
 * @Description:
 * @author: wangzhenfei
 * @date: 2017-06-19 15:45
 */

public class FileUtils {
    /**
     * 获取硬盘存储路径
     * @param path
     * @return
     */
    public static File getDiskCacheDir(String path) {
        Context mContext = MyApplication.getAppInstance();
        boolean externalStorageAvailable = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        final String cachePath;
        if(externalStorageAvailable){
            File file  = mContext.getExternalCacheDir();
            cachePath = file.getPath();
        }else {
            cachePath = mContext.getCacheDir().getPath();
        }
        return  new File(cachePath + File.separator + path);
    }
}
