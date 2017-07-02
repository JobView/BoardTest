package com.wzf.boardgame.function.http.dto.request;

/**
 * Created by wzf on 2017/7/2.
 */

public class GetSmsCodeReqDto extends BaseRequestDto{
    public static final String SMS_CODE_REGISTER = "1";
    public static final String SMS_CODE_PSW = "2";
    private String userMobile;
    private String codeType;

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }
}
