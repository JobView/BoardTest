package com.wzf.boardgame.function.server;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Parcelable;

import com.wzf.boardgame.function.eventbus.EventBus;
import com.wzf.boardgame.function.eventbus.EventBusForGameList;
import com.wzf.boardgame.function.http.dto.response.GameListResDto;
import com.wzf.boardgame.function.imageloader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Othershe
 * Time: 2016/8/18 11:48
 */
public class DataService extends IntentService {
    public DataService() {
        super("");
    }

    public static void startService(Context context, List<GameListResDto.WaterfallListBean> datas, boolean isFresh) {
        Intent intent = new Intent(context, DataService.class);
        intent.putParcelableArrayListExtra("data", (ArrayList<? extends Parcelable>) datas);
        intent.putExtra("isFresh", isFresh);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent == null) {
            return;
        }

        List<GameListResDto.WaterfallListBean> datas = intent.getParcelableArrayListExtra("data");
        boolean isFresh = intent.getBooleanExtra("isFresh", false);
        handleGirlItemData(datas, isFresh);
    }

    private void handleGirlItemData(List<GameListResDto.WaterfallListBean> datas, boolean isFresh) {
        EventBusForGameList gameList = new EventBusForGameList();
        gameList.setFresh(isFresh);
        if(datas == null){
            datas = new ArrayList<GameListResDto.WaterfallListBean>();
        }else {
            for (GameListResDto.WaterfallListBean data : datas) {
                Bitmap bitmap = ImageLoader.getInstance().load(this, data.getBoardImgUrl());
                if (bitmap != null) {
                    data.setWidth(bitmap.getWidth());
                    data.setHeight(bitmap.getHeight());
                }
            }
        }
        gameList.setDatas(datas);
        EventBus.getDefault().post(gameList);
    }
}
