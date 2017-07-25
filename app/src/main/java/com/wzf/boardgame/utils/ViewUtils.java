package com.wzf.boardgame.utils;

import android.support.v4.widget.SwipeRefreshLayout;

import com.wzf.boardgame.R;


/**
 * @Description:
 * @author: wangzhenfei
 * @date: 2017-06-20 16:23
 */

public class ViewUtils {
    public static void setSwipeRefreshLayoutSchemeResources(SwipeRefreshLayout refreshLayout){
        if(refreshLayout != null){
            refreshLayout.setColorSchemeResources(R.color.random5, R.color.random3, R.color.random4, R.color.random1);
        }
    }
}
