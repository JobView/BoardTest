package com.wzf.boardgame.function.http.dto.request;

/**
 * @Description:
 * @author: wangzhenfei
 * @date: 2017-08-03 09:43
 */

public class CommentGameListReqDto extends BaseRequestDto{
    private String boardId;
    private int pageNum;
    private int pageSize;

    public CommentGameListReqDto() {
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

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }
}
