package com.wzf.boardgame.ui.activity;

import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.wzf.boardgame.R;
import com.wzf.boardgame.constant.UrlService;
import com.wzf.boardgame.function.http.OkHttpUtils;
import com.wzf.boardgame.function.http.ResponseSubscriber;
import com.wzf.boardgame.function.http.dto.request.GetSmsCodeReqDto;
import com.wzf.boardgame.function.http.dto.request.RegisterRequestDto;
import com.wzf.boardgame.ui.base.BaseActivity;
import com.wzf.boardgame.utils.DebugLog;
import com.wzf.boardgame.utils.REGX;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wzf on 2017/7/2.
 */

public class RegisterActivity extends BaseActivity {


    @Bind(R.id.im_left)
    ImageView imLeft;
    @Bind(R.id.tv_center)
    TextView tvCenter;
    @Bind(R.id.et_nickname)
    EditText etNickname;
    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.et_psw)
    EditText etPsw;
    @Bind(R.id.et_sms_code)
    EditText etSmsCode;
    @Bind(R.id.btn_get_code)
    Button btnGetCode;
    @Bind(R.id.et_invite_code)
    EditText etInviteCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tvCenter.setText("注册");
        tvCenter.setVisibility(View.VISIBLE);
        imLeft.setVisibility(View.VISIBLE);
        etPhone.setFilters(REGX.getFilters(REGX.REGX_MOBILE_INPUT));
    }

    @OnClick({R.id.im_left, R.id.btn_get_code, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_left:
                finish();
                break;
            case R.id.btn_get_code:
                getSmsCode();
                break;
            case R.id.btn_login:
                register();
                break;
        }
    }

    private void getSmsCode() {
        String phone = etPhone.getText().toString().trim();
        if(TextUtils.isEmpty(phone) || phone.length() != 11){
            showToast("手机号码不正确");
            return;
        }


        GetSmsCodeReqDto reqDto = new GetSmsCodeReqDto();
        reqDto.setUserMobile(phone);
        reqDto.setCodeType(GetSmsCodeReqDto.SMS_CODE_REGISTER);
        OkHttpUtils.getInstance().getUrlService(UrlService.class).smsCode(reqDto.toEncodeString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new ResponseSubscriber<Object>(this, true) {
                    @Override
                    public void onSuccess(Object loginResponseDto) throws Exception {
                        showToast("验证码已发送");
                    }

                    @Override
                    public void onFailure(int code, String message) throws Exception {
                        super.onFailure(code, message);
                        showToast(message);
                    }
                });

    }

    private void register() {
        String phone = etPhone.getText().toString();
        if(TextUtils.isEmpty(phone) || phone.length() != 11){
            showToast("手机号码不正确");
            return;
        }
        String smsCode = etSmsCode.getText().toString();
        if(TextUtils.isEmpty(smsCode)){
            showToast("验证码不能为空");
            return;
        }
        String nickName = etNickname.getText().toString();
        if(TextUtils.isEmpty(nickName)){
            showToast("昵称不能为空");
            return;
        }
        if(nickName.length() > 15){
            showToast("昵称不能超过15个字符");
            return;
        }
        String pwd = etPsw.getText().toString();
        if(TextUtils.isEmpty(pwd) || pwd.length() < 6 || pwd.length() > 20){
            showToast("密码应该是6-20位");
            return;
        }
        String inviteCode = etInviteCode.getText().toString();
        RegisterRequestDto dto = new RegisterRequestDto();
        dto.setNickname(nickName);
        dto.setSmsCode(smsCode);
        dto.setUserPwd(pwd);
        dto.setUserMobile(phone);
        dto.setUserId(inviteCode);
        UrlService.SERVICE.register(dto.toEncodeString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new ResponseSubscriber<Object>(this, true) {
                    @Override
                    public void onSuccess(Object loginResponseDto) throws Exception {
                        super.onSuccess(loginResponseDto);
                        showToast("注册成功");
                        finish();
                    }
//
                    @Override
                    public void onFailure(int code, String message) throws Exception {
                        super.onFailure(code, message);
                        showToast(message);
                    }
                });
    }

}
