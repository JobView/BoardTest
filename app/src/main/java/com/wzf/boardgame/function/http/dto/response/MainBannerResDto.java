package com.wzf.boardgame.function.http.dto.response;

import java.util.List;

/**
 * Created by wzf on 2017/7/8.
 */

public class MainBannerResDto {

    private List<CarouselListBean> carouselList;

    public List<CarouselListBean> getCarouselList() {
        return carouselList;
    }

    public void setCarouselList(List<CarouselListBean> carouselList) {
        this.carouselList = carouselList;
    }

    public static class CarouselListBean {
        /**
         * imgUrl : http://isujin.com/wp-content/uploads/2017/05/wallhaven-60149-300x169.jpg
         * pageUrl : http://www.baidu.com
         */

        private String imgUrl;
        private String pageUrl;
        private int bannerType; // 0不跳转，1跳转到帖子，2跳转到webview

        public int getBannerType() {
            return bannerType;
        }

        public void setBannerType(int bannerType) {
            this.bannerType = bannerType;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getPageUrl() {
            return pageUrl;
        }

        public void setPageUrl(String pageUrl) {
            this.pageUrl = pageUrl;
        }
    }
}
