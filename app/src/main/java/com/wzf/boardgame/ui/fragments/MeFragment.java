package com.wzf.boardgame.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.wzf.boardgame.ui.activity.FansActivity;
import com.wzf.boardgame.ui.activity.FollowsActivity;
import com.wzf.boardgame.ui.activity.MeInfoEditActivity;
import com.wzf.boardgame.ui.activity.ReplyActivity;
import com.wzf.boardgame.ui.activity.StoreActivity;
import com.wzf.boardgame.ui.activity.SubjectsActivity;
import com.wzf.boardgame.ui.base.BaseFragment;
import com.wzf.boardgame.ui.model.UserInfo;
import com.wzf.boardgame.utils.AppDeviceInfo;
import com.wzf.boardgame.utils.ViewUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wzf on 2017/7/5.
 */

public class MeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

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
    @Bind(R.id.im_message_red_point)
    ImageView imMessageRedPoint;
    @Bind(R.id.tv_invite_code)
    TextView tvInviteCode;
    @Bind(R.id.tv_version_code)
    TextView tvVersionCode;
    @Bind(R.id.srl)
    SwipeRefreshLayout srl;

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
        srl.setOnRefreshListener(this);
        //实现首次自动显示加载提示
//        srl.post(new Runnable() {
//            @Override
//            public void run() {
//                srl.setRefreshing(true);
//            }
//        });
        ViewUtils.setSwipeRefreshLayoutSchemeResources(srl);
        onRefresh();
        
    }

    @Override
    public void onRefresh() {
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
                        srl.setRefreshing(false);
                        if(responseDto != null){
                            UserInfo.getInstance().setUserInfo(responseDto);
                            updateView();
                        }
                    }
                    @Override
                    public void onFailure(int code, String message) throws Exception {
                        super.onFailure(code, message);
                        srl.setRefreshing(false);
                        bActivity.showToast(message);
                    }
                });
    }

    private void updateView() {
        if(UserInfo.getInstance().hasUpdate){
            if(srl.getVisibility() != View.VISIBLE){
                tvUnlogin.setVisibility(View.GONE);
                srl.setVisibility(View.VISIBLE);
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
            imV.setVisibility(UserInfo.getInstance().getAuthLevel() == 0 ? View.GONE : View.VISIBLE);
            tvDescription.setText(UserInfo.getInstance().getPersonaSign());
            tvAttentionCount.setText(UserInfo.getInstance().getFollowCount());
            tvFunCount.setText(UserInfo.getInstance().getFansCount());
            tvSubjectCount.setText(UserInfo.getInstance().getPostCount());
            tvReplayCount.setText(UserInfo.getInstance().getReplyCount());
            tvInviteCode.setText(UserInfo.getInstance().getUid());
            tvVersionCode.setText(AppDeviceInfo.getAppVersionName(MyApplication.getAppInstance()));
        }
    }


    @OnClick({R.id.ll_message, R.id.ll_user_info, R.id.ll_store, R.id.tv_unlogin, R.id.ll_attention, R.id.ll_fans, R.id.ll_subject, R.id.ll_reply})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_message:
                break;
            case R.id.ll_user_info:
                startActivity(new Intent(bActivity, MeInfoEditActivity.class));
                break;
            case R.id.ll_store:
                startActivity(new Intent(bActivity, StoreActivity.class));
                break;
            case R.id.tv_unlogin:
                UserInfo.isLogin(bActivity);
                break;
            case R.id.ll_attention:
                FollowsActivity.startMethod(bActivity, UserInfo.getInstance().getUid());
                break;
            case R.id.ll_fans:
                FansActivity.startMethod(bActivity, UserInfo.getInstance().getUid());
                break;
            case R.id.ll_subject:
                SubjectsActivity.startMethod(bActivity, UserInfo.getInstance().getUid());
                break;
            case R.id.ll_reply:
                ReplyActivity.startMethod(bActivity, UserInfo.getInstance().getUid());
                break;
        }
    }
}
