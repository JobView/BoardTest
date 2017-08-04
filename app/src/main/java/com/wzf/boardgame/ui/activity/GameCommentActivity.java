package com.wzf.boardgame.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.wzf.boardgame.R;
import com.wzf.boardgame.constant.UrlService;
import com.wzf.boardgame.function.http.ResponseSubscriber;
import com.wzf.boardgame.function.http.dto.request.CommentGameReqDto;
import com.wzf.boardgame.function.http.dto.request.GameReqDto;
import com.wzf.boardgame.function.http.dto.response.GameCommentListResDto;
import com.wzf.boardgame.function.http.dto.response.GameDetailResDto;
import com.wzf.boardgame.ui.base.BaseActivity;
import com.wzf.boardgame.ui.views.NestRadioGroup;
import com.wzf.boardgame.utils.ScreenUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @Description:
 * @author: wangzhenfei
 * @date: 2017-08-04 09:06
 */

public class GameCommentActivity extends BaseActivity {
    @Bind(R.id.im_left)
    ImageView imLeft;
    @Bind(R.id.tv_center)
    TextView tvCenter;
    @Bind(R.id.im_right1)
    ImageView imRight1;
    @Bind(R.id.radio_recommend)
    RadioButton radioRecommend;
    @Bind(R.id.radio_unrecommend)
    RadioButton radioUnrecommend;
    @Bind(R.id.main_radio)
    NestRadioGroup mainRadio;
    @Bind(R.id.et_new_content)
    EditText etNewContent;

    private String gameId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_comment);
        ButterKnife.bind(this);
        gameId = getIntent().getStringExtra("gameId");
        initView();
    }

    private void initView() {
        imLeft.setVisibility(View.VISIBLE);
        tvCenter.setText("桌游评论");
        tvCenter.setVisibility(View.VISIBLE);
        imRight1.setImageResource(R.mipmap.tabbar_btn_game_nor);
        imRight1.setVisibility(View.VISIBLE);
//        int height = (int) ScreenUtils.dpToPix(50.0f, this);
//        int offset = (int) ScreenUtils.dpToPix(30.0f, this);
//
//        Drawable b = getResources().getDrawable(R.drawable.selector_im_recommend);
//        b.setBounds(offset, 0, height, height);
//        radioRecommend.setCompoundDrawables(b, null, null, null);
    }


    @OnClick({R.id.im_left, R.id.im_right1, R.id.im_right2})
    public void onClick(View view) {
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
        String comment = etNewContent.getText().toString().trim();
        if(TextUtils.isEmpty(comment)){
            showToast("请输入评论内容");
            return;
        }
        int isRecommend = mainRadio.getCheckedRadioButtonId() == R.id.radio_unrecommend ? 0 : 1;
        CommentGameReqDto reqDto = new CommentGameReqDto();
        reqDto.setBoardId(gameId);
        reqDto.setIsRecommend(isRecommend);
        reqDto.setCommentContent(comment);
        UrlService.SERVICE.commentBoardGame(reqDto.toEncodeString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new ResponseSubscriber<Object>() {
                    @Override
                    public void onSuccess(Object dto) throws Exception {
                        super.onSuccess(dto);
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

    public static void startMethod(Context context, String gameId) {
        context.startActivity(new Intent(context, GameCommentActivity.class).
                putExtra("gameId", gameId));
    }
}
