package com.wzf.boardgame.function.http.dto.request;

/**
 * @Description:
 * @author: wangzhenfei
 * @date: 2017-08-03 09:43
 */

public class GameReqDto extends BaseRequestDto{
    private String boardId;

    public GameReqDto() {
    }

    public GameReqDto(String boardId) {
        this.boardId = boardId;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }
}
