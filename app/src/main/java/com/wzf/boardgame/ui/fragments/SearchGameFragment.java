package com.wzf.boardgame.ui.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.wzf.boardgame.MyApplication;
import com.wzf.boardgame.R;
import com.wzf.boardgame.constant.UrlService;
import com.wzf.boardgame.function.http.ResponseSubscriber;
import com.wzf.boardgame.function.http.dto.request.CommunityListReqDto;
import com.wzf.boardgame.function.http.dto.response.BaseResponse;
import com.wzf.boardgame.function.http.dto.response.GameListResDto;
import com.wzf.boardgame.function.imageloader.ImageLoader;
import com.wzf.boardgame.ui.activity.GameDetailActivity;
import com.wzf.boardgame.ui.activity.GameFeedBackActivity;
import com.wzf.boardgame.ui.adapter.OnRecyclerScrollListener;
import com.wzf.boardgame.ui.adapter.RcyCommonAdapter;
import com.wzf.boardgame.ui.adapter.RcyViewHolder;
import com.wzf.boardgame.ui.base.BaseFragment;
import com.wzf.boardgame.ui.views.ScaleImageView;
import com.wzf.boardgame.utils.ScreenUtils;
import com.wzf.boardgame.utils.SoftInputUtil;
import com.wzf.boardgame.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by wzf on 2017/7/5.
 */

public class SearchGameFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    View mRootView;
    @Bind(R.id.srl)
    SwipeRefreshLayout srl;
    @Bind(R.id.rv)
    RecyclerView rv;
    @Bind(R.id.et_search)
    EditText etSearch;
    @Bind(R.id.tv_empty)
    TextView tvEmpty;
    private RcyCommonAdapter<GameListResDto.WaterfallListBean> adapter;
    int page = 1;
    private String key = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = bActivity.getLayoutInflater().inflate(R.layout.fragment_search_game, null);
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
        //实现首次自动显示加载提示
//        srl.post(new Runnable() {
//            @Override
//            public void run() {
//                srl.setRefreshing(true);
//            }
//        });
        ViewUtils.setSwipeRefreshLayoutSchemeResources(srl);
        StaggeredGridLayoutManager sManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        sManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        rv.setLayoutManager(sManager);
        adapter = getAdapter();
        //设置item之间的间隔
        rv.addItemDecoration(new RecyclerView.ItemDecoration(){
            int lr  = ScreenUtils.dip2px(MyApplication.getAppInstance(), 5);
            int tb = ScreenUtils.dip2px(MyApplication.getAppInstance(), 10);
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                if(parent.getChildAdapterPosition(view)==0){
                    outRect.top = ScreenUtils.dip2px(MyApplication.getAppInstance(), 13);
                }else {
                    outRect.top = tb;
                }
                outRect.left=lr;
                outRect.right=lr;
            }

        });
        rv.setAdapter(adapter);
        rv.addOnScrollListener(new OnRecyclerScrollListener(adapter, srl, sManager) {
            @Override
            public void loadMore() {
                if (!adapter.isLoadFinish()) {
                    getData(false);//获取数据
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        getData(true);
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
        UrlService.SERVICE.getWaterfallList(reqDto.toEncodeString())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .map(new Func1<BaseResponse<GameListResDto>,BaseResponse<GameListResDto>>() {
                    @Override
                    public BaseResponse<GameListResDto> call(BaseResponse<GameListResDto> game) {
                        //加载异步线程获取图片的宽高
                        List<GameListResDto.WaterfallListBean> gameLists = game.getResponse().getWaterfallList();
                        for (GameListResDto.WaterfallListBean data : gameLists) {
                            if(data.getImgWidth() == 0 || data.getImgHeight() == 0){
                                Bitmap bitmap = ImageLoader.getInstance().load(bActivity, data.getBoardImgUrl());
                                if (bitmap != null) {
                                    data.setImgWidth(bitmap.getWidth());
                                    data.setImgHeight(bitmap.getHeight());
                                }
                            }

                        }
                        return game;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ResponseSubscriber<GameListResDto>(bActivity, false) {
                    @Override
                    public void onSuccess(GameListResDto responseDto) throws Exception {
                        super.onSuccess(responseDto);
                        page ++;
                        if(refresh){
                            if(responseDto.getWaterfallList().size() == 0){
                                tvEmpty.setVisibility(View.VISIBLE);
                                srl.setVisibility(View.GONE);
                            }else {
                                tvEmpty.setVisibility(View.GONE);
                                srl.setVisibility(View.VISIBLE);
                            }
                            adapter.refresh(responseDto.getWaterfallList());
                            srl.setRefreshing(false);
                        }else {
                            adapter.loadMore(responseDto.getWaterfallList());
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

    private RcyCommonAdapter<GameListResDto.WaterfallListBean> getAdapter() {
        return new RcyCommonAdapter<GameListResDto.WaterfallListBean>(bActivity, new ArrayList<GameListResDto.WaterfallListBean>(), true, rv) {
            int w = ScreenUtils.getScreenWidth(MyApplication.getAppInstance()) / 3;
            @Override
            public void convert(RcyViewHolder holder, final GameListResDto.WaterfallListBean o) {
                ScaleImageView imageView = holder.getView(R.id.im);
                float scale = (float) o.getImgHeight() / (float) o.getImgWidth();
                int h  = (int) (scale * w);
                ImageLoader.getInstance().displayTargeSizeImage(o.getBoardImgUrl(), imageView, w, h);
            }

            @Override
            public int getLayoutId(int position) {
                return R.layout.item_game_list;
            }

            @Override
            public void onItemClickListener(int position) {
                super.onItemClickListener(position);
                GameDetailActivity.startMethod(bActivity, mDatas.get(position).getBoardId());
            }
        };
    }

}
