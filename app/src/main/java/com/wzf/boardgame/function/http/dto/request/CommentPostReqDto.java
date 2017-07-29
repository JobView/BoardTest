package com.wzf.boardgame.function.http.dto.request;

import java.util.List;

/**
 * Created by wzf on 2017/7/29.
 */

public class CommentPostReqDto extends BaseRequestDto {
    private String postId;
    private String replyContent;
    private List<String> replyImgsUrl;

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public List<String> getReplyImgsUrl() {
        return replyImgsUrl;
    }

    public void setReplyImgsUrl(List<String> replyImgsUrl) {
        this.replyImgsUrl = replyImgsUrl;
    }
}
