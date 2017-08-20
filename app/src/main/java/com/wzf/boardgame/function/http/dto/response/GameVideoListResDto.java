package com.wzf.boardgame.function.http.dto.response;

import java.util.List;

/**
 * Created by wzf on 2017/8/19.
 */

public class GameVideoListResDto {

    /**
     * isLastPage : 1
     * totalCount : 1
     * pageSize : 5
     * videoList : [{"videoUrl":"http://v.youku.com/v_show/id_XMjcwNDM1ODg2NA==.html?spm=a2hzp.8244740.0.0","videoTitle":"教学","releaseNickname":"一群","releaseAvatar":"http://os7i4k6w5.bkt.clouddn.com/image/cxw/1502529898669.png"}]
     * pageNum : 1
     */

    private int isLastPage;
    private int totalCount;
    private int pageSize;
    private int pageNum;
    private List<VideoListBean> videoList;

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

    public List<VideoListBean> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<VideoListBean> videoList) {
        this.videoList = videoList;
    }

    public static class VideoListBean {
        /**
         * videoUrl : http://v.youku.com/v_show/id_XMjcwNDM1ODg2NA==.html?spm=a2hzp.8244740.0.0
         * videoTitle : 教学
         * releaseNickname : 一群
         * releaseAvatar : http://os7i4k6w5.bkt.clouddn.com/image/cxw/1502529898669.png
         */

        private String videoUrl;
        private String videoCover;
        private String videoTitle;
        private String releaseNickname;
        private String releaseAvatar;

        public String getVideoCover() {
            return videoCover;
        }

        public void setVideoCover(String videoCover) {
            this.videoCover = videoCover;
        }

        public String getVideoUrl() {
            return videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }

        public String getVideoTitle() {
            return videoTitle;
        }

        public void setVideoTitle(String videoTitle) {
            this.videoTitle = videoTitle;
        }

        public String getReleaseNickname() {
            return releaseNickname;
        }

        public void setReleaseNickname(String releaseNickname) {
            this.releaseNickname = releaseNickname;
        }

        public String getReleaseAvatar() {
            return releaseAvatar;
        }

        public void setReleaseAvatar(String releaseAvatar) {
            this.releaseAvatar = releaseAvatar;
        }
    }
}
