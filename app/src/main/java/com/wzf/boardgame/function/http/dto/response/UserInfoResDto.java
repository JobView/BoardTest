package com.wzf.boardgame.function.http.dto.response;

/**
 * Created by wzf on 2017/7/11.
 */

public class UserInfoResDto {


    /**
     * replyCount : 0
     * regTime : 2017-07-05 19:48
     * nickname : 王者
     * followCount : 0
     * userId : 100004
     * userMobile : 185****9590
     * postCount : 0
     * fansCount : 0
     */

    private int replyCount;
    private String regTime;
    private String nickname;
    private String avatarUrl;
    private String personaSign;
    private String birthday;
    private String cityName;
    private int followCount;
    private int authLevel;
    private int sex;
    private double distance;
    private int isFollow;
    private String userId;
    private String userMobile;
    private int postCount;
    private int fansCount;
    private int integral;

    public int getAuthLevel() {
        return authLevel;
    }

    public void setAuthLevel(int authLevel) {
        this.authLevel = authLevel;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public String getPersonaSign() {
        return personaSign;
    }

    public void setPersonaSign(String personaSign) {
        this.personaSign = personaSign;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getIsFollow() {
        return isFollow;
    }

    public void setIsFollow(int isFollow) {
        this.isFollow = isFollow;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public String getRegTime() {
        return regTime;
    }

    public void setRegTime(String regTime) {
        this.regTime = regTime;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
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
