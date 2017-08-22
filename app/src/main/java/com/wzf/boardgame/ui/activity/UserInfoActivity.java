package com.wzf.boardgame.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wzf.boardgame.R;
import com.wzf.boardgame.constant.UrlService;
import com.wzf.boardgame.function.http.ResponseSubscriber;
import com.wzf.boardgame.function.http.dto.request.FollowReqDto;
import com.wzf.boardgame.function.http.dto.request.GetUserRelationReqDto;
import com.wzf.boardgame.function.http.dto.request.UserInfoReqDto;
import com.wzf.boardgame.function.http.dto.response.BaseResponse;
import com.wzf.boardgame.function.http.dto.response.UserInfoResDto;
import com.wzf.boardgame.function.imageloader.ImageLoader;
import com.wzf.boardgame.ui.adapter.RcyCommonAdapter;
import com.wzf.boardgame.ui.adapter.RcyViewHolder;
import com.wzf.boardgame.ui.base.BaseActivity;
import com.wzf.boardgame.ui.model.UserInfo;
import com.wzf.boardgame.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @Description:
 * @author: wangzhenfei
 * @date: 2017-08-01 14:43
 */

public class UserInfoActivity extends BaseActivity {
    @Bind(R.id.im_left)
    ImageView imLeft;
    @Bind(R.id.tv_center)
    TextView tvCenter;
    @Bind(R.id.im_right1)
    ImageView imRight1;
    @Bind(R.id.im_avatar)
    ImageView imAvatar;
    @Bind(R.id.im_gender)
    ImageView imGender;
    @Bind(R.id.tv_nickname)
    TextView tvNickname;
    @Bind(R.id.tv_city)
    TextView tvCity;
    @Bind(R.id.tv_distance)
    TextView tvDistance;
    @Bind(R.id.im_v)
    ImageView imV;
    @Bind(R.id.tv_description)
    TextView tvDescription;
    @Bind(R.id.tv_attention_count)
    TextView tvAttentionCount;
    @Bind(R.id.tv_fun_count)
    TextView tvFunCount;
    @Bind(R.id.tv_subject_count)
    TextView tvSubjectCount;
    @Bind(R.id.tv_replay_count)
    TextView tvReplayCount;

    private String uid;
    private UserInfoResDto resDto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        uid = getIntent().getStringExtra("uid");
        initView();
        initData();
    }


    private void initView() {
        imLeft.setVisibility(View.VISIBLE);
        tvCenter.setText("我的关注");
        tvCenter.setVisibility(View.VISIBLE);
        imRight1.setImageResource(R.mipmap.otheruser_btn_follow_nor);
        imRight1.setVisibility(View.VISIBLE);
    }


    private void initData() {
        UserInfoReqDto reqDto = new UserInfoReqDto();
        reqDto.setUserId(uid);
        UrlService.SERVICE.getUserInformation(reqDto.toEncodeString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new ResponseSubscriber<UserInfoResDto>(this, true) {
                    @Override
                    public void onSuccess(UserInfoResDto responseDto) throws Exception {
                        super.onSuccess(responseDto);
                        resDto = responseDto;
                        updateView();
                    }
                    @Override
                    public void onFailure(int code, String message) throws Exception {
                        super.onFailure(code, message);
                        showToast(message);
                    }
                });
    }

    private void updateView() {
        if(resDto == null){
            return;
        }

        ImageLoader.getInstance().displayOnlineRoundImage(resDto.getAvatarUrl(), imAvatar);
        if(resDto.getSex() == 1){
            imGender.setImageResource(R.mipmap.otheruser_icon_man_nor);
        }else if(resDto.getSex() == 2){
            imGender.setImageResource(R.mipmap.otheruser_icon_woman_nor);
        }else {
            imGender.setVisibility(View.GONE);
        }
        tvCity.setText(resDto.getCityName());
        tvDistance.setText(resDto.getDistance() == 0 ? "" : resDto.getDistance() + "km");
        tvNickname.setText(resDto.getNickname());
        imV.setVisibility(resDto.getAuthLevel() == 0 ? View.GONE : View.VISIBLE);
        tvDescription.setText(resDto.getPersonaSign());
        tvAttentionCount.setText(resDto.getFollowCount() + "");
        tvFunCount.setText(resDto.getFansCount() + "");
        tvSubjectCount.setText(resDto.getPostCount() + "");
        tvReplayCount.setText(resDto.getReplyCount() + "");
        imRight1.setImageResource(resDto.getIsFollow() == 0? R.mipmap.otheruser_btn_follow_nor : R.mipmap.otheruser_btn_following_nor);
    }


    @OnClick({R.id.im_left, R.id.im_right1,R.id.ll_attention, R.id.ll_fans, R.id.ll_subject, R.id.ll_reply, R.id.im_avatar})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.im_left:
                finish();
                break;
            case R.id.im_right1:
                if(resDto != null){
                    changeFollowStatus();
                }
                break;
            case R.id.ll_attention:
                if(resDto != null){
                    FollowsActivity.startMethod(this, uid);
                }

                break;
            case R.id.ll_fans:
                if(resDto != null){
                    FansActivity.startMethod(this, uid);
                }
                break;

            case R.id.ll_subject:
                if(resDto != null){
                    SubjectsActivity.startMethod(this, uid);
                }

                break;
            case R.id.ll_reply:
                if(resDto != null){
                    ReplyActivity.startMethod(this, uid);
                }
                break;
            case R.id.im_avatar:
                if(resDto != null){
                    List<String> list = new ArrayList<>();
                    list.add(resDto.getAvatarUrl());
                    ViewUtils.previewPicture(this, 0, list);
                }
                break;

        }
    }

    private void changeFollowStatus() {
        final FollowReqDto reqDto = new FollowReqDto();
        reqDto.setFollowUserId(uid);
        Observable<BaseResponse<Object>> userInformation;
        if(resDto.getIsFollow() == 0){ //未关注
            userInformation = UrlService.SERVICE.followUser(reqDto.toEncodeString());
        }else {
            userInformation = UrlService.SERVICE.cancelFollowUser(reqDto.toEncodeString());
        }
        userInformation.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new ResponseSubscriber<Object>(this, true) {
                    @Override
                    public void onSuccess(Object responseDto) throws Exception {
                        super.onSuccess(responseDto);
                        resDto.setIsFollow(resDto.getIsFollow() == 0 ? 1: 0);
                        resDto.setFansCount(resDto.getIsFollow() == 0 ? resDto.getFansCount() - 1 : resDto.getFansCount() + 1);
                        showToast(resDto.getIsFollow() == 0? "取消关注成功" : "关注成功");
                        updateView();
                    }
                    @Override
                    public void onFailure(int code, String message) throws Exception {
                        super.onFailure(code, message);
                        showToast(message);
                    }
                });
    }

    public static void startMethod(Context context, String uid){
        context.startActivity(new Intent(context, UserInfoActivity.class).putExtra("uid", uid));
    }
}
