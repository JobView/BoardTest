package com.wzf.boardgame.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
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
import com.wzf.boardgame.utils.SoftInputUtil;
import com.wzf.boardgame.utils.StringUtils;
import com.wzf.boardgame.utils.ViewUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wzf on 2017/7/5.
 */

public class SearchPostFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    View mRootView;
    @Bind(R.id.srl)
    SwipeRefreshLayout srl;
    @Bind(R.id.rv)
    RecyclerView rv;
    @Bind(R.id.et_search)
    EditText etSearch;
    @Bind(R.id.tv_empty)
    TextView tvEmpty;

    private RcyCommonAdapter<CommunityListResDto.PostListBean> adapter;
    int page = 1;
    private String key = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = bActivity.getLayoutInflater().inflate(R.layout.fragment_search_community, null);
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
    }

    @Override
    public void onRefresh() {
        getData(true);
    }

    private void initView() {
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    page = 1;
                    key = etSearch.getText().toString().trim();
                    SoftInputUtil.closeSoftInput(bActivity);
                    getData(true);
                    return true;
                }
                return false;
            }

        });
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
    private void getData(final boolean refresh) {
        if(TextUtils.isEmpty(key)){
           return;
        }
        if(refresh){
            page = 1;
        }
        CommunityListReqDto reqDto = new CommunityListReqDto();
        reqDto.setPageSize(30);
        reqDto.setPageNum(page);
        reqDto.setSearch(key);
        UrlService.SERVICE.communityList(reqDto.toEncodeString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new ResponseSubscriber<CommunityListResDto>(bActivity, refresh) {
                    @Override
                    public void onSuccess(CommunityListResDto responseDto) throws Exception {
                        super.onSuccess(responseDto);
                        page ++;
                        if(refresh){
                            if(responseDto.getPostList().size() == 0){
                                tvEmpty.setVisibility(View.VISIBLE);
                                srl.setVisibility(View.GONE);
                            }else {
                                tvEmpty.setVisibility(View.GONE);
                                srl.setVisibility(View.VISIBLE);
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
                fetchContent(holder, o);
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
                imImage.setVisibility(o.getIsImg() == 0 ? View.INVISIBLE : View.VISIBLE);
                imUp.setVisibility(o.getTopSort() == 0 ? View.INVISIBLE : View.VISIBLE);
                imPrime.setVisibility(o.getIsFine() == 0 ? View.INVISIBLE : View.VISIBLE);
                imV.setVisibility(o.getAuthLevel() == 0 ? View.INVISIBLE : View.VISIBLE);
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

}
