package com.wzf.boardgame.function.http.dto.response;

/**
 * Created by wzf on 2017/7/16.
 */

public class QiNiuTokenResDto {

    /**
     * imgUrl : http://os7i4k6w5.bkt.clouddn.com
     * token : FVSsu08J3JH_VZw6bxTZLaV7wqImzaHhVcQZBnNC:JD28YMwIP4GtsgusgiH99yz0v5M=:eyJzY29wZSI6Ind1c2hlbmdrZWppLWltZyIsImRlYWRsaW5lIjoxNTAwMjAyMTI4fQ==
     */

    private String imgUrl;
    private String token;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
