package com.wzf.boardgame.function.http.dto.response;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @author: wangzhenfei
 * @date: 2017-07-18 11:04
 */

public class PostDetailResDto {


    /**
     * replyCount : 0
     * nickname : 王者荣耀
     * readCount : 1
     * avatarUrl : http://os7i4k6w5.bkt.clouddn.com/100004/1500205854906
     * userId : 100004
     * postContent : 哈哈就是
     * postTitle : 我的帖子
     * isCollect : 0
     * postImgsUrls : ["http://os7i4k6w5.bkt.clouddn.com/100004/1500369916059","http://os7i4k6w5.bkt.clouddn.com/100004/1500369927298","http://os7i4k6w5.bkt.clouddn.com/100004/1500369937215"]
     * postId : PI_1500370116888_0000100001
     * postTime : 2017-07-18 17:28
     */

    private int replyCount;
    private String nickname;
    private int readCount;
    private String avatarUrl;
    private String userId;
    private String postContent;
    private String postTitle;
    private int isCollect;
    private String postId;
    private String postTime;
    private List<String> postImgsUrls;
    private int authLevel;

    public int getAuthLevel() {
        return authLevel;
    }

    public void setAuthLevel(int authLevel) {
        this.authLevel = authLevel;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getReadCount() {
        return readCount;
    }

    public void setReadCount(int readCount) {
        this.readCount = readCount;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public int getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(int isCollect) {
        this.isCollect = isCollect;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public List<String> getPostImgsUrls() {
        return postImgsUrls == null ? new ArrayList<String>() : postImgsUrls;
    }

    public void setPostImgsUrls(List<String> postImgsUrls) {
        this.postImgsUrls = postImgsUrls;
    }
}
