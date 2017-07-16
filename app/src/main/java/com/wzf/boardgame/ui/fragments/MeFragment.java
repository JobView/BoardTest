package com.wzf.boardgame.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.wzf.boardgame.MyApplication;
import com.wzf.boardgame.R;
import com.wzf.boardgame.constant.UrlService;
import com.wzf.boardgame.function.http.ResponseSubscriber;
import com.wzf.boardgame.function.http.dto.request.UserInfoReqDto;
import com.wzf.boardgame.function.http.dto.response.UserInfoResDto;
import com.wzf.boardgame.function.imageloader.ImageLoader;
import com.wzf.boardgame.ui.activity.MeInfoEditActivity;
import com.wzf.boardgame.ui.base.BaseFragment;
import com.wzf.boardgame.ui.model.UserInfo;
import com.wzf.boardgame.utils.AppDeviceInfo;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wzf on 2017/7/5.
 */

public class MeFragment extends BaseFragment {

    View mRootView;
    @Bind(R.id.tv_center)
    TextView tvCenter;
    @Bind(R.id.tv_unlogin)
    TextView tvUnlogin;
    @Bind(R.id.im_avatar)
    ImageView imAvatar;
    @Bind(R.id.im_gender)
    ImageView imGender;
    @Bind(R.id.tv_nickname)
    TextView tvNickname;
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
    @Bind(R.id.im_message_red_point)
    ImageView imMessageRedPoint;
    @Bind(R.id.tv_invite_code)
    TextView tvInviteCode;
    @Bind(R.id.tv_version_code)
    TextView tvVersionCode;
    @Bind(R.id.scrollView)
    ScrollView scrollView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = bActivity.getLayoutInflater().inflate(R.layout.fragment_me, null);
            ButterKnife.bind(this, mRootView);
            initView();
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateView();
    }

    private void initView() {
        tvCenter.setText("个人资料");
        tvCenter.setVisibility(View.VISIBLE);
        initData();
        
    }

    private void initData() {
        UserInfoReqDto reqDto = new UserInfoReqDto();
        reqDto.setUserId(UserInfo.getInstance().getUid());
        UrlService.SERVICE.getUserInformation("")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new ResponseSubscriber<UserInfoResDto>(bActivity, true) {
                    @Override
                    public void onSuccess(UserInfoResDto responseDto) throws Exception {
                        super.onSuccess(responseDto);
                        if(responseDto != null){
                            UserInfo.getInstance().setUserInfo(responseDto);
                            updateView();
                        }
                    }
                    @Override
                    public void onFailure(int code, String message) throws Exception {
                        super.onFailure(code, message);
                        bActivity.showToast(message);
                    }
                });
    }

    private void updateView() {
        if(UserInfo.getInstance().hasUpdate){
            if(scrollView.getVisibility() != View.VISIBLE){
                tvUnlogin.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
            }
            ImageLoader.getInstance().displayOnlineRoundImage(UserInfo.getInstance().getAvatarUrl(), imAvatar);
            if("1".equals(UserInfo.getInstance().getSex())){
                imGender.setImageResource(R.mipmap.otheruser_icon_man_nor);
            }else if("2".equals(UserInfo.getInstance().getSex())){
                imGender.setImageResource(R.mipmap.otheruser_icon_woman_nor);
            }else {
                imGender.setVisibility(View.GONE);
            }
            tvNickname.setText(UserInfo.getInstance().getNickname());
            tvDescription.setText(UserInfo.getInstance().getPersonaSign());
            tvAttentionCount.setText(UserInfo.getInstance().getFollowCount());
            tvFunCount.setText(UserInfo.getInstance().getFansCount());
            tvSubjectCount.setText(UserInfo.getInstance().getPostCount());
            tvReplayCount.setText(UserInfo.getInstance().getReplyCount());
            tvInviteCode.setText(UserInfo.getInstance().getUid());
            tvVersionCode.setText(AppDeviceInfo.getAppVersionName(MyApplication.getAppInstance()));
        }
    }


    @OnClick({R.id.ll_message, R.id.ll_user_info, R.id.ll_store})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_message:
                break;
            case R.id.ll_user_info:
                startActivity(new Intent(bActivity, MeInfoEditActivity.class));
                break;
            case R.id.ll_store:
                break;
        }
    }
}
