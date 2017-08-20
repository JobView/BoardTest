package com.wzf.boardgame.function.http.dto.request;

/**
 * Created by wzf on 2017/8/20.
 */

public class GameContentReqDto extends BaseRequestDto {
    private String boardId;
    private int contentType; // 1：规则内容，2：扩展内容

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public int getContentType() {
        return contentType;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }
}
