package com.wzf.boardgame.function.http.dto.request;

/**
 * @Description:
 * @author: wangzhenfei
 * @date: 2017-08-18 14:15
 */

public class SearchUserReqDto extends BaseRequestDto{
    private String searchContent;

    public String getSearchContent() {
        return searchContent;
    }

    public void setSearchContent(String searchContent) {
        this.searchContent = searchContent;
    }
}
