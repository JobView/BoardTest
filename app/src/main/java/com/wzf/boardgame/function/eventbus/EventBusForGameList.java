package com.wzf.boardgame.function.eventbus;

import com.wzf.boardgame.function.http.dto.response.GameListResDto;

import java.util.List;

/**
 * @Description:
 * @author: wangzhenfei
 * @date: 2017-07-31 09:11
 */

public class EventBusForGameList {
    private List<GameListResDto.WaterfallListBean> datas;
    private boolean isFresh;


    public List<GameListResDto.WaterfallListBean> getDatas() {
        return datas;
    }

    public void setDatas(List<GameListResDto.WaterfallListBean> datas) {
        this.datas = datas;
    }

    public boolean isFresh() {
        return isFresh;
    }

    public void setFresh(boolean fresh) {
        isFresh = fresh;
    }
}
