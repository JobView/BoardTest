package com.wzf.boardgame.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.wzf.boardgame.function.http.dto.response.LoginResDto;
import com.wzf.boardgame.ui.activity.LoginActivity;
import com.wzf.boardgame.ui.activity.MenuActivity;
import com.wzf.boardgame.ui.adapter.OnRecyclerScrollListener;
import com.wzf.boardgame.ui.adapter.RcyCommonAdapter;
import com.wzf.boardgame.ui.adapter.RcyViewHolder;
import com.wzf.boardgame.ui.base.BaseFragment;
import com.wzf.boardgame.ui.model.UserInfo;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wzf on 2017/7/5.
 */

public class CommunityFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    View mRootView;
    @Bind(R.id.tv_center)
    TextView tvCenter;
    @Bind(R.id.im_right1)
    ImageView imRight1;
    @Bind(R.id.srl)
    SwipeRefreshLayout srl;
    @Bind(R.id.rv)
    RecyclerView rv;

    private RcyCommonAdapter<CommunityListResDto.PostListBean> adapter;
    int page = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = bActivity.getLayoutInflater().inflate(R.layout.fragment_community, null);
            ButterKnife.bind(this, mRootView);
            initData();
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        return mRootView;
    }

    private void initData() {
        initView();
        getBanner();
        onRefresh();
    }

    @Override
    public void onRefresh() {
        getData(true);
    }

    private void initView() {
        tvCenter.setText("首页");
        tvCenter.setVisibility(View.VISIBLE);
        imRight1.setImageResource(R.mipmap.home_btn_write_nor);
        imRight1.setVisibility(View.VISIBLE);
        srl.setOnRefreshListener(this);
        LinearLayoutManager llManager = new LinearLayoutManager(bActivity);
        rv.setLayoutManager(llManager);
        adapter = getAdapter();
        rv.setAdapter(adapter);
        rv.addOnScrollListener(new OnRecyclerScrollListener(adapter, srl, llManager) {
            @Override
            public void loadMore() {
                if (!adapter.isLoadFinish()) {
                    getData(false);//获取数据
                }
            }
        });

    }

    private void getData(final boolean refresh) {
        if(refresh){
            page = 1;
        }
        CommunityListReqDto reqDto = new CommunityListReqDto();
        reqDto.setPageSize(page);
        UrlService.SERVICE.communityList(reqDto.toEncodeString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new ResponseSubscriber<CommunityListResDto>(bActivity, refresh) {
                    @Override
                    public void onSuccess(CommunityListResDto responseDto) throws Exception {
                        super.onSuccess(responseDto);
                        if(refresh){
                            adapter.refresh(responseDto.getPostList());
                            srl.setRefreshing(false);
                        }else {
                            adapter.loadMore(responseDto.getPostList());
                        }
                        if(responseDto.getIsLastPage() == 1){
                            adapter.loadMoreFinish();
                        }

                    }
                    @Override
                    public void onFailure(int code, String message) throws Exception {
                        super.onFailure(code, message);
                        bActivity.showToast(message);
                    }
                });
    }

    private RcyCommonAdapter<CommunityListResDto.PostListBean> getAdapter() {
        return new RcyCommonAdapter<CommunityListResDto.PostListBean>(bActivity, new ArrayList<CommunityListResDto.PostListBean>(), true, rv) {
            @Override
            public void convert(RcyViewHolder holder, CommunityListResDto.PostListBean o) {

            }

            @Override
            public int getLayoutId(int position) {
                return R.layout.item_community_list;
            }
        };
    }

    private void getBanner() {

    }

    @OnClick(R.id.im_right1)
    public void onViewClicked() {

    }


}
