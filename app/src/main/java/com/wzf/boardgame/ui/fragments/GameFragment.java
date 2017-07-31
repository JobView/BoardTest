package com.wzf.boardgame.ui.fragments;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wzf.boardgame.MyApplication;
import com.wzf.boardgame.R;
import com.wzf.boardgame.constant.UrlService;
import com.wzf.boardgame.function.eventbus.EventBus;
import com.wzf.boardgame.function.eventbus.EventBusForGameList;
import com.wzf.boardgame.function.eventbus.Subscribe;
import com.wzf.boardgame.function.eventbus.ThreadMode;
import com.wzf.boardgame.function.http.ResponseSubscriber;
import com.wzf.boardgame.function.http.dto.request.CommunityListReqDto;
import com.wzf.boardgame.function.http.dto.response.BaseResponse;
import com.wzf.boardgame.function.http.dto.response.GameListResDto;
import com.wzf.boardgame.function.imageloader.ImageLoader;
import com.wzf.boardgame.ui.adapter.OnRecyclerScrollListener;
import com.wzf.boardgame.ui.adapter.RcyCommonAdapter;
import com.wzf.boardgame.ui.adapter.RcyViewHolder;
import com.wzf.boardgame.ui.base.BaseFragment;
import com.wzf.boardgame.ui.views.ScaleImageView;
import com.wzf.boardgame.utils.ScreenUtils;
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

public class GameFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    View mRootView;
    @Bind(R.id.tv_center)
    TextView tvCenter;
    @Bind(R.id.im_right1)
    ImageView imRight1;
    @Bind(R.id.srl)
    SwipeRefreshLayout srl;
    @Bind(R.id.rv)
    RecyclerView rv;
    private RcyCommonAdapter<GameListResDto.WaterfallListBean> adapter;
    int page = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = bActivity.getLayoutInflater().inflate(R.layout.fragment_game, null);
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
        onRefresh();
    }

    private void initView() {
        tvCenter.setText("桌游百科");
        tvCenter.setVisibility(View.VISIBLE);
        imRight1.setImageResource(R.mipmap.game_btn_mali_nor);
        imRight1.setVisibility(View.VISIBLE);

        srl.setOnRefreshListener(this);
        //实现首次自动显示加载提示
        srl.post(new Runnable() {
            @Override
            public void run() {
                srl.setRefreshing(true);
            }
        });
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
        if(refresh){
            page = 1;
        }
        CommunityListReqDto reqDto = new CommunityListReqDto();
        reqDto.setPageSize(30);
        reqDto.setPageNum(page);
        UrlService.SERVICE.getWaterfallList(reqDto.toEncodeString())
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .map(new Func1<BaseResponse<GameListResDto>,BaseResponse<GameListResDto>>() {
                    @Override
                    public BaseResponse<GameListResDto> call(BaseResponse<GameListResDto> game) {
                        //加载异步线程获取图片的宽高
                        List<GameListResDto.WaterfallListBean> gameLists = game.getResponse().getWaterfallList();
                        for (GameListResDto.WaterfallListBean data : gameLists) {
                            Bitmap bitmap = ImageLoader.getInstance().load(bActivity, data.getBoardImgUrl());
                            if (bitmap != null) {
                                data.setWidth(bitmap.getWidth());
                                data.setHeight(bitmap.getHeight());
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
            @Override
            public void convert(RcyViewHolder holder, final GameListResDto.WaterfallListBean o) {
                ScaleImageView imageView = holder.getView(R.id.im);
                imageView.setInitSize(o.getWidth(), o.getHeight());
                ImageLoader.getInstance().displayOnlineImage(o.getBoardImgUrl(), imageView, 0, 0);
            }

            @Override
            public int getLayoutId(int position) {
                return R.layout.item_game_list;
            }
        };
    }

    @OnClick(R.id.im_right1)
    public void onViewClicked() {

    }


}
