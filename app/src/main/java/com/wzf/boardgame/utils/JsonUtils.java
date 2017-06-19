package com.wzf.boardgame.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.List;

/**
 * 解析统一处理工具类
 * @author zhenfei
 */
public class JsonUtils {


    /**
     * 将对象转换为json字符串
     * @param o
     * @return
     */
    public static String toJson(Object o){
        String json = "";
        if(o != null){
            json = new Gson().toJson(o);
        }
        return json;
    }

    /**
     * JSON转换工具类附格式
     * @param <T>
     * @param json {"status":1,"message":"success","results":[{..},{..}]}或
     *             {"status":1,"message":"success","results":{..}}
     * @return
     * @notice 用于转换对象及对象中的数组的JSON格式到Bean中
     */
    public static <T> T fromJSON(Class<T> t, String json) {
        Gson gson = new Gson();
        T obj = null;
        try {
            obj = gson.fromJson(json, t);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return obj;
    }


    /**
     * JSON转换工具类附格式
     *
     * @param t    泛型
     * @param json JSON数据  [{"id":0,"name":"name0"},{"id":1,"name":"name1"}]
     * @return List<T>
     * @notice 用于转换纯数组JSON格式到Bean中, 在3.0的项目中基本不会使用到
     */
    public static <T> List<T> getListFromJSON(Class<T> t, String json) {
        Gson gson = new Gson();
        List<T> lists = new ArrayList<T>();
        try {
            JsonParser parser = new JsonParser();
            JsonArray array = parser.parse(json).getAsJsonArray();
            for (final JsonElement jsonElement : array) {
                T entity = gson.fromJson(jsonElement, t);
                lists.add(entity);
            }
        }catch (Exception e){
//            MaxerLogger.err("==", e.getMessage());
        }
        return lists;
    }


    /**
     * json 格式化
     * @param jsonStr
     * @return
     */
    public static String format(String jsonStr) {
        int level = 0;
        StringBuffer jsonForMatStr = new StringBuffer();
        for(int i=0;i<jsonStr.length();i++){
            char c = jsonStr.charAt(i);
            if(level>0&&'\n'==jsonForMatStr.charAt(jsonForMatStr.length()-1)){
                jsonForMatStr.append(getLevelStr(level));
            }
            switch (c) {
                case '{':
                case '[':
                    jsonForMatStr.append(c+"\n");
                    level++;
                    break;
                case ',':
                    jsonForMatStr.append(c+"\n");
                    break;
                case '}':
                case ']':
                    jsonForMatStr.append("\n");
                    level--;
                    jsonForMatStr.append(getLevelStr(level));
                    jsonForMatStr.append(c);
                    break;
                default:
                    jsonForMatStr.append(c);
                    break;
            }
        }

        return jsonForMatStr.toString();

    }

    private static String getLevelStr(int level){
        StringBuffer levelStr = new StringBuffer();
        for(int levelI = 0;levelI<level ; levelI++){
            levelStr.append("\t");
        }
        return levelStr.toString();
    }

}
