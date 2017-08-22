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
import com.wzf.boardgame.function.http.dto.request.UserSubjectReqDto;
import com.wzf.boardgame.function.http.dto.response.CommunityListResDto;
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

public class SubjectsActivity extends BaseActivity {
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
        tvCenter.setText(UserInfo.getInstance().getUid().endsWith(uid)? "我的主题" : "TA的主题");
        tvCenter.setVisibility(View.VISIBLE);
        imRight1.setImageResource(R.mipmap.fans_btn_follow_nor);
        imRight1.setVisibility(View.VISIBLE);
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
        return new RcyCommonAdapter<CommunityListResDto.PostListBean>(this, new ArrayList<CommunityListResDto.PostListBean>(), true, rv){
            @Override
            public void convert(RcyViewHolder holder, CommunityListResDto.PostListBean o) {
                TextView tvTime = holder.getView(R.id.tv_time);
                TextView tvContent = holder.getView(R.id.tv_content);
                tvTime.setText(o.getPostTime());
                tvContent.setText(o.getNickname() + "发布了帖子:" + o.getPostTitle());

            }

            @Override
            public int getLayoutId(int position) {
                return R.layout.item_subject_reply;
            }

            @Override
            public void onItemClickListener(int position) {
                super.onItemClickListener(position);
                PostDetailActivity.startMethod(SubjectsActivity.this, mDatas.get(position).getPostId());
            }
        };
    }

    private void getData() {
        UserSubjectReqDto reqDto = new UserSubjectReqDto();
        reqDto.setPageNum(page);
        reqDto.setUserId(uid);
        UrlService.SERVICE.getUserPostList(reqDto.toEncodeString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new ResponseSubscriber<CommunityListResDto>(this, true) {
                    @Override
                    public void onSuccess(CommunityListResDto responseDto) throws Exception {
                        super.onSuccess(responseDto);
                        page ++;
                        adapter.loadMore(responseDto.getPostList(), responseDto.getIsLastPage() == 1);
                    }
                    @Override
                    public void onFailure(int code, String message) throws Exception {
                        super.onFailure(code, message);
                        showToast(message);
                    }
                });
    }


    @OnClick({R.id.im_left})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.im_left:
                finish();
                break;
        }
    }

    public static void startMethod(Context context, String uid){
        context.startActivity(new Intent(context, SubjectsActivity.class).putExtra("uid", uid));
    }
}
