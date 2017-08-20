package com.wzf.boardgame.function.http.dto.request;

/**
 * Created by wzf on 2017/8/19.
 */

public class GetGameListReqDto extends BaseRequestDto{
    private String boardId;
    private int pageNum;

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

}
