package com.wzf.boardgame.e;

/**
 * Author : Bean
 * 2017-06-20 15:07
 */
public enum E_Platform implements BaseEnum{

    WEIXIN_CIRCLE(1,"微信朋友圈"),
    WEIXIN(2,"微信"),
    QQ(3,"QQ"),
    ;

    int code;
    String msg;

    E_Platform(int code, String msg)
    {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
