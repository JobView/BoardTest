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
import com.wzf.boardgame.function.http.dto.request.SearchUserReqDto;
import com.wzf.boardgame.function.http.dto.response.FollowLiseResDto;
import com.wzf.boardgame.function.http.dto.response.SearchUserResDto;
import com.wzf.boardgame.ui.base.BaseActivity;
import com.wzf.boardgame.utils.SoftInputUtil;

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
        tvCenter.setVisibility(View.VISIBLE);
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

        SearchUserReqDto reqDto = new SearchUserReqDto();
//        reqDto.setPageNum(page);
//        reqDto.setUid(uid);
        reqDto.setSearchContent(key);
        UrlService.SERVICE.findUser(reqDto.toEncodeString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new ResponseSubscriber<SearchUserResDto>(this, true) {
                    @Override
                    public void onSuccess(SearchUserResDto responseDto) throws Exception {
                        super.onSuccess(responseDto);
                        if(TextUtils.isEmpty(responseDto.getTargetUserId())){
                            showToast("没有找到匹配的用户");
                        }else {
                            UserInfoActivity.startMethod(AddFriendsActivity.this, responseDto.getTargetUserId());
                        }
                    }
                    @Override
                    public void onFailure(int code, String message) throws Exception {
                        super.onFailure(code, message);
                        showToast(message);
                    }
                });
    }

    @Override
    protected void onDestroy() {
        SoftInputUtil.closeSoftInput(this);
        super.onDestroy();
    }
}
