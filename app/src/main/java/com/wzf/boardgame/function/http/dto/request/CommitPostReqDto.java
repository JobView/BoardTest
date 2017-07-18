package com.wzf.boardgame.function.http.dto.request;

import java.util.List;

/**
 * Created by wzf on 2017/7/11.
 */

public class CommitPostReqDto extends BaseRequestDto{

    private String postTitle;
    private String postContent;
    private List<String> postImgUrl;

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public List<String> getPostImgUrl() {
        return postImgUrl;
    }

    public void setPostImgUrl(List<String> postImgUrl) {
        this.postImgUrl = postImgUrl;
    }
}
