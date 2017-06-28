package com.wzf.boardgame;

import com.wzf.boardgame.function.http.dto.request.RegisterRequestDto;
import com.wzf.boardgame.utils.JsonUtils;
import com.wzf.boardgame.utils.MathUtilAndroid;

/**
 * Created by wzf on 2017/6/28.
 */

public class Test {
    public static void main(String[] args) {
        RegisterRequestDto dto = new RegisterRequestDto();
        dto.setUserId("123");
        dto.setNickname("wzf");
        dto.setSmsCode("1234");
        dto.setUserPwd("qqqqqq");
        dto.setUserMobile("18521709590");
        String befer = MathUtilAndroid.encodeAES(JsonUtils.toJson(dto));
        System.out.println(befer);
        String after = MathUtilAndroid.decodeAES(befer);
        System.out.println(after);
    }
}
