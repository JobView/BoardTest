package com.wzf.boardgame.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wzf.boardgame.R;
import com.wzf.boardgame.constant.UrlService;
import com.wzf.boardgame.e.E_Relationship;
import com.wzf.boardgame.function.http.ResponseSubscriber;
import com.wzf.boardgame.function.http.dto.request.GetUserRelationReqDto;
import com.wzf.boardgame.function.http.dto.response.FollowLiseResDto;
import com.wzf.boardgame.function.imageloader.ImageLoader;
import com.wzf.boardgame.ui.adapter.OnRecyclerScrollListener;
import com.wzf.boardgame.ui.adapter.RcyCommonAdapter;
import com.wzf.boardgame.ui.adapter.RcyViewHolder;
import com.wzf.boardgame.ui.base.BaseActivity;
import com.wzf.boardgame.ui.model.UserInfo;
import com.wzf.boardgame.utils.StringUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @Description:
 * @author: wangzhenfei
 * @date: 2017-08-01 14:43
 */

public class FollowsActivity extends BaseActivity {
    @Bind(R.id.im_left)
    ImageView imLeft;
    @Bind(R.id.tv_center)
    TextView tvCenter;
    @Bind(R.id.im_right1)
    ImageView imRight1;
    @Bind(R.id.rv)
    RecyclerView rv;
    private RcyCommonAdapter adapter;
    private int page = 1;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_recycle);
        ButterKnife.bind(this);
        uid = getIntent().getStringExtra("uid");
        initView();
        getData();
    }


    private void initView() {
        imLeft.setVisibility(View.VISIBLE);
        boolean isMe = UserInfo.getInstance().getUid().equals(uid);
        tvCenter.setText(isMe ? "我的关注" : "TA的关注");
        tvCenter.setVisibility(View.VISIBLE);
        imRight1.setImageResource(R.mipmap.following_btn_addfriend_nor);
        imRight1.setVisibility(isMe ? View.VISIBLE : View.GONE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        adapter = getAdapter();
        rv.setAdapter(adapter);
        rv.addOnScrollListener(new OnRecyclerScrollListener(adapter, null, layoutManager) {
            @Override
            public void loadMore() {
                if(!adapter.isLoadFinish()){
                    getData();
                }
            }
        });
    }

    private RcyCommonAdapter getAdapter() {
        return new RcyCommonAdapter<FollowLiseResDto.FollowListBean>(this, new ArrayList<FollowLiseResDto.FollowListBean>(), true, rv){
            @Override
            public void convert(RcyViewHolder holder, FollowLiseResDto.FollowListBean o) {
                ImageView imAvatar = holder.getView(R.id.im_avatar);
                TextView tvNickname = holder.getView(R.id.tv_nickname);
                TextView tvDescription = holder.getView(R.id.tv_description);
                TextView tvStatus = holder.getView(R.id.tv_status);
                ImageLoader.getInstance().displayOnlineRoundImage(StringUtils.getResourcePath(o.getAvatarUrl()), imAvatar);
                tvNickname.setText(o.getNickname());
                tvDescription.setText(o.getPersonaSign());
                tvStatus.setText(E_Relationship.getRelationShip(o.getRelation()).getMsg());
            }

            @Override
            public int getLayoutId(int position) {
                return R.layout.item_follow_list;
            }

            @Override
            public void onItemClickListener(int position) {
                super.onItemClickListener(position);
                UserInfoActivity.startMethod(FollowsActivity.this, mDatas.get(position).getUserId());
            }
        };
    }

    private void getData() {
        GetUserRelationReqDto reqDto = new GetUserRelationReqDto();
        reqDto.setPageNum(page);
        reqDto.setUid(uid);
        UrlService.SERVICE.getUserFollowerList(reqDto.toEncodeString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new ResponseSubscriber<FollowLiseResDto>(this, true) {
                    @Override
                    public void onSuccess(FollowLiseResDto responseDto) throws Exception {
                        super.onSuccess(responseDto);
                        page ++;
                        adapter.loadMore(responseDto.getFollowList(), responseDto.getIsLastPage() == 1);
                    }
                    @Override
                    public void onFailure(int code, String message) throws Exception {
                        super.onFailure(code, message);
                        showToast(message);
                    }
                });
    }


    @OnClick({R.id.im_left, R.id.im_right1})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.im_left:
                finish();
                break;
            case R.id.im_right1:
                startActivity(new Intent(this, AddFriendsActivity.class));
                break;
        }
    }

    public static void startMethod(Context context, String uid){
        context.startActivity(new Intent(context, FollowsActivity.class).putExtra("uid", uid));
    }
}
