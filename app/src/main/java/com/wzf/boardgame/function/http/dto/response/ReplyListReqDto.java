package com.wzf.boardgame.function.http.dto.response;

import java.util.List;

/**
 * @Description:
 * @author: wangzhenfei
 * @date: 2017-08-02 08:17
 */

public class ReplyListReqDto {

    /**
     * replyList : [{"nickname":"王者荣耀","beAnswerNickname":"吾声官方","replyContent":"测试","replyId":"RI_1501581314445_0000100034","replyTime":"2017-08-01 22:00","postId":"PI_1501581296309_0000100036"},{"nickname":"王者荣耀","beAnswerNickname":"大花生","replyContent":"有上海的小伙伴么","replyId":"RI_1501518322927_0000100032","replyTime":"2017-08-01 17:27","postId":"PI_1501421353308_0000100006"},{"nickname":"王者荣耀","beAnswerNickname":"大花生","replyContent":"有上海的小伙伴么","replyId":"RI_1501518322927_0000100032","replyTime":"2017-08-01 17:27","postId":"PI_1501421353308_0000100006"},{"nickname":"王者荣耀","beAnswerNickname":"吾声官方","replyContent":"吾声桌游【安卓内测0.1.1】版本公告","replyId":"RI_1501579254015_0000100033","replyTime":"2017-08-01 17:20","postId":"PI_1501417458272_0000100001"}]
     * isLastPage : 1
     * totalCount : 4
     * pageSize : 30
     * pageNum : 1
     */

    private int isLastPage;
    private int totalCount;
    private int pageSize;
    private int pageNum;
    /**
     * nickname : 王者荣耀
     * beAnswerNickname : 吾声官方
     * replyContent : 测试
     * replyId : RI_1501581314445_0000100034
     * replyTime : 2017-08-01 22:00
     * postId : PI_1501581296309_0000100036
     */

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
        private String nickname;
        private String beAnswerNickname;
        private String replyContent;
        private String replyId;
        private String replyTime;
        private String postId;

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getBeAnswerNickname() {
            return beAnswerNickname;
        }

        public void setBeAnswerNickname(String beAnswerNickname) {
            this.beAnswerNickname = beAnswerNickname;
        }

        public String getReplyContent() {
            return replyContent;
        }

        public void setReplyContent(String replyContent) {
            this.replyContent = replyContent;
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
