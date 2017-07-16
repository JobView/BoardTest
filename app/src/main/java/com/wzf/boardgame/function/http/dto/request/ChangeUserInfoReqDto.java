package com.wzf.boardgame.function.http.dto.request;

import android.text.TextUtils;

import com.wzf.boardgame.ui.model.UserInfo;

/**
 * Created by wzf on 2017/7/11.
 */

public class ChangeUserInfoReqDto extends BaseRequestDto{


    /**
     * replyCount : 0
     * regTime : 2017-07-05 19:48
     * nickname : 王者
     */

    private String nickName;
    private String avatarUrl;
    private String personaSign;
    private String birthday;
    private Integer sex;


    public String getNickname() {
        return nickName;
    }

    public void setNickname(String nickname) {
        this.nickName = nickname;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public void updateUserUnfo(){
        if(!TextUtils.isEmpty(nickName)){
            UserInfo.getInstance().setNickname(nickName);
        }

        if(!TextUtils.isEmpty(avatarUrl)){
            UserInfo.getInstance().setAvatarUrl(avatarUrl);
        }

        if(!TextUtils.isEmpty(personaSign)){
            UserInfo.getInstance().setPersonaSign(personaSign);
        }

        if(!TextUtils.isEmpty(birthday)){
            UserInfo.getInstance().setBirthday(birthday);
        }

        if(sex != null && sex.intValue() != 0){
            UserInfo.getInstance().setSex(String.valueOf(sex));
        }


    }
}
