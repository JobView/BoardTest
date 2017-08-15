package com.wzf.boardgame.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.wzf.boardgame.R;
import com.wzf.boardgame.constant.UrlService;
import com.wzf.boardgame.function.http.ResponseSubscriber;
import com.wzf.boardgame.function.http.dto.request.GetUserRelationReqDto;
import com.wzf.boardgame.function.http.dto.response.FollowLiseResDto;
import com.wzf.boardgame.ui.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @Description:
 * @author: wangzhenfei
 * @date: 2017-08-15 10:18
 */

public class AddFriendsActivity extends BaseActivity {
    @Bind(R.id.im_left)
    ImageView imLeft;
    @Bind(R.id.tv_center)
    TextView tvCenter;
    @Bind(R.id.et_search)
    EditText etSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        imLeft.setVisibility(View.VISIBLE);
        tvCenter.setText("添加好友");
    }

    @OnClick({R.id.im_left, R.id.btn_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.im_left:
                finish();
                break;
            case R.id.btn_search:
                searchUser();
                break;
        }
    }

    private void searchUser() {
        String key = etSearch.getText().toString().trim();
        if(TextUtils.isEmpty(key)){
            showToast("请输入搜索内容");
            return;
        }

        GetUserRelationReqDto reqDto = new GetUserRelationReqDto();
//        reqDto.setPageNum(page);
//        reqDto.setUid(uid);
        UrlService.SERVICE.getUserFollowerList(reqDto.toEncodeString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new ResponseSubscriber<FollowLiseResDto>(this, true) {
                    @Override
                    public void onSuccess(FollowLiseResDto responseDto) throws Exception {
                        super.onSuccess(responseDto);

                    }
                    @Override
                    public void onFailure(int code, String message) throws Exception {
                        super.onFailure(code, message);
                        showToast(message);
                    }
                });
    }
}
