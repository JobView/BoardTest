package com.wzf.boardgame.function.http.dto.response;

import java.util.List;

/**
 * @Description:
 * @author: wangzhenfei
 * @date: 2017-08-04 10:21
 */

public class GameFeedBackTypeListResDto {

    /**
     * feedbackTypeName : 增加哪些桌游
     * feedbackType : 1
     */

    private List<FeedbackTypeListBean> feedbackTypeList;

    public List<FeedbackTypeListBean> getFeedbackTypeList() {
        return feedbackTypeList;
    }

    public void setFeedbackTypeList(List<FeedbackTypeListBean> feedbackTypeList) {
        this.feedbackTypeList = feedbackTypeList;
    }

    public static class FeedbackTypeListBean {
        private String feedbackTypeName;
        private int feedbackType;

        public String getFeedbackTypeName() {
            return feedbackTypeName;
        }

        public void setFeedbackTypeName(String feedbackTypeName) {
            this.feedbackTypeName = feedbackTypeName;
        }

        public int getFeedbackType() {
            return feedbackType;
        }

        public void setFeedbackType(int feedbackType) {
            this.feedbackType = feedbackType;
        }
    }
}
