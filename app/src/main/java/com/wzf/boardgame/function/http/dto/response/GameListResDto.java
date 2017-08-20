package com.wzf.boardgame.function.http.dto.response;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.LinearLayout;

import java.util.List;

/**
 * Created by wzf on 2017/7/8.
 */

public class GameListResDto {

    /**
     * isLastPage : 1
     * totalCount : 20
     * pageSize : 30
     * waterfallList : [{"boardId":"9","boardImgUrl":"http://os7i4k6w5.bkt.clouddn.com/image/wskj/1498636083546.jpg"},{"boardId":"8","boardImgUrl":"http://os7i4k6w5.bkt.clouddn.com/image/wskj/1498636068609.jpg"},{"boardId":"7","boardImgUrl":"http://os7i4k6w5.bkt.clouddn.com/image/wskj/1498636034013.jpg"},{"boardId":"6","boardImgUrl":"http://os7i4k6w5.bkt.clouddn.com/image/wskj/1498636014549.jpg"},{"boardId":"5","boardImgUrl":"http://os7i4k6w5.bkt.clouddn.com/image/wskj/1498635997058.jpg"},{"boardId":"4","boardImgUrl":"http://os7i4k6w5.bkt.clouddn.com/image/wskj/1498635976056.jpg"},{"boardId":"3","boardImgUrl":"http://os7i4k6w5.bkt.clouddn.com/image/wskj/1498635963690.jpg"},{"boardId":"20","boardImgUrl":"http://os7i4k6w5.bkt.clouddn.com/image/wskj/1498635948801.jpg"},{"boardId":"2","boardImgUrl":"http://os7i4k6w5.bkt.clouddn.com/image/wskj/1498635924950.jpg"},{"boardId":"19","boardImgUrl":"http://os7i4k6w5.bkt.clouddn.com/image/wskj/1498635910365.jpg"},{"boardId":"18","boardImgUrl":"http://os7i4k6w5.bkt.clouddn.com/image/wskj/1498635891523.jpg"},{"boardId":"17","boardImgUrl":"http://os7i4k6w5.bkt.clouddn.com/image/wskj/1498635875436.jpg"},{"boardId":"16","boardImgUrl":"http://os7i4k6w5.bkt.clouddn.com/image/wskj/1498635863417.jpg"},{"boardId":"15","boardImgUrl":"http://os7i4k6w5.bkt.clouddn.com/image/wskj/1498635837381.jpg"},{"boardId":"14","boardImgUrl":"http://os7i4k6w5.bkt.clouddn.com/image/wskj/1498635813973.jpg"},{"boardId":"13","boardImgUrl":"http://os7i4k6w5.bkt.clouddn.com/image/wskj/1498635736740.jpg"},{"boardId":"12","boardImgUrl":"http://os7i4k6w5.bkt.clouddn.com/image/wskj/1498635066459.jpg"},{"boardId":"11","boardImgUrl":"http://os7i4k6w5.bkt.clouddn.com/image/cxw/1498634988557.png"},{"boardId":"10","boardImgUrl":"http://os7i4k6w5.bkt.clouddn.com/image/cxw/1498634959100.png"},{"boardId":"1","boardImgUrl":"http://os7i4k6w5.bkt.clouddn.com/image/cxw/1498634896765.png"}]
     * pageNum : 1
     */

    private int isLastPage;
    private int totalCount;
    private int pageSize;
    private int pageNum;
    private List<WaterfallListBean> waterfallList;

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

    public List<WaterfallListBean> getWaterfallList() {
        return waterfallList;
    }

    public void setWaterfallList(List<WaterfallListBean> waterfallList) {
        this.waterfallList = waterfallList;
    }

    public static class WaterfallListBean {
        /**
         * boardId : 9
         * boardImgUrl : http://os7i4k6w5.bkt.clouddn.com/image/wskj/1498636083546.jpg
         */

        private String boardId;
        private String boardImgUrl;
        private int imgWidth;
        private int imgHeight;

        public int getImgWidth() {
            return imgWidth;
        }

        public void setImgWidth(int imgWidth) {
            this.imgWidth = imgWidth;
        }

        public int getImgHeight() {
            return imgHeight;
        }

        public void setImgHeight(int imgHeight) {
            this.imgHeight = imgHeight;
        }

        public String getBoardId() {
            return boardId;
        }

        public void setBoardId(String boardId) {
            this.boardId = boardId;
        }

        public String getBoardImgUrl() {
            return boardImgUrl;
        }

        public void setBoardImgUrl(String boardImgUrl) {
            this.boardImgUrl = boardImgUrl;
        }

        @Override
        public String toString() {
            return "WaterfallListBean{" +
                    "boardId='" + boardId + '\'' +
                    ", boardImgUrl='" + boardImgUrl + '\'' +
                    ", imgWidth=" + imgWidth +
                    ", imgHeight=" + imgHeight +
                    '}';
        }
    }
}
