package com.wzf.boardgame.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
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
import com.wzf.boardgame.function.http.dto.response.MainBannerResDto;
import com.wzf.boardgame.function.imageloader.ImageLoader;
import com.wzf.boardgame.ui.activity.EditPostActivity;
import com.wzf.boardgame.ui.activity.PostDetailActivity;
import com.wzf.boardgame.ui.activity.SearchActivity;
import com.wzf.boardgame.ui.adapter.OnRecyclerScrollListener;
import com.wzf.boardgame.ui.adapter.RcyCommonAdapter;
import com.wzf.boardgame.ui.adapter.RcyViewHolder;
import com.wzf.boardgame.ui.base.BaseFragment;
import com.wzf.boardgame.ui.views.banner.AdImagePagerAdapter;
import com.wzf.boardgame.ui.views.banner.AutoScrollViewPager;
import com.wzf.boardgame.ui.views.banner.PageIndicatorView;
import com.wzf.boardgame.utils.StringUtils;
import com.wzf.boardgame.utils.ViewUtils;

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
    private MainBannerResDto banner;

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
        ViewUtils.setSwipeRefreshLayoutSchemeResources(srl);
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

    private void getBanner() {
        UrlService.SERVICE.getBanner("")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new ResponseSubscriber<MainBannerResDto>(bActivity, true) {
                    @Override
                    public void onSuccess(MainBannerResDto responseDto) throws Exception {
                        super.onSuccess(responseDto);
                        banner = responseDto;
                        if(banner != null && banner.getCarouselList().size() > 0){
                            if(!(adapter.getmDatas().size() > 0 && adapter.getmDatas().get(0).isAds())){ //第一条不是广告
                                adapter.getmDatas().add(0, new CommunityListResDto.PostListBean(true)); // 为广告留位置
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                    @Override
                    public void onFailure(int code, String message) throws Exception {
                        super.onFailure(code, message);
                        bActivity.showToast(message);
                    }
                });

    }
    private void getData(final boolean refresh) {
        if(refresh){
            page = 1;
        }
        CommunityListReqDto reqDto = new CommunityListReqDto();
        reqDto.setPageSize(30);
        reqDto.setPageNum(page);
        UrlService.SERVICE.communityList(reqDto.toEncodeString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new ResponseSubscriber<CommunityListResDto>(bActivity, refresh) {
                    @Override
                    public void onSuccess(CommunityListResDto responseDto) throws Exception {
                        super.onSuccess(responseDto);
                        page ++;
                        if(refresh){
                            if(banner != null && banner.getCarouselList().size() > 0){
                                responseDto.getPostList().add(0, new CommunityListResDto.PostListBean(true)); // 为广告留位置
                            }
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
                        srl.setRefreshing(false);
                    }
                });
    }

    private RcyCommonAdapter<CommunityListResDto.PostListBean> getAdapter() {
        return new RcyCommonAdapter<CommunityListResDto.PostListBean>(bActivity, new ArrayList<CommunityListResDto.PostListBean>(), true, rv) {
            @Override
            public void convert(RcyViewHolder holder, CommunityListResDto.PostListBean o) {
                if(o.isAds()){
                    fetchHeader(holder);
                }else {
                    fetchContent(holder, o);
                }
            }

            private void fetchHeader(RcyViewHolder holder) {
                AutoScrollViewPager page =  holder.getView(R.id.view_pager);
                final PageIndicatorView indicator =  holder.getView(R.id.pageview);
                page.setAdapter(new AdImagePagerAdapter(bActivity, banner.getCarouselList()));
                indicator.setTotalPage(banner.getCarouselList().size());
                indicator.settype(1);
                page.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                    public void onPageScrollStateChanged(int arg0) {}

                    public void onPageScrolled(int arg0, float arg1, int arg2) {}

                    public void onPageSelected(int arg0) {
                        indicator.setCurrentPage(arg0 % banner.getCarouselList().size());
                    }
                });
                page.setInterval(3000);
                page.startAutoScroll();
                int times = Integer.MAX_VALUE / banner.getCarouselList().size();
                page.setCurrentItem(times / 2 * banner.getCarouselList().size());
            }

            private void fetchContent(RcyViewHolder holder, CommunityListResDto.PostListBean o) {
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

                tvTitle.setText(o.getPostTitle());
                tvNickname.setText(o.getNickname());
                tvTime.setText(o.getPostTime());
                tvViewCount.setText(StringUtils.getCountByWan(o.getReadCount() + ""));
                tvCommentCount.setText(StringUtils.getCountByWan(o.getReplyCount() + ""));
                imImage.setVisibility(o.getIsImg() == 0 ? View.GONE : View.VISIBLE);
                imUp.setVisibility(o.getTopSort() == 0 ? View.GONE : View.VISIBLE);
                imPrime.setVisibility(o.getIsFine() == 0 ? View.GONE : View.VISIBLE);
                imV.setVisibility(o.getAuthLevel() == 0 ? View.GONE : View.VISIBLE);
                ImageLoader.getInstance().displayOnlineRoundImage(o.getAvatarUrl(), imAvatar);
            }

            @Override
            public int getLayoutId(int position) {
                CommunityListResDto.PostListBean bean = mDatas.get(position);
                if(bean.isAds()){
                    return R.layout.item_main_banner;
                }else {
                    return R.layout.item_community_list;
                }
            }

            @Override
            public void onItemClickListener(int position) {
                super.onItemClickListener(position);
                CommunityListResDto.PostListBean o = mDatas.get(position);
                if(o != null){
                    PostDetailActivity.startMethod(bActivity, o.getPostId());
                }
            }
        };
    }


    @OnClick({R.id.im_right1, R.id.rl_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.im_right1:
                startActivity(new Intent(bActivity, EditPostActivity.class));
                break;
            case R.id.rl_search:
                SearchActivity.startMethod(bActivity, SearchActivity.SEARCH_POST);
                break;
        }
    }

}
