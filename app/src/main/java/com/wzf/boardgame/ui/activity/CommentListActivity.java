package com.wzf.boardgame.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wzf.boardgame.R;
import com.wzf.boardgame.constant.UrlService;
import com.wzf.boardgame.function.http.ResponseSubscriber;
import com.wzf.boardgame.function.http.dto.request.PostReqDto;
import com.wzf.boardgame.function.http.dto.response.CommentListResDto;
import com.wzf.boardgame.ui.adapter.OnRecyclerScrollListener;
import com.wzf.boardgame.ui.adapter.RcyCommonAdapter;
import com.wzf.boardgame.ui.adapter.RcyViewHolder;
import com.wzf.boardgame.ui.base.BaseActivity;
import com.wzf.boardgame.utils.ViewUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wzf on 2017/7/23.
 */

public class CommentListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{
    @Bind(R.id.im_left)
    ImageView imLeft;
    @Bind(R.id.tv_center)
    TextView tvCenter;
    @Bind(R.id.im_right1)
    ImageView imRight1;
    @Bind(R.id.rv)
    RecyclerView rv;
    @Bind(R.id.srl)
    SwipeRefreshLayout srl;
    private RcyCommonAdapter<CommentListResDto.ReplyListBean> adapter;
    private String postId;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_list);
        ButterKnife.bind(this);
        initView();
        initData();
    }


    private void initView() {
        imLeft.setVisibility(View.VISIBLE);
        tvCenter.setText("评论列表");
        tvCenter.setVisibility(View.VISIBLE);
        imRight1.setImageResource(R.mipmap.tabbar_btn_game_nor);
//        imRight1.setVisibility(View.VISIBLE);
    }


    private void initData() {
        postId = getIntent().getStringExtra("postId");
        srl.setOnRefreshListener(this);
        ViewUtils.setSwipeRefreshLayoutSchemeResources(srl);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        adapter = getAdapter();
        rv.setAdapter(adapter);
        rv.addOnScrollListener(new OnRecyclerScrollListener(adapter, srl, layoutManager) {
            @Override
            public void loadMore() {
                if (!adapter.isLoadFinish()) {
                    loadData(false);
                }
            }
        });
        onRefresh();
    }

    @Override
    public void onRefresh() {
        loadData(true);
    }

    private void loadData(final boolean fresh) {
        final PostReqDto reqDto = new PostReqDto(postId);
        if(fresh){
            page = 1;
        }
        reqDto.setPageNum(page);
        UrlService.SERVICE.getPostReplyList(reqDto.toEncodeString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new ResponseSubscriber<CommentListResDto>(this, true) {
                    @Override
                    public void onSuccess(CommentListResDto responseDto) throws Exception {
                        super.onSuccess(responseDto);
                        page ++;
                        if(fresh){
                            adapter.refresh(responseDto.getReplyList(), responseDto.getIsLastPage() == 1);
                            srl.setRefreshing(false);
                        }else {
                            adapter.loadMore(responseDto.getReplyList(), responseDto.getIsLastPage() == 1);
                        }

                    }

                    @Override
                    public void onFailure(int code, String message) throws Exception {
                        super.onFailure(code, message);
                        showToast(message);
                    }
                });
    }

    private RcyCommonAdapter<CommentListResDto.ReplyListBean> getAdapter() {
        return new RcyCommonAdapter<CommentListResDto.ReplyListBean>(this, new ArrayList<CommentListResDto.ReplyListBean>(), true, rv) {
            @Override
            public void convert(RcyViewHolder holder, CommentListResDto.ReplyListBean replyListBean) {

            }

            @Override
            public int getLayoutId(int position) {
                return R.layout.item_comment_list;
            }
        };
    }

    @OnClick({R.id.im_left, R.id.im_right1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_left:
                finish();
                break;
            case R.id.im_right1:
                break;
        }
    }

    public static void startMethod(Context context, String postId) {
        context.startActivity(new Intent(context, CommentListActivity.class).
                putExtra("postId", postId));
    }


}
