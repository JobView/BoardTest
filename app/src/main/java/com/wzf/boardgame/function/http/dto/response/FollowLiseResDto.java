package com.wzf.boardgame.function.http.dto.response;

import java.util.List;

/**
 * @Description:
 * @author: wangzhenfei
 * @date: 2017-08-01 15:44
 */

public class FollowLiseResDto {

    /**
     * isLastPage : 1
     * followList : [{"nickname":"吾声官方","avatarUrl":"http://os7i4k6w5.bkt.clouddn.com/100009/1500376599564","userId":"100009","relation":3,"personaSign":"么么哒～(^з^)-☆"},{"nickname":"大花生","avatarUrl":"http://os7i4k6w5.bkt.clouddn.com/100014/1501431229500","userId":"100014","relation":3},{"nickname":"多环芳烃pahs","avatarUrl":"http://os7i4k6w5.bkt.clouddn.com/100031/1501422166404","userId":"100031","relation":3,"personaSign":"天机云锦用在我"}]
     * totalCount : 3
     * pageSize : 30
     * pageNum : 1
     */

    private int isLastPage;
    private int totalCount;
    private int pageSize;
    private int pageNum;
    /**
     * nickname : 吾声官方
     * avatarUrl : http://os7i4k6w5.bkt.clouddn.com/100009/1500376599564
     * userId : 100009
     * relation : 3
     * personaSign : 么么哒～(^з^)-☆
     */

    private List<FollowListBean> followList;

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

    public List<FollowListBean> getFollowList() {
        return followList;
    }

    public void setFollowList(List<FollowListBean> followList) {
        this.followList = followList;
    }

    public static class FollowListBean {
        private String nickname;
        private String avatarUrl;
        private String userId;
        private int relation;
        private String personaSign;

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

        public int getRelation() {
            return relation;
        }

        public void setRelation(int relation) {
            this.relation = relation;
        }

        public String getPersonaSign() {
            return personaSign;
        }

        public void setPersonaSign(String personaSign) {
            this.personaSign = personaSign;
        }
    }
}
