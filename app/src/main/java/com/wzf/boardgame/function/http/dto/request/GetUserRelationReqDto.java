package com.wzf.boardgame.function.http.dto.request;

/**
 * @Description:
 * @author: wangzhenfei
 * @date: 2017-08-01 14:52
 */

public class GetUserRelationReqDto extends BaseRequestDto {
    private String userId;
    private int pageNum;
//    private int pageSize;


    public String getUid() {
        return userId;
    }

    public void setUid(String uid) {
        this.userId = uid;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
}
