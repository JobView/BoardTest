package com.wzf.boardgame.utils;

import com.wzf.boardgame.constant.UrlService;

import java.text.DecimalFormat;

/**
 * @Description:
 * @author: wangzhenfei
 * @date: 2017-06-19 15:42
 */

public class StringUtils {

    /**
     * 获得万为单位的数据
     * @param src
     */
    public static String getCountByWan(String src){
        try {
            int num = Integer.valueOf(src);
            if(num > 9999){
                DecimalFormat df = new DecimalFormat("0.0");
                double d = (num * 1.0/ 10000);
                String db = df.format(d);
                return db + "万";
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return src;
    }

    public static String getResourcePath(String path){
        if(path == null){
            return "";
        }
        if(!path.startsWith("http")){
            path = UrlService.BASE_RESOURCE + path;
        }
        return path;
    }
}
