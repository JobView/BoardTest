package com.wzf.boardgame.function.http.dto.response;

/**
 * Created by wzf on 2017/7/5.
 */

public class LoginResDto {

    /**
     * loginTime : 1499255516672
     * token : ETvJgAaAPOjRD7xncY/TWyKQt92xUF94nHRbfIBZIyk=
     * userId : 100004
     */

    private String loginTime;
    private String token;
    private String userId;

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
