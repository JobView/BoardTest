package com.wzf.boardgame.ui.adapter;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * Created by zhenfei.wang on 2016/7/12.
 * recycle的滑动监听，上拉加载更多操作
 */
public abstract class OnRecyclerScrollListener extends RecyclerView.OnScrollListener{
    private int lastVisibleItem = 0;
    private RecyclerView.Adapter mAdapter;
    private SwipeRefreshLayout refreshLayout;
    private LinearLayoutManager layoutManager;
    private StaggeredGridLayoutManager sLayoutManager;

    public OnRecyclerScrollListener(RecyclerView.Adapter mAdapter, SwipeRefreshLayout refreshLayout, LinearLayoutManager layoutManager) {
        this.mAdapter = mAdapter;
        this.refreshLayout = refreshLayout;
        this.layoutManager = layoutManager;
    }

    public OnRecyclerScrollListener(RecyclerView.Adapter mAdapter, SwipeRefreshLayout refreshLayout, StaggeredGridLayoutManager sLayoutManager) {
        this.mAdapter = mAdapter;
        this.refreshLayout = refreshLayout;
        this.sLayoutManager = sLayoutManager;
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
        if(sLayoutManager != null){
            sLayoutManager.invalidateSpanAssignments();
        }

    }
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if(layoutManager != null){
            lastVisibleItem = layoutManager.findLastVisibleItemPosition();
        }
        if(sLayoutManager != null){
            int[] lastPositions = sLayoutManager.findLastVisibleItemPositions(new int[sLayoutManager.getSpanCount()]);
            lastVisibleItem = getMaxPosition(lastPositions);
        }

    }

    /**
     * 获得最大的位置
     *
     * @param positions
     * @return
     */
    private int getMaxPosition(int[] positions) {
        int size = positions.length;
        int maxPosition = Integer.MIN_VALUE;
        for (int i = 0; i < size; i++) {
            maxPosition = Math.max(maxPosition, positions[i]);
        }
        return maxPosition;
    }

    public abstract void loadMore();
}
