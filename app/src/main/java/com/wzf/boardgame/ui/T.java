package com.wzf.boardgame.ui;

import com.wzf.boardgame.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @author: wangzhenfei
 * @date: 2017-06-19 09:40
 */

public class T {
    public static void main(String[] args) {
//        String str = "werwerwer\n" +
//                "<img src=\"http://os7i4k6w5.bkt.clouddn.com/100004/1500366347890\" />\n" +
//                " qweqweqe\n" +
//                "<img src=\"http://os7i4k6w5.bkt.clouddn.com/100004/1500366355981\" />\n" +
//                "qweqweqw\n" +
//                "<img src=\"http://os7i4k6w5.bkt.clouddn.com/100004/1500366360548\" />\n" +
//                "qweqwe";
//        List<String> urls = new ArrayList();
//        String result = getUrlAndReplace(str, urls);
        String s = "哈哈就是\n" +
                "{$img$}\n" +
                "厉害了厉害\n" +
                "{$img$}\n" +
                "真的\n" +
                "{$img$}\n";
        String[] sp = s.split("\\{\\$img\\$}");
        for(String s1 : sp){
            System.out.println(s1);
        }

    }

    public static String getUrlAndReplace(String str, List<String> urls){
        System.out.println(str);
        int start = str.indexOf("<img");
        int end = str.indexOf("/>");
        System.out.println(start);
        System.out.println(end);
        if(start < 0){
            return str;
        }
        String url = str.substring(start + 10, end - 2);
        urls.add(url);
        System.out.println(url);
        return getUrlAndReplace(str.replace(str.substring(start, end + 2), "{$img$}"), urls);
    }
}
