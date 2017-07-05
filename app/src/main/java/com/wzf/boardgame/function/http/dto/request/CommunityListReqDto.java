package com.wzf.boardgame.function.http.dto.request;

/**
 * Created by wzf on 2017/7/5.
 */

public class CommunityListReqDto extends BaseRequestDto{
    private String search;
    private int pageNum;
    private int pageSize;


    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "CommunityListReqDto{" +
                "search='" + search + '\'' +
                ", pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                '}';
    }
}
