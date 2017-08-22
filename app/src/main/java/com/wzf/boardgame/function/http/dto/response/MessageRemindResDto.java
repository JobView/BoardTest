package com.wzf.boardgame.function.http.dto.response;

import java.util.List;

/**
 * @Description:
 * @author: wangzhenfei
 * @date: 2017-08-22 09:07
 */

public class MessageRemindResDto {

    /**
     * isLastPage : 1
     * totalCount : 2
     * pageSize : 30
     * msgList : [{"nickname":"还是我的","authLevel":0,"replySummary":"哈哈哈哈比比刚明明名字明给你过敏名模him您公民1哦","replyId":"RI_1503364121194_0000100012","replyTime":"2017-08-22 09:08","postId":"PI_1501579113732_0000100035"},{"nickname":"还是我的","authLevel":0,"replySummary":"厉害了","replyId":"RI_1503364101983_0000100011","replyTime":"2017-08-22 09:08","postId":"PI_1501585346560_0000100039"}]
     * pageNum : 1
     */

    private int isLastPage;
    private int totalCount;
    private int pageSize;
    private int pageNum;
    /**
     * nickname : 还是我的
     * authLevel : 0
     * replySummary : 哈哈哈哈比比刚明明名字明给你过敏名模him您公民1哦
     * replyId : RI_1503364121194_0000100012
     * replyTime : 2017-08-22 09:08
     * postId : PI_1501579113732_0000100035
     */

    private List<MsgListBean> msgList;

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

    public List<MsgListBean> getMsgList() {
        return msgList;
    }

    public void setMsgList(List<MsgListBean> msgList) {
        this.msgList = msgList;
    }

    public static class MsgListBean {
        private String nickname;
        private int authLevel;
        private String replySummary;
        private String replyId;
        private String replyTime;
        private String postId;

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getAuthLevel() {
            return authLevel;
        }

        public void setAuthLevel(int authLevel) {
            this.authLevel = authLevel;
        }

        public String getReplySummary() {
            return replySummary;
        }

        public void setReplySummary(String replySummary) {
            this.replySummary = replySummary;
        }

        public String getReplyId() {
            return replyId;
        }

        public void setReplyId(String replyId) {
            this.replyId = replyId;
        }

        public String getReplyTime() {
            return replyTime;
        }

        public void setReplyTime(String replyTime) {
            this.replyTime = replyTime;
        }

        public String getPostId() {
            return postId;
        }

        public void setPostId(String postId) {
            this.postId = postId;
        }
    }
}
