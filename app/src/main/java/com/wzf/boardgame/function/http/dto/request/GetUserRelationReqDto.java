package com.wzf.boardgame.function.http.dto.request;

/**
 * @Description:
 * @author: wangzhenfei
 * @date: 2017-08-01 14:52
 */

public class GetUserRelationReqDto extends BaseRequestDto {
    private String uid;
    private int pageNum;
//    private int pageSize;


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
}
