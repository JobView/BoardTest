package com.wzf.boardgame.ui.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mob.MobSDK;
import com.wzf.boardgame.R;
import com.wzf.boardgame.constant.UrlService;
import com.wzf.boardgame.function.http.ResponseSubscriber;
import com.wzf.boardgame.function.http.dto.request.RegisterRequestDto;
import com.wzf.boardgame.function.http.dto.response.LoginResDto;
import com.wzf.boardgame.function.share.PlatformAuthorizeUserInfoManager;
import com.wzf.boardgame.function.share.ResourcesManager;
import com.wzf.boardgame.ui.base.BaseActivity;
import com.wzf.boardgame.ui.model.UserInfo;
import com.wzf.boardgame.utils.REGX;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wzf on 2017/7/2.
 */

public class LoginActivity extends BaseActivity {
    @Bind(R.id.tv_center)
    TextView tvCenter;
    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.et_psw)
    EditText etPsw;
    @Bind(R.id.forget_psd)
    TextView forgetPsd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();

    }

    private void initView() {
        tvCenter.setText("登录");
        tvCenter.setVisibility(View.VISIBLE);
        etPhone.setFilters(REGX.getFilters(REGX.REGX_MOBILE_INPUT));
        forgetPsd.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //中划线
        etPhone.setText(UserInfo.getInstance().getPhone());
        etPhone.setSelection(UserInfo.getInstance().getPhone().length());
        etPsw.setText(UserInfo.getInstance().getPsw());
        etPsw.setSelection(UserInfo.getInstance().getPsw().length());
    }

    @OnClick({R.id.forget_psd, R.id.to_register, R.id.btn_login, R.id.qq, R.id.wx, R.id.sina})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.forget_psd:
                break;
            case R.id.to_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.btn_login:
                login();
                break;
            case R.id.qq:
//                Platform platform = ShareSDK.getPlatform(QQ.NAME);
//                Platform.ShareParams shareParams = new  Platform.ShareParams();
//                shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
//                shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
//                shareParams.setTitleUrl(ResourcesManager.getInstace(MobSDK.getContext()).getTitleUrl());
//                shareParams.setShareType(Platform.SHARE_WEBPAGE);
////                platform.setPlatformActionListener(platformActionListener);
//                platform.share(shareParams);
                PlatformAuthorizeUserInfoManager platAuth = new PlatformAuthorizeUserInfoManager(this);
                platAuth.qqShareAuthorize();
                break;
            case R.id.wx:
                break;
            case R.id.sina:
                break;
        }
    }

    private void login() {
        final String phone = etPhone.getText().toString();
        if (TextUtils.isEmpty(phone) || phone.length() != 11) {
            showToast("手机号码不正确");
            return;
        }
        final String pwd = etPsw.getText().toString();
        if (TextUtils.isEmpty(pwd) || pwd.length() < 6 || pwd.length() > 20) {
            showToast("密码应该是6-20位");
            return;
        }
        RegisterRequestDto dto = new RegisterRequestDto();
        dto.setUserPwd(pwd);
        dto.setUserMobile(phone);
        UrlService.SERVICE.login(dto.toEncodeString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new ResponseSubscriber<LoginResDto>(this, true) {
                    @Override
                    public void onSuccess(LoginResDto loginResponseDto) throws Exception {
                        super.onSuccess(loginResponseDto);
                        UserInfo.getInstance().setLogUser(loginResponseDto);
                        UserInfo.getInstance().setPhone(phone);
                        UserInfo.getInstance().setPsw(pwd);
                        showToast("登录成功");
                        startActivity(new Intent(LoginActivity.this, MenuActivity.class));
                        finish();
                    }

                    @Override
                    public void onFailure(int code, String message) throws Exception {
                        super.onFailure(code, message);
                        showToast(message);
                    }
                });
    }


}
