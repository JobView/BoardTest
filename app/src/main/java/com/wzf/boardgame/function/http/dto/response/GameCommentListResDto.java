package com.wzf.boardgame.function.http.dto.response;

import java.util.List;

/**
 * @Description:
 * @author: wangzhenfei
 * @date: 2017-08-03 11:01
 */

public class GameCommentListResDto {

    /**
     * isLastPage : 1
     * totalCount : 20
     * pageSize : 20
     * commentList : [{"commentTime":"2017年08月03日","nickname":"王的荣耀","avatarUrl":"http://os7i4k6w5.bkt.clouddn.com/100004/1500205854906","userId":"100004","isClick":0,"commentContent":"这个游戏真是:1501729033904","commentCount":0,"isRecommend":1,"commentId":"BC_1501729229227_0000100001"}]
     */

    private int isLastPage;
    private int totalCount;
    private int pageSize;
    /**
     * commentTime : 2017年08月03日
     * nickname : 王的荣耀
     * avatarUrl : http://os7i4k6w5.bkt.clouddn.com/100004/1500205854906
     * userId : 100004
     * isClick : 0
     * commentContent : 这个游戏真是:1501729033904
     * commentCount : 0
     * isRecommend : 1
     * commentId : BC_1501729229227_0000100001
     */

    private List<CommentListBean> commentList;

    public int getIsLastPage() {
        return isLastPage;
    }

    public void setIsLastPage(int isLastPage) {
        this.isLastPage = isLastPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<CommentListBean> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<CommentListBean> commentList) {
        this.commentList = commentList;
    }

    public static class CommentListBean {
        private String commentTime;
        private String nickname;
        private String avatarUrl;
        private String userId;
        private int isClick;
        private String commentContent;
        private int commentCount;
        private int isRecommend;
        private String commentId;
        public boolean isHeader;
        private int authLevel;

        public CommentListBean() {
        }

        public CommentListBean(boolean isHeader) {
            this.isHeader = isHeader;
        }

        public int getAuthLevel() {
            return authLevel;
        }

        public void setAuthLevel(int authLevel) {
            this.authLevel = authLevel;
        }

        public String getCommentTime() {
            return commentTime;
        }

        public void setCommentTime(String commentTime) {
            this.commentTime = commentTime;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
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

        public int getIsClick() {
            return isClick;
        }

        public void setIsClick(int isClick) {
            this.isClick = isClick;
        }

        public String getCommentContent() {
            return commentContent;
        }

        public void setCommentContent(String commentContent) {
            this.commentContent = commentContent;
        }

        public int getCommentCount() {
            return commentCount;
        }

        public void setCommentCount(int commentCount) {
            this.commentCount = commentCount;
        }

        public int getIsRecommend() {
            return isRecommend;
        }

        public void setIsRecommend(int isRecommend) {
            this.isRecommend = isRecommend;
        }

        public String getCommentId() {
            return commentId;
        }

        public void setCommentId(String commentId) {
            this.commentId = commentId;
        }
    }
}
