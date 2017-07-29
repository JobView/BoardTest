package com.wzf.boardgame.function.http.dto.request;

/**
 * Created by wzf on 2017/7/29.
 */

public class ReplyCommentReqDto extends BaseRequestDto {
    private String replyId;
    private String beAnswerUserId;
    private String answerContent;

    public String getReplyId() {
        return replyId;
    }

    public void setReplyId(String replyId) {
        this.replyId = replyId;
    }

    public String getBeAnswerUserId() {
        return beAnswerUserId;
    }

    public void setBeAnswerUserId(String beAnswerUserId) {
        this.beAnswerUserId = beAnswerUserId;
    }

    public String getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }
}
