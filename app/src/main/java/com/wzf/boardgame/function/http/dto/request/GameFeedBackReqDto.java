package com.wzf.boardgame.function.http.dto.request;

import java.util.List;

/**
 * Created by wzf on 2017/7/11.
 */

public class GameFeedBackReqDto extends BaseRequestDto{

    private int feedbackType;
    private String feedbackTitle;
    private String feedbackContent;
    private List<String> feedbackImgUrl;

    public int getFeedbackType() {
        return feedbackType;
    }

    public void setFeedbackType(int feedbackType) {
        this.feedbackType = feedbackType;
    }

    public String getFeedbackTitle() {
        return feedbackTitle;
    }

    public void setFeedbackTitle(String feedbackTitle) {
        this.feedbackTitle = feedbackTitle;
    }

    public String getFeedbackContent() {
        return feedbackContent;
    }

    public void setFeedbackContent(String feedbackContent) {
        this.feedbackContent = feedbackContent;
    }

    public List<String> getFeedbackImgUrl() {
        return feedbackImgUrl;
    }

    public void setFeedbackImgUrl(List<String> feedbackImgUrl) {
        this.feedbackImgUrl = feedbackImgUrl;
    }
}
