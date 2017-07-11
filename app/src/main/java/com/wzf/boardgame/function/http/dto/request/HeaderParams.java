package com.wzf.boardgame.function.http.dto.request;

import android.text.TextUtils;

import com.wzf.boardgame.MyApplication;
import com.wzf.boardgame.function.map.BaiDuMapManager;
import com.wzf.boardgame.ui.model.UserInfo;
import com.wzf.boardgame.utils.AppDeviceInfo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import okhttp3.Headers;

/**
 * @Description:
 * @author: wangzhenfei
 * @date: 2017-06-19 11:02
 */

public class HeaderParams {
    private static HeaderParams mInstance;
    protected Headers.Builder headersBuilder = new Headers.Builder();
    private String userId;
    private String token;
    private String loginTime;
    private String client = "BRPG";
    private String clicentVersion ;
    private String os = "Android";
    private String osVersion;
    private String networkType;
    private String openuuid;

    private HeaderParams(){}

    public static HeaderParams getInstance() {
        if (mInstance == null) {
            synchronized (HeaderParams.class) {
                if (mInstance == null) {
                    mInstance = new HeaderParams();
                }
            }
        }
        return mInstance;
    }

    public String getUserId() {
        return UserInfo.getInstance().getUid();
    }

    public String getToken() {
        return UserInfo.getInstance().getToken();
    }

    public String getClient() {
        return client;
    }

    public String getClicentVersion() {
        if(TextUtils.isEmpty(clicentVersion)){
            clicentVersion = AppDeviceInfo.getAppVersionName(MyApplication.getAppInstance());
        }
        return clicentVersion == null ? "" : clicentVersion;
    }

    public String getOs() {
        return os;
    }

    public String getOsVersion() {
        if(TextUtils.isEmpty(osVersion)){
            osVersion = AppDeviceInfo.getSystemVersion();
        }
        return osVersion == null ? "" : osVersion;
    }

    public String getNetworkType() {
        if(TextUtils.isEmpty(networkType)){
            networkType = AppDeviceInfo.getNetworkType();
        }
        return networkType == null ? "" : networkType;
    }

    public String getOpenuuid() {
        if(TextUtils.isEmpty(openuuid)){
            openuuid = AppDeviceInfo.getDeviceid();
        }
        return openuuid == null ? "" : openuuid;
    }

    public String getLoginTime() {
        return loginTime == null ? "" : loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getCityName() {
        return BaiDuMapManager.getInstance().getCityName();
    }

    public String getLon() {
        return BaiDuMapManager.getInstance().getLon() + "";
    }

    public String getLat() {
        return BaiDuMapManager.getInstance().getLat() + "";
    }

    public Headers refreshData(){
        headersBuilder.set("userId", getUserId());
        headersBuilder.set("token", getToken());
        headersBuilder.set("client", getClient());
        headersBuilder.set("clicentVersion", getClicentVersion());
        headersBuilder.set("os", getOs());
        headersBuilder.set("osVersion", getOsVersion());
        headersBuilder.set("networkType", getNetworkType());
        headersBuilder.set("openuuid", getOpenuuid());
        headersBuilder.set("loginTime", getLoginTime());
        return headersBuilder.build();
    }

    private static String getValueEncoded(String value) {
        if (value == null) return "null";
        String newValue = value.replace("\n", "");
        for (int i = 0, length = newValue.length(); i < length; i++) {
            char c = newValue.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                try {
                    return URLEncoder.encode(newValue, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        return newValue;
    }
}
