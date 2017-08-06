package com.wzf.boardgame.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wzf.boardgame.R;
import com.wzf.boardgame.constant.UrlService;
import com.wzf.boardgame.function.http.ResponseSubscriber;
import com.wzf.boardgame.function.http.dto.request.CommunityListReqDto;
import com.wzf.boardgame.function.http.dto.response.CommunityListResDto;
import com.wzf.boardgame.function.imageloader.ImageLoader;
import com.wzf.boardgame.ui.activity.PostDetailActivity;
import com.wzf.boardgame.ui.adapter.OnRecyclerScrollListener;
import com.wzf.boardgame.ui.adapter.RcyCommonAdapter;
import com.wzf.boardgame.ui.adapter.RcyViewHolder;
import com.wzf.boardgame.ui.base.BaseFragment;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wzf on 2017/7/5.
 */

public class StorePostFragment extends BaseFragment{
    View mRootView;
    @Bind(R.id.rv)
    RecyclerView rv;
    @Bind(R.id.top)
    View top;
    private RcyCommonAdapter<CommunityListResDto.PostListBean> adapter;
    int page = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = bActivity.getLayoutInflater().inflate(R.layout.layout_recycle, null);
            ButterKnife.bind(this, mRootView);
            initView();
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        return mRootView;
    }

    private void initView() {
        top.setVisibility(View.GONE);
        LinearLayoutManager llManager = new LinearLayoutManager(bActivity);
        rv.setLayoutManager(llManager);
        adapter = getAdapter();
        rv.setAdapter(adapter);
        rv.addOnScrollListener(new OnRecyclerScrollListener(adapter, null, llManager) {
            @Override
            public void loadMore() {
                if (!adapter.isLoadFinish()) {
                    getData();//获取数据
                }
            }
        });
        getData();
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    private RcyCommonAdapter<CommunityListResDto.PostListBean> getAdapter() {
        return new RcyCommonAdapter<CommunityListResDto.PostListBean>(bActivity, new ArrayList<CommunityListResDto.PostListBean>(), true, rv) {
            @Override
            public void convert(RcyViewHolder holder, CommunityListResDto.PostListBean o) {
                TextView tvTitle =holder.getView(R.id.tv_title);
                TextView tvNickname =holder.getView(R.id.tv_nickname);
                TextView tvTime =holder.getView(R.id.tv_time);
                TextView tvViewCount =holder.getView(R.id.tv_view_count);
                TextView tvCommentCount =holder.getView(R.id.tv_comment_count);
                ImageView imImage =holder.getView(R.id.im_image);
                ImageView imPrime =holder.getView(R.id.im_prime);
                ImageView imUp =holder.getView(R.id.im_up);
                ImageView imV =holder.getView(R.id.im_v);
                ImageView imAvatar =holder.getView(R.id.im_avatar);
                ImageView imView =holder.getView(R.id.im_view);
                ImageView imComment =holder.getView(R.id.im_comment);
                imView.setVisibility(View.GONE);
                imComment.setVisibility(View.GONE);

                tvTitle.setText(o.getPostTitle());
                tvNickname.setText(o.getNickname());
                tvTime.setText(o.getPostTime());
                tvViewCount.setVisibility( View.GONE);
                tvCommentCount.setVisibility( View.GONE);
                imImage.setVisibility( View.GONE);
                imUp.setVisibility(View.GONE);
                imPrime.setVisibility(View.GONE);
                imV.setVisibility(View.GONE);
                ImageLoader.getInstance().displayOnlineRoundImage(o.getAvatarUrl(), imAvatar);
            }




            @Override
            public int getLayoutId(int position) {
                return R.layout.item_community_list;
            }

            @Override
            public void onItemClickListener(int position) {
                super.onItemClickListener(position);
                CommunityListResDto.PostListBean o = mDatas.get(position);
                PostDetailActivity.startMethod(bActivity, o.getPostId());
            }
        };
    }


    private void getData() {
        CommunityListReqDto reqDto = new CommunityListReqDto();
        reqDto.setPageSize(30);
        reqDto.setPageNum(page);
        UrlService.SERVICE.getPostCollectList(reqDto.toEncodeString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new ResponseSubscriber<CommunityListResDto>(bActivity, true) {
                    @Override
                    public void onSuccess(CommunityListResDto responseDto) throws Exception {
                        super.onSuccess(responseDto);
                        page ++;
                        adapter.loadMore(responseDto.getPostList(), responseDto.getIsLastPage() == 1);

                    }
                    @Override
                    public void onFailure(int code, String message) throws Exception {
                        super.onFailure(code, message);
                        bActivity.showToast(message);
                    }
                });
    }

    @OnClick(R.id.im_right1)
    public void onViewClicked() {

    }


}
