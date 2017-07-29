package com.wzf.boardgame.function.http.dto.response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wzf on 2017/7/23.
 */

public class CommentListResDto {

    /**
     * replyList : [{"nickname":"王者荣耀","storey":2,"authLevel":0,"avatarUrl":"http://os7i4k6w5.bkt.clouddn.com/100004/1500205854906","userId":"100004","replyContent":"哈哈哈哈哈哈哈","replyTime":"2017-07-23 17:01","replyId":"RI_1500800462795_0000100001","replyAnswerList":[{"answerUserId":"","answerNickname":"","beAnswerUserId":"","beAnswerNickname":"","answerContent":""}]},{"nickname":"王者荣耀","storey":3,"authLevel":0,"avatarUrl":"http://os7i4k6w5.bkt.clouddn.com/100004/1500205854906","userId":"100004","replyContent":"呵呵呵呵呵呵","replyTime":"2017-07-23 17:01","replyId":"RI_1500800496770_0000100002","replyAnswerList":[{"answerUserId":"","answerNickname":"","beAnswerUserId":"","beAnswerNickname":"","answerContent":""}]}]
     * isLastPage : 1
     * totalCount : 2
     * pageSize : 20
     * pageNum : 1
     */

    private int isLastPage;
    private int totalCount;
    private int pageSize;
    private int pageNum;
    private List<ReplyListBean> replyList;

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

    public List<ReplyListBean> getReplyList() {
        return replyList;
    }

    public void setReplyList(List<ReplyListBean> replyList) {
        this.replyList = replyList;
    }

    public static class ReplyListBean {
        /**
         * nickname : 王者荣耀
         * storey : 2
         * authLevel : 0
         * avatarUrl : http://os7i4k6w5.bkt.clouddn.com/100004/1500205854906
         * userId : 100004
         * replyContent : 哈哈哈哈哈哈哈
         * replyTime : 2017-07-23 17:01
         * replyId : RI_1500800462795_0000100001
         * replyAnswerList : [{"answerUserId":"","answerNickname":"","beAnswerUserId":"","beAnswerNickname":"","answerContent":""}]
         */

        private String nickname;
        private int storey;
        private int authLevel;
        private String avatarUrl;
        private String userId;
        private String replyContent;
        private List<String> replyImgsUrl;
        private String replyTime;
        private String replyId;
        private List<ReplyAnswerListBean> replyAnswerList;

        public List<String> getReplyImgsUrl() {
            return replyImgsUrl == null ? new ArrayList<String>() : replyImgsUrl;
        }

        public void setReplyImgsUrl(List<String> replyImgsUrl) {
            this.replyImgsUrl = replyImgsUrl;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getStorey() {
            return storey;
        }

        public void setStorey(int storey) {
            this.storey = storey;
        }

        public int getAuthLevel() {
            return authLevel;
        }

        public void setAuthLevel(int authLevel) {
            this.authLevel = authLevel;
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

        public String getReplyContent() {
            return replyContent;
        }

        public void setReplyContent(String replyContent) {
            this.replyContent = replyContent;
        }

        public String getReplyTime() {
            return replyTime;
        }

        public void setReplyTime(String replyTime) {
            this.replyTime = replyTime;
        }

        public String getReplyId() {
            return replyId;
        }

        public void setReplyId(String replyId) {
            this.replyId = replyId;
        }

        public List<ReplyAnswerListBean> getReplyAnswerList() {
            return replyAnswerList == null ? new ArrayList<ReplyAnswerListBean>() : replyAnswerList;
        }

        public void setReplyAnswerList(List<ReplyAnswerListBean> replyAnswerList) {
            this.replyAnswerList = replyAnswerList;
        }

        public static class ReplyAnswerListBean {
            /**
             * answerUserId :
             * answerNickname :
             * beAnswerUserId :
             * beAnswerNickname :
             * answerContent :
             */

            private String answerUserId;
            private String answerNickname;
            private String beAnswerUserId;
            private String beAnswerNickname;
            private String answerContent;

            public String getAnswerUserId() {
                return answerUserId;
            }

            public void setAnswerUserId(String answerUserId) {
                this.answerUserId = answerUserId;
            }

            public String getAnswerNickname() {
                return answerNickname;
            }

            public void setAnswerNickname(String answerNickname) {
                this.answerNickname = answerNickname;
            }

            public String getBeAnswerUserId() {
                return beAnswerUserId;
            }

            public void setBeAnswerUserId(String beAnswerUserId) {
                this.beAnswerUserId = beAnswerUserId;
            }

            public String getBeAnswerNickname() {
                return beAnswerNickname;
            }

            public void setBeAnswerNickname(String beAnswerNickname) {
                this.beAnswerNickname = beAnswerNickname;
            }

            public String getAnswerContent() {
                return answerContent;
            }

            public void setAnswerContent(String answerContent) {
                this.answerContent = answerContent;
            }
        }
    }
}
