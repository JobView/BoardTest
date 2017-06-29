package com.wzf.boardgame.function.http.dto.request;

import com.wzf.boardgame.utils.JsonUtils;
import com.wzf.boardgame.utils.MathUtilAndroid;

/**
 * Created by wzf on 2017/6/28.
 */

public class RegisterRequestDto extends BaseRequestDto{

    /**
     * nickname : 昵称
     * smsCode : 5871
     * userId : 1000145
     * userPwd : 123456
     * userMobile : 13814591112
     */

    private String nickname;
    private String smsCode;
    private String userId;
    private String userPwd;
    private String userMobile;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }


}
