package com.wzf.boardgame.ui.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.wzf.boardgame.MyApplication;
import com.wzf.boardgame.R;
import com.wzf.boardgame.constant.UrlService;
import com.wzf.boardgame.function.http.ResponseSubscriber;
import com.wzf.boardgame.function.http.dto.request.ChangeUserInfoReqDto;
import com.wzf.boardgame.function.http.dto.request.UserInfoReqDto;
import com.wzf.boardgame.function.http.dto.response.UserInfoResDto;
import com.wzf.boardgame.function.imageloader.ImageLoader;
import com.wzf.boardgame.function.imageloader.ImagePickerImageLoader;
import com.wzf.boardgame.function.map.BaiDuMapManager;
import com.wzf.boardgame.function.upload_qiniu.UpLoadQiNiuManager;
import com.wzf.boardgame.ui.base.BaseActivity;
import com.wzf.boardgame.ui.dialog.AlertCommonDialog;
import com.wzf.boardgame.ui.dialog.CommentDialog;
import com.wzf.boardgame.ui.model.UserInfo;
import com.wzf.boardgame.utils.ActivityCollector;
import com.wzf.boardgame.utils.ScreenUtils;
import com.wzf.boardgame.utils.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wzf on 2017/7/16.
 * 我的信息编辑
 */

public class MeInfoEditActivity extends BaseActivity {
    private static final int REQUEST_CODE_SELECT = 1004;
    @Bind(R.id.im_left)
    ImageView imLeft;
    @Bind(R.id.tv_center)
    TextView tvCenter;
    @Bind(R.id.im_avatar)
    ImageView imAvatar;
    @Bind(R.id.tv_nickname)
    TextView tvNickname;
    @Bind(R.id.tv_sine)
    TextView tvSine;
    @Bind(R.id.tv_gender)
    TextView tvGender;
    @Bind(R.id.tv_birthday)
    TextView tvBirthday;
    @Bind(R.id.tv_integral)
    TextView tvIntegral;
    @Bind(R.id.tv_city)
    TextView tvCity;
    @Bind(R.id.tv_phone)
    TextView tvPhone;
    @Bind(R.id.tv_register_time)
    TextView tvRegisterTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me_info_edit);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        imLeft.setVisibility(View.VISIBLE);
        tvCenter.setText("个人信息");
        tvCenter.setVisibility(View.VISIBLE);
        updateView();
    }

    private void updateView() {
        ImageLoader.getInstance().displayOnlineRoundImage(UserInfo.getInstance().getAvatarUrl(), imAvatar);
        if("1".equals(UserInfo.getInstance().getSex())){
            tvGender.setText("男");
        }else if("2".equals(UserInfo.getInstance().getSex())){
            tvGender.setText("女");
        }else {
            tvGender.setText("");
        }
        tvNickname.setText(UserInfo.getInstance().getNickname());
        tvBirthday.setText(UserInfo.getInstance().getBirthday());
        tvSine.setText(UserInfo.getInstance().getPersonaSign());
        tvPhone.setText(UserInfo.getInstance().getPhone());
        tvRegisterTime.setText(UserInfo.getInstance().getRegTime());
        tvCity.setText(BaiDuMapManager.getInstance().getCityName());
        tvIntegral.setText(UserInfo.getInstance().getIntegral());
    }


    private void updateUserInfo(final ChangeUserInfoReqDto reqDto) {
        if(reqDto == null ){
            return;
        }
        UrlService.SERVICE.changeUserInformation(reqDto.toEncodeString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new ResponseSubscriber<Object>(this, true) {
                    @Override
                    public void onSuccess(Object responseDto) throws Exception {
                        super.onSuccess(responseDto);
                        reqDto.updateUserUnfo();
                        updateView();
                    }
                    @Override
                    public void onFailure(int code, String message) throws Exception {
                        super.onFailure(code, message);
                        showToast(message);
                    }
                });
    }

    private void uploadAvatar(String path) {
        UpLoadQiNiuManager.getInstance().uploadFile(new File(path), new UpLoadQiNiuManager.QiNiuUpLoadListener() {
            @Override
            public void progress(double percent) {
                showDialog("图片上传中：" + percent + "%");
            }

            @Override
            public void complete(boolean success, String result) {
                dissMissDialog();
                ChangeUserInfoReqDto reqDto = new ChangeUserInfoReqDto();
                reqDto.setAvatarUrl(result);
                updateUserInfo(reqDto);
            }
        });
    }

    /**
     * 图片选择使用ImagePicker库
     */
    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setCrop(true);                            //是否需要剪切
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //矩形剪切
        // 宽高默认280
        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, ScreenUtils.getScreenWidth(MyApplication.getAppInstance()), getResources().getDisplayMetrics());
        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, ScreenUtils.dpToPix(400, MyApplication.getAppInstance()), getResources().getDisplayMetrics());
        imagePicker.setFocusWidth(width);
        imagePicker.setFocusHeight(width);
        // 图片保存的宽高800
        imagePicker.setOutPutX(width);
        imagePicker.setOutPutY(width);
        imagePicker.setImageLoader(new ImagePickerImageLoader()); //图片加载器
        imagePicker.setMultiMode(false);                          //单选
    }

    @OnClick({R.id.im_left, R.id.ll_nickname, R.id.ll_sine, R.id.ll_gender, R.id.ll_birthday, R.id.ll_change_pwd, R.id.im_avatar, R.id.tv_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_avatar:
                initImagePicker();
                ImagePicker.getInstance().setSelectLimit(1);
                Intent intent = new Intent(this, ImageGridActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SELECT);
                break;
            case R.id.im_left:
                finish();
                break;
            case R.id.ll_nickname:
                new CommentDialog(this, UserInfo.getInstance().getNickname(), "请输入昵称", 10) {
                    @Override
                    public void sendText(String text) {
                        ChangeUserInfoReqDto reqDto = new ChangeUserInfoReqDto();
                        reqDto.setNickname(text);
                        updateUserInfo(reqDto);
                    }
                }.show();
                break;
            case R.id.ll_sine:
                new CommentDialog(this, UserInfo.getInstance().getPersonaSign(), "请输入个性签名", 20) {
                    @Override
                    public void sendText(String text) {
                        ChangeUserInfoReqDto reqDto = new ChangeUserInfoReqDto();
                        reqDto.setPersonaSign(text);
                        updateUserInfo(reqDto);
                    }
                }.show();
                break;
            case R.id.ll_gender:
                new AlertCommonDialog(this, "请选择性别", "男", "女", true){
                    @Override
                    public void onPositiveClick() {
                        ChangeUserInfoReqDto reqDto = new ChangeUserInfoReqDto();
                        reqDto.setSex(1);
                        updateUserInfo(reqDto);
                    }

                    @Override
                    public void onNegativeClick() {
                        ChangeUserInfoReqDto reqDto = new ChangeUserInfoReqDto();
                        reqDto.setSex(2);
                        updateUserInfo(reqDto);
                    }
                }.show();
                break;
            case R.id.ll_birthday:
                final Calendar calendar = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        String selectBirthday = StringUtils.concat(year,"-",StringUtils.addZero(monthOfYear + 1),"-",StringUtils.addZero(dayOfMonth));
                        String dataNow =  StringUtils.concat(calendar.get(calendar.YEAR),"-",StringUtils.addZero( calendar.get(calendar.MONTH )+ 1),"-",StringUtils.addZero(calendar.get(calendar.DAY_OF_MONTH)));
                        if(!dataNow.equals(selectBirthday)){
                            ChangeUserInfoReqDto reqDto = new ChangeUserInfoReqDto();
                            reqDto.setBirthday(selectBirthday);
                            updateUserInfo(reqDto);
                        }

                    }
                },calendar.get(calendar.YEAR), calendar.get(calendar.MONTH )+ 1, calendar.get(calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMaxDate(calendar.getTime().getTime());
                datePickerDialog.setTitle("请选择出生日期");
                datePickerDialog.show();
                break;
            case R.id.ll_change_pwd:
                startActivity(new Intent(this, ForgetPwdActivity.class));
                break;
            case R.id.tv_logout:
                new AlertCommonDialog(this, "确定退出此账户？","确定","取消"){
                    @Override
                    public void onPositiveClick() {
                        loginOut();
                    }
                }.show();

                break;
        }
    }

    private void loginOut() {
        UrlService.SERVICE.exit("")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new ResponseSubscriber<Object>(this, true) {
                    @Override
                    public void onSuccess(Object responseDto) throws Exception {
                        super.onSuccess(responseDto);
                        ActivityCollector.removeAllActivity();
                        MeInfoEditActivity.this.startActivity(new Intent(MeInfoEditActivity.this, LoginActivity.class));
                    }
                    @Override
                    public void onFailure(int code, String message) throws Exception {
                        super.onFailure(code, message);
                        showToast(message);
                    }
                });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (resultCode) {
            case ImagePicker.RESULT_CODE_ITEMS: //添加图片返回
                if (intent != null && requestCode == REQUEST_CODE_SELECT) {
                    ArrayList<ImageItem> images = (ArrayList<ImageItem>) intent.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                    if (images != null && images.size() > 0) {
                        uploadAvatar(images.get(0).path);
                    }
                }
                break;
        }
    }



}
