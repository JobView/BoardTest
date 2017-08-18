package com.wzf.boardgame.function.http.dto.response;

/**
 * @Description:
 * @author: wangzhenfei
 * @date: 2017-08-18 14:20
 */

public class SearchUserResDto {

    /**
     * isFollow : 0
     * sex : 1
     * replyCount : 41
     * nickname : 王的荣耀
     * followCount : 7
     * integral : 0
     * distance : 0.0
     * avatarUrl : http://os7i4k6w5.bkt.clouddn.com/100004/1500205854906
     * targetUserId : 100004
     * personaSign : 我的就是我的！
     * postCount : 8
     * fansCount : 0
     */

    private int isFollow;
    private int sex;
    private int replyCount;
    private String nickname;
    private int followCount;
    private int integral;
    private double distance;
    private String avatarUrl;
    private String targetUserId;
    private String personaSign;
    private int postCount;
    private int fansCount;

    public int getIsFollow() {
        return isFollow;
    }

    public void setIsFollow(int isFollow) {
        this.isFollow = isFollow;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getFollowCount() {
        return followCount;
    }

    public void setFollowCount(int followCount) {
        this.followCount = followCount;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(String targetUserId) {
        this.targetUserId = targetUserId;
    }

    public String getPersonaSign() {
        return personaSign;
    }

    public void setPersonaSign(String personaSign) {
        this.personaSign = personaSign;
    }

    public int getPostCount() {
        return postCount;
    }

    public void setPostCount(int postCount) {
        this.postCount = postCount;
    }

    public int getFansCount() {
        return fansCount;
    }

    public void setFansCount(int fansCount) {
        this.fansCount = fansCount;
    }
}
