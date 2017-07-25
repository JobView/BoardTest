package com.wzf.boardgame.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.wzf.boardgame.R;
import com.wzf.boardgame.constant.UrlService;
import com.wzf.boardgame.function.http.ResponseSubscriber;
import com.wzf.boardgame.function.http.dto.request.PostReqDto;
import com.wzf.boardgame.function.http.dto.response.PostDetailResDto;
import com.wzf.boardgame.ui.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wzf on 2017/7/23.
 */

public class EditCommentActivity extends BaseActivity {
    @Bind(R.id.im_left)
    ImageView imLeft;
    @Bind(R.id.tv_center)
    TextView tvCenter;
    @Bind(R.id.im_right1)
    ImageView imRight1;
    @Bind(R.id.et_comment)
    EditText etComment;

    private String postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_comment);
        ButterKnife.bind(this);
        initView();
        initData();
    }


    private void initView() {
        imLeft.setVisibility(View.VISIBLE);
        tvCenter.setText("发表帖子");
        tvCenter.setVisibility(View.VISIBLE);
        imRight1.setImageResource(R.mipmap.tabbar_btn_game_nor);
        imRight1.setVisibility(View.VISIBLE);
    }


    private void initData() {
        postId = getIntent().getStringExtra("postId");
    }

    @OnClick({R.id.im_left, R.id.im_right1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_left:
                finish();
                break;
            case R.id.im_right1:
                commitComment();
                break;
        }
    }

    private void commitComment() {
        String content = etComment.getText().toString().trim();
        if(TextUtils.isEmpty(content)){
            showToast("请输入评论内容");
            return;
        }
        PostReqDto reqDto = new PostReqDto(postId);
        reqDto.setReplyContent(content);
        UrlService.SERVICE.commentPost(reqDto.toEncodeString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new ResponseSubscriber<Object>(this, true) {
                    @Override
                    public void onSuccess(Object responseDto) throws Exception {
                        super.onSuccess(responseDto);
                        showToast("评论成功");
                        finish();
                    }

                    @Override
                    public void onFailure(int code, String message) throws Exception {
                        super.onFailure(code, message);
                        showToast(message);
                    }
                });
    }

    public static void startMethod(Context context, String postId) {
        context.startActivity(new Intent(context, EditCommentActivity.class).
                putExtra("postId", postId));
    }
}
