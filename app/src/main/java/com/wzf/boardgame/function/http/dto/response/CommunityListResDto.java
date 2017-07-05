package com.wzf.boardgame.function.http.dto.response;

import java.util.List;

/**
 * Created by wzf on 2017/7/5.
 */

public class CommunityListResDto {

    /**
     * isLastPage : 1
     * totalCount : 1
     * postList : [{"replyCount":0,"readCount":0,"isImg":0,"userId":"100000","topSort":0,"postTitle":"aa bb cc dd abc","postId":"PI_1498831041599_0000100001","isFine":0,"postTime":"2017-06-30 21:57"}]
     * pageSize : 1
     * pageNum : 1
     */

    private int isLastPage;
    private int totalCount;
    private int pageSize;
    private int pageNum;
    private List<PostListBean> postList;

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

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public List<PostListBean> getPostList() {
        return postList;
    }

    public void setPostList(List<PostListBean> postList) {
        this.postList = postList;
    }

    public static class PostListBean {
        /**
         * replyCount : 0
         * readCount : 0
         * isImg : 0
         * userId : 100000
         * topSort : 0
         * postTitle : aa bb cc dd abc
         * postId : PI_1498831041599_0000100001
         * isFine : 0
         * postTime : 2017-06-30 21:57
         */

        private int replyCount;
        private int readCount;
        private int isImg;
        private String userId;
        private int topSort;
        private String postTitle;
        private String postId;
        private int isFine;
        private String postTime;

        public int getReplyCount() {
            return replyCount;
        }

        public void setReplyCount(int replyCount) {
            this.replyCount = replyCount;
        }

        public int getReadCount() {
            return readCount;
        }

        public void setReadCount(int readCount) {
            this.readCount = readCount;
        }

        public int getIsImg() {
            return isImg;
        }

        public void setIsImg(int isImg) {
            this.isImg = isImg;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public int getTopSort() {
            return topSort;
        }

        public void setTopSort(int topSort) {
            this.topSort = topSort;
        }

        public String getPostTitle() {
            return postTitle;
        }

        public void setPostTitle(String postTitle) {
            this.postTitle = postTitle;
        }

        public String getPostId() {
            return postId;
        }

        public void setPostId(String postId) {
            this.postId = postId;
        }

        public int getIsFine() {
            return isFine;
        }

        public void setIsFine(int isFine) {
            this.isFine = isFine;
        }

        public String getPostTime() {
            return postTime;
        }

        public void setPostTime(String postTime) {
            this.postTime = postTime;
        }
    }
}
