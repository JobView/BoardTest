package com.wzf.boardgame.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.wzf.boardgame.R;
import com.wzf.boardgame.constant.UrlService;
import com.wzf.boardgame.function.CountTimeDownManager;
import com.wzf.boardgame.function.http.OkHttpUtils;
import com.wzf.boardgame.function.http.ResponseSubscriber;
import com.wzf.boardgame.function.http.dto.request.GetSmsCodeReqDto;
import com.wzf.boardgame.function.http.dto.request.RegisterRequestDto;
import com.wzf.boardgame.ui.base.BaseActivity;
import com.wzf.boardgame.utils.REGX;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wzf on 2017/8/6.
 */

public class ForgetPwdActivity extends BaseActivity {
    @Bind(R.id.im_left)
    ImageView imLeft;
    @Bind(R.id.tv_center)
    TextView tvCenter;
    @Bind(R.id.et_phone)
    EditText etPhone;
    @Bind(R.id.et_psw)
    EditText etPsw;
    @Bind(R.id.et_sms_code)
    EditText etSmsCode;
    @Bind(R.id.btn_get_code)
    Button btnGetCode;
    @Bind(R.id.btn_commit)
    Button btnCommit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tvCenter.setText("忘记密码");
        tvCenter.setVisibility(View.VISIBLE);
        imLeft.setVisibility(View.VISIBLE);
        etPhone.setFilters(REGX.getFilters(REGX.REGX_MOBILE_INPUT));
    }

    @Override
    protected void onResume() {
        super.onResume();
        CountTimeDownManager.setListener(new CountTimeDownManager.TimeCountDownListener() {
            @Override
            public void time(int count) {
                if(count == 0){
                    btnGetCode.setText("获取验证码");
                    btnGetCode.setClickable(true);
                }else {
                    btnGetCode.setText(count + "");
                    btnGetCode.setClickable(false);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CountTimeDownManager.stop();
    }

    @OnClick({R.id.im_left, R.id.btn_get_code, R.id.btn_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_left:
                finish();
                break;
            case R.id.btn_get_code:
                getSmsCode();
                break;
            case R.id.btn_commit:
                commit();
                break;
        }
    }

    private void getSmsCode() {
        String phone = etPhone.getText().toString().trim();
        if(TextUtils.isEmpty(phone) || phone.length() != 11){
            showToast("手机号码不正确");
            return;
        }
        CountTimeDownManager.start(60);
        GetSmsCodeReqDto reqDto = new GetSmsCodeReqDto();
        reqDto.setUserMobile(phone);
        reqDto.setCodeType(GetSmsCodeReqDto.SMS_CODE_PSW);
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

    private void commit() {
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
        String pwd = etPsw.getText().toString();
        if(TextUtils.isEmpty(pwd) || pwd.length() < 6 || pwd.length() > 20){
            showToast("密码应该是6-20位");
            return;
        }

        RegisterRequestDto dto = new RegisterRequestDto();
        dto.setSmsCode(smsCode);
        dto.setUserPwd(pwd);
        dto.setUserMobile(phone);
        UrlService.SERVICE.changePwd(dto.toEncodeString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new ResponseSubscriber<Object>(this, true) {
                    @Override
                    public void onSuccess(Object loginResponseDto) throws Exception {
                        super.onSuccess(loginResponseDto);
                        showToast("密码修改成功");
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
