package com.wzf.boardgame.ui.model;



import com.wzf.boardgame.function.http.dto.request.BaseRequestDto;
import com.wzf.boardgame.function.http.dto.request.HeaderParams;
import com.wzf.boardgame.function.http.dto.response.LoginResDto;
import com.wzf.boardgame.utils.PreferencesHelper;

import rx.schedulers.Schedulers;

/**
 * Created by YOUNG on 2017-03-17.
 */

public class UserInfo {
    public static final String TOURIST = "TOURIST";
    public static final String USER = "USER";

    private static UserInfo userInfo;
    private final PreferencesHelper mHelper;
    private String uid = "";
    private String token = "";
    private String phone = "phone";
    private String psw = "psw";
    private String nickname = "nickname";
    private String avatarUrl = "avatarUrl";
    private String personaSign = "personaSign";
    private String birthday = "birthday";
    private String sex = "sex"; // 性别，1：男，2：女
    private String followCount = "followCount";
    private String fansCount = "fansCount";
    private String postCount = "postCount";
    private String replyCount = "replyCount";//是否登陆 TOURIST游客， USER用户

    private UserInfo() {
        mHelper = new PreferencesHelper(PreferencesHelper.TB_USER);
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPhone() {
        return mHelper.getValue(phone) == null ? "" : mHelper.getValue(phone);
    }

    public void setPhone(String phone) {
        mHelper.setValue(this.phone, phone);
    }

    public String getBirthday() {
        return mHelper.getValue(birthday) == null ? "" : mHelper.getValue(birthday);
    }

    public void setBirthday(String birthday) {
        mHelper.setValue(this.birthday, birthday);
    }

    public String getPsw() {
        return mHelper.getValue(psw) == null ? "" : mHelper.getValue(psw);
    }

    public void setPsw(String psw) {
        mHelper.setValue(this.psw, psw);
    }



    public String getNickname() {
        return mHelper.getValue(nickname) == null ? "" : mHelper.getValue(nickname);
    }

    public void setNickname(String nickname) {
        mHelper.setValue(this.nickname, nickname);
    }

    public String getAvatarUrl() {
        return mHelper.getValue(avatarUrl) == null ? "" : mHelper.getValue(avatarUrl);
    }

    public void setAvatarUrl(String avatarUrl) {
        mHelper.setValue(this.avatarUrl, avatarUrl);
    }

    public String getPersonaSign() {
        return mHelper.getValue(personaSign) == null ? "" : mHelper.getValue(personaSign);
    }

    public void setPersonaSign(String personaSign) {
        mHelper.setValue(this.personaSign, personaSign);
    }

    public String getSex() {
        return mHelper.getValue(sex) == null ? "" : mHelper.getValue(sex);
    }

    public void setSex(String sex) {
        mHelper.setValue(this.sex, sex);
    }

    public String getFollowCount() {
        return mHelper.getValue(followCount) == null ? "" : mHelper.getValue(followCount);
    }

    public void setFollowCount(String followCount) {
        mHelper.setValue(this.followCount, followCount);
    }

    public String getFansCount() {
        return mHelper.getValue(fansCount) == null ? "" : mHelper.getValue(fansCount);
    }

    public void setFansCount(String fansCount) {
        mHelper.setValue(this.fansCount, fansCount);
    }

    public String getPostCount() {
        return mHelper.getValue(postCount) == null ? "" : mHelper.getValue(postCount);
    }

    public void setPostCount(String postCount) {
        mHelper.setValue(this.postCount, postCount);
    }

    public String getReplyCount() {
        return mHelper.getValue(replyCount) == null ? "" : mHelper.getValue(replyCount);
    }

    public void setReplyCount(String replyCount) {
        mHelper.setValue(this.replyCount, replyCount);
    }

    public static UserInfo getInstance() {
        if (userInfo == null) {
            synchronized (UserInfo.class) {
                if (userInfo == null) {
                    userInfo = new UserInfo();
                }
            }
        }
        return userInfo;
    }

    public void setUser(LoginResDto dto){
        setUid(dto.getUserId());
        setToken(dto.getToken());
        HeaderParams.getInstance().setLoginTime(dto.getLoginTime());
    }

}
