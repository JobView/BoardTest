package com.wzf.boardgame.ui.adapter;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by zhenfei.wang on 2016/7/12.
 * recycle的滑动监听，上拉加载更多操作
 */
public abstract class OnRecyclerScrollListener extends RecyclerView.OnScrollListener{
    private int lastVisibleItem = 0;
    private RecyclerView.Adapter mAdapter;
    private SwipeRefreshLayout refreshLayout;
    private LinearLayoutManager layoutManager;

    public OnRecyclerScrollListener(RecyclerView.Adapter mAdapter, SwipeRefreshLayout refreshLayout, LinearLayoutManager layoutManager) {
        this.mAdapter = mAdapter;
        this.refreshLayout = refreshLayout;
        this.layoutManager = layoutManager;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        //如果滚动到底部，就获取更多的数据
        if (mAdapter != null && newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == mAdapter.getItemCount()) {
            if (refreshLayout == null || !refreshLayout.isRefreshing()) {
                loadMore();
            }
        }
    }
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        lastVisibleItem = layoutManager.findLastVisibleItemPosition();
    }

    public abstract void loadMore();
}
