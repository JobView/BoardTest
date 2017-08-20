package com.wzf.boardgame.ui.fragments;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.text.util.Linkify;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wzf.boardgame.MyApplication;
import com.wzf.boardgame.R;
import com.wzf.boardgame.constant.UrlService;
import com.wzf.boardgame.function.autolink.AutoLinkUtils;
import com.wzf.boardgame.function.http.ResponseSubscriber;
import com.wzf.boardgame.function.http.dto.request.GetGameListReqDto;
import com.wzf.boardgame.function.http.dto.request.PostReqDto;
import com.wzf.boardgame.function.http.dto.request.ReplyCommentReqDto;
import com.wzf.boardgame.function.http.dto.response.CommentListResDto;
import com.wzf.boardgame.function.http.dto.response.GameVideoListResDto;
import com.wzf.boardgame.function.imageloader.ImageLoader;
import com.wzf.boardgame.ui.activity.CommentListActivity;
import com.wzf.boardgame.ui.activity.UserInfoActivity;
import com.wzf.boardgame.ui.activity.WebViewActivity;
import com.wzf.boardgame.ui.adapter.OnRecyclerScrollListener;
import com.wzf.boardgame.ui.adapter.RcyCommonAdapter;
import com.wzf.boardgame.ui.adapter.RcyViewHolder;
import com.wzf.boardgame.ui.base.BaseFragment;
import com.wzf.boardgame.ui.dialog.CommentDialog;
import com.wzf.boardgame.ui.model.UserInfo;
import com.wzf.boardgame.utils.ScreenUtils;
import com.wzf.boardgame.utils.StringUtils;
import com.wzf.boardgame.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wzf on 2017/8/19.
 */

public class GameVideoListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    View mRootView;
    @Bind(R.id.top)
    View top;
    @Bind(R.id.rv)
    RecyclerView rv;
    @Bind(R.id.srl)
    SwipeRefreshLayout srl;
    private RcyCommonAdapter<GameVideoListResDto.VideoListBean> adapter;
    private String boardId;
    private int page = 1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = bActivity.getLayoutInflater().inflate(R.layout.layout_srl_rv, null);
            ButterKnife.bind(this, mRootView);
            init();
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        return mRootView;
    }

    private void init() {
        boardId = getArguments() == null ? "" : getArguments().getString("boardId");
        top.setVisibility(View.GONE);
        srl.setOnRefreshListener(this);
        ViewUtils.setSwipeRefreshLayoutSchemeResources(srl);
        LinearLayoutManager layoutManager = new LinearLayoutManager(bActivity);
        rv.setLayoutManager(layoutManager);
        //设置item之间的间隔
        rv.addItemDecoration(new RecyclerView.ItemDecoration() {
            int lr = ScreenUtils.dip2px(MyApplication.getAppInstance(), 5);
            int tb = ScreenUtils.dip2px(MyApplication.getAppInstance(), 10);

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                if (parent.getChildAdapterPosition(view) == 0) {
                    outRect.top = ScreenUtils.dip2px(MyApplication.getAppInstance(), 13);
                } else {
                    outRect.top = tb;
                }
                outRect.left = lr;
                outRect.right = lr;
            }

        });
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
         GetGameListReqDto reqDto = new GetGameListReqDto();
        if(fresh){
            page = 1;
        }
        reqDto.setBoardId(boardId);
        reqDto.setPageNum(page);
        UrlService.SERVICE.getVideoList(reqDto.toEncodeString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new ResponseSubscriber<GameVideoListResDto>(bActivity, false) {
                    @Override
                    public void onSuccess(GameVideoListResDto responseDto) throws Exception {
                        super.onSuccess(responseDto);
                        page ++;
                        if(fresh){
                            adapter.refresh(responseDto.getVideoList(), responseDto.getIsLastPage() == 1);
                            srl.setRefreshing(false);
                        }else {
                            adapter.loadMore(responseDto.getVideoList(), responseDto.getIsLastPage() == 1);
                        }

                    }

                    @Override
                    public void onFailure(int code, String message) throws Exception {
                        super.onFailure(code, message);
                       bActivity. showToast(message);
                        srl.setRefreshing(false);
                    }
                });
    }

    private RcyCommonAdapter<GameVideoListResDto.VideoListBean> getAdapter() {
        return new RcyCommonAdapter<GameVideoListResDto.VideoListBean>(bActivity, new ArrayList<GameVideoListResDto.VideoListBean>(), true, rv) {
            @Override
            public void convert(RcyViewHolder holder, final GameVideoListResDto.VideoListBean replyListBean) {
                ImageView imAvatar = holder.getView(R.id.im_avatar);
                TextView tvName = holder.getView(R.id.tv_nickname);
                TextView tvTitle = holder.getView(R.id.tv_title);
                ImageView imCover = holder.getView(R.id.im_cover);

                ImageLoader.getInstance().displayOnlineRoundImage(replyListBean.getReleaseAvatar(), imAvatar);
                tvName.setText(replyListBean.getReleaseNickname());
                tvTitle.setText(replyListBean.getVideoTitle());
                ImageLoader.getInstance().displayOnlineImage(replyListBean.getVideoCover(), imCover, 0, 0);
            }

            @Override
            public int getLayoutId(int position) {
                return R.layout.item_game_video;
            }

            @Override
            public void onItemClickListener(int position) {
                super.onItemClickListener(position);
                WebViewActivity.startMethod(bActivity, mDatas.get(position).getVideoUrl());
            }
        };
    }

    public static BaseFragment getInstance(String boardId){
        GameVideoListFragment fragment = new GameVideoListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("boardId", boardId);
        fragment.setArguments(bundle);
        return fragment;
    }
}
