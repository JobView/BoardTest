package com.wzf.boardgame.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
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
import com.wzf.boardgame.function.http.dto.request.CommitPostReqDto;
import com.wzf.boardgame.function.http.dto.request.GameFeedBackReqDto;
import com.wzf.boardgame.function.http.dto.response.GameFeedBackTypeListResDto;
import com.wzf.boardgame.function.imageloader.ImageLoader;
import com.wzf.boardgame.function.imageloader.ImageLoaderToBitmapListener;
import com.wzf.boardgame.function.imageloader.ImagePickerImageLoader;
import com.wzf.boardgame.function.upload_qiniu.UpLoadQiNiuManager;
import com.wzf.boardgame.ui.adapter.CommonAdapter;
import com.wzf.boardgame.ui.adapter.ViewHolder;
import com.wzf.boardgame.ui.base.BaseActivity;
import com.wzf.boardgame.utils.DebugLog;
import com.wzf.boardgame.utils.ScreenUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @Description:
 * @author: wangzhenfei
 * @date: 2017-08-04 10:15
 */

public class GameFeedBackActivity extends BaseActivity {
    private static final int REQUEST_CODE_SELECT = 1004;
    @Bind(R.id.im_left)
    ImageView imLeft;
    @Bind(R.id.tv_center)
    TextView tvCenter;
    @Bind(R.id.im_right1)
    ImageView imRight1;
    @Bind(R.id.sp)
    Spinner sp;

    @Bind(R.id.et_title)
    EditText etTitle;
    @Bind(R.id.et_new_content)
    EditText etNewContent;

    private ProgressDialog progressDialog;

    private GameFeedBackTypeListResDto.FeedbackTypeListBean selectItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);
        initView();
        initData();
        initImagePicker();
    }


    private void initView() {
        imLeft.setVisibility(View.VISIBLE);
        tvCenter.setText("意见反馈");
        tvCenter.setVisibility(View.VISIBLE);
        imRight1.setImageResource(R.mipmap.tabbar_btn_game_nor);
        imRight1.setVisibility(View.VISIBLE);
    }

    /**
     * 图片选择使用ImagePicker库
     */
    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setCrop(true);                            //是否需要剪切
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //矩形剪切
        // 宽高默认280
//        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, ScreenUtils.getScreenWidth(MyApplication.getAppInstance()), getResources().getDisplayMetrics());
        int width = ScreenUtils.getScreenWidth(MyApplication.getAppInstance());
//        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, ScreenUtils.dpToPix((int)(width * 1.0 * 2 / 3), MyApplication.getAppInstance()), getResources().getDisplayMetrics());
        int height = (int)(width * 1.0 * 2 / 3);
        imagePicker.setFocusWidth(width);
        imagePicker.setFocusHeight(height);
        // 图片保存的宽高800
        imagePicker.setOutPutX(width);
        imagePicker.setOutPutY(height);
        imagePicker.setImageLoader(new ImagePickerImageLoader()); //图片加载器
        imagePicker.setMultiMode(false);                          //单选
    }


    private void initData() {
        UrlService.SERVICE.getFeedbackTypeList("")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new ResponseSubscriber<GameFeedBackTypeListResDto>() {
                    @Override
                    public void onSuccess(GameFeedBackTypeListResDto dto) throws Exception {
                        super.onSuccess(dto);
                        setSpinnerData(dto);
                    }

                    @Override
                    public void onFailure(int code, String message) throws Exception {
                        super.onFailure(code, message);
                        showToast(message);
                    }
                });
    }

    private void setSpinnerData(GameFeedBackTypeListResDto dto) {
        final List<GameFeedBackTypeListResDto.FeedbackTypeListBean>  list = dto.getFeedbackTypeList();
        selectItem = list != null && list.size() > 0 ? list.get(0) : null;
        ArrayAdapter< String> adapter =
                new ArrayAdapter<String>( this,
                        android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        for(GameFeedBackTypeListResDto.FeedbackTypeListBean bean : list){
            adapter.add(bean.getFeedbackTypeName());
        }
        sp.setAdapter(adapter);
        sp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem = list.get(position);
            }
        });
    }

    @OnClick({R.id.im_left, R.id.im_right1, R.id.im_picture})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.im_left:
                finish();
                break;
            case R.id.im_right1:
                commitFeedBack();
                break;
            case R.id.im_picture:
                ImagePicker.getInstance().setSelectLimit(1);
                Intent intent = new Intent(this, ImageGridActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SELECT);
                break;
            
        }
    }

    private void commitFeedBack() {
        if(selectItem == null){
            showToast("请选择反馈类型");
            return;
        }
        String title = etTitle.getText().toString().trim();
        if(TextUtils.isEmpty(title)){
            showToast("标题不能为空");
            return;
        }
        String content = etNewContent.getText().toString();
        if(TextUtils.isEmpty(content)){
            showToast("内容不能为空");
            return;
        }
        DebugLog.i(content);
        List<String> urls = new ArrayList();
        String result = getUrlAndReplace(content, urls);
        GameFeedBackReqDto reqDto = new GameFeedBackReqDto();
        reqDto.setFeedbackType(selectItem.getFeedbackType());
        reqDto.setFeedbackTitle(title);
        reqDto.setFeedbackContent(content);
        reqDto.setFeedbackImgUrl(urls);
        UrlService.SERVICE.sendFeedback(reqDto.toEncodeString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new ResponseSubscriber<Object>(this, true) {
                    @Override
                    public void onSuccess(Object responseDto) throws Exception {
                        super.onSuccess(responseDto);
                        showToast("发布成功");
                        finish();
                    }
                    @Override
                    public void onFailure(int code, String message) throws Exception {
                        super.onFailure(code, message);
                        showToast(message);
                    }
                });
    }

    private void uploadPicture(final String path) {
        if(progressDialog == null){
            progressDialog = new ProgressDialog(GameFeedBackActivity.this);
            progressDialog.setTitle("图片上传中...");
            progressDialog.show();
        }
        UpLoadQiNiuManager.getInstance().uploadFile(new File(path), new UpLoadQiNiuManager.QiNiuUpLoadListener() {
            @Override
            public void progress(double percent) {
                if(progressDialog == null){
                    progressDialog = new ProgressDialog(GameFeedBackActivity.this);
                }
                progressDialog.setTitle("图片上传中：" + percent + "%");
                if(!progressDialog.isShowing()){
                    progressDialog.show();
                }

            }

            @Override
            public void complete(boolean success, String result) {
                if(progressDialog != null && progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
                ChangeUserInfoReqDto reqDto = new ChangeUserInfoReqDto();
                insertPicture(path, result);
            }
        });

    }

    private void insertPicture(final String path, final String result) {
        int screenW = (int)(ScreenUtils.getScreenWidth(MyApplication.getAppInstance()) * 1.0f * 3 / 4);
        int height = (int )(screenW * (2 * 1.0 / 3));
        ImageLoader.getInstance().fileToBitmap(path,screenW, height, new ImageLoaderToBitmapListener() {
            @Override
            public void onLoadFinish(Bitmap bitmap) {
                ImageSpan imageSpan = new ImageSpan(GameFeedBackActivity.this, bitmap);
                String tempUrl = "<img src=\"" + result + "\" />";
                SpannableString spannableString = new SpannableString(tempUrl);
                spannableString.setSpan(imageSpan, 0, tempUrl.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                // 将选择的图片追加到EditText中光标所在位置
                int index = etNewContent.getSelectionStart();
                // 获取光标所在位置
                Editable edit_text = etNewContent.getEditableText();
                if (index < 0 || index >= edit_text.length()) {
                    edit_text.append("\n");
                    edit_text.append(spannableString);
                    edit_text.append("\n");
                } else {
                    edit_text.insert(index, "\n");
                    edit_text.insert(index + 1, spannableString);
                    edit_text.insert(index + 1 + spannableString.length(), "\n");
                }
            }
        });
    }


    public  String getUrlAndReplace(String str, List<String> urls){
        System.out.println(str);
        int start = str.indexOf("<img");
        int end = str.indexOf("/>");
        System.out.println(start);
        System.out.println(end);
        if(start < 0){
            return str;
        }
        String url = str.substring(start + 10, end - 2);
        urls.add(url);
        System.out.println(url);
        return getUrlAndReplace(str.replace(str.substring(start, end + 2), "{$img$}"), urls);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (resultCode) {
            case ImagePicker.RESULT_CODE_ITEMS: //添加图片返回
                if (intent != null && requestCode == REQUEST_CODE_SELECT) {
                    ArrayList<ImageItem> images = (ArrayList<ImageItem>) intent.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                    if (images != null && images.size() > 0) {
                        uploadPicture(images.get(0).path);
                    }
                }
                break;
        }
    }
}
