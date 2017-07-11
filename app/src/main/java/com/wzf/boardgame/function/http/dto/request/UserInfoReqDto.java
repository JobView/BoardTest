package com.wzf.boardgame.function.http.dto.request;

/**
 * Created by wzf on 2017/7/11.
 */

public class UserInfoReqDto extends BaseRequestDto{
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
