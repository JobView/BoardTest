package com.wzf.boardgame.utils;

import android.app.Activity;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;

import com.lzy.imagepicker.bean.ImageItem;
import com.wzf.boardgame.R;
import com.wzf.boardgame.ui.activity.MImagePreviewDelActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * @Description:
 * @author: wangzhenfei
 * @date: 2017-06-20 16:23
 */

public class ViewUtils {
    public static void setSwipeRefreshLayoutSchemeResources(final SwipeRefreshLayout refreshLayout){
        if(refreshLayout != null){
            refreshLayout.setColorSchemeResources(R.color.random5, R.color.random3, R.color.random4, R.color.random1);
//            refreshLayout.post(new Runnable() {
//                @Override
//                public void run() {
//                    refreshLayout.setRefreshing(true);
//                }
//            });
        }
    }

    public static void previewPicture(Activity activity, int position ,List<String> paths){
        ArrayList<ImageItem> imageItemList = new ArrayList<>();
        for(String path : paths){
            ImageItem item = new ImageItem();
            item.path = StringUtils.getResourcePath(path);
            imageItemList.add(item);
        }

        MImagePreviewDelActivity.startMethod(activity,
                imageItemList, position, false, 0);

    }
}
