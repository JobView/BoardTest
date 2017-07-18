package com.wzf.boardgame.function.http.dto.request;

/**
 * Created by wzf on 2017/7/11.
 */

public class PostDetailReqDto extends BaseRequestDto{

    private String postId;

    public PostDetailReqDto() {
    }

    public PostDetailReqDto(String postId) {
        this.postId = postId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }
}
