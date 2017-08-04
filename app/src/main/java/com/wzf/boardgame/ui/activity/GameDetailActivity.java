package com.wzf.boardgame.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wzf.boardgame.R;
import com.wzf.boardgame.constant.UrlService;
import com.wzf.boardgame.function.http.ResponseSubscriber;
import com.wzf.boardgame.function.http.dto.request.CommentGameListReqDto;
import com.wzf.boardgame.function.http.dto.request.GameCommentRecommendReqDto;
import com.wzf.boardgame.function.http.dto.request.GameReqDto;
import com.wzf.boardgame.function.http.dto.request.PostReqDto;
import com.wzf.boardgame.function.http.dto.response.GameCommentListResDto;
import com.wzf.boardgame.function.http.dto.response.GameDetailResDto;
import com.wzf.boardgame.function.imageloader.ImageLoader;
import com.wzf.boardgame.ui.adapter.OnRecyclerScrollListener;
import com.wzf.boardgame.ui.adapter.RcyCommonAdapter;
import com.wzf.boardgame.ui.adapter.RcyViewHolder;
import com.wzf.boardgame.ui.base.BaseActivity;
import com.wzf.boardgame.utils.ViewUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @Description:
 * @author: wangzhenfei
 * @date: 2017-08-03 09:34
 */

public class GameDetailActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.im_left)
    ImageView imLeft;
    @Bind(R.id.tv_center)
    TextView tvCenter;
    @Bind(R.id.im_right1)
    ImageView imRight1;
    @Bind(R.id.im_right2)
    ImageView imRight2;

    @Bind(R.id.srl)
    SwipeRefreshLayout srl;
    @Bind(R.id.rv)
    RecyclerView rv;

    private RcyCommonAdapter<GameCommentListResDto.CommentListBean> adapter;
    int page = 1;
    private GameDetailResDto headerDto;

    private String gameId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);
        ButterKnife.bind(this);
        gameId = getIntent().getStringExtra("gameId");
        initView();
        initData();
    }


    private void initView() {
        imLeft.setVisibility(View.VISIBLE);
        tvCenter.setText("");
        tvCenter.setVisibility(View.VISIBLE);
        imRight1.setImageResource(R.mipmap.forum_btn_comment_nor);
        imRight1.setVisibility(View.VISIBLE);
        imRight2.setImageResource(R.mipmap.forum_btn_collect_nor);
        imRight2.setVisibility(View.VISIBLE);
        srl.setOnRefreshListener(this);
        ViewUtils.setSwipeRefreshLayoutSchemeResources(srl);
        LinearLayoutManager llManager = new LinearLayoutManager(this);
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

    private void initData() {
        getHeader();
    }

    @Override
    protected void onResume() {
        super.onResume();
        onRefresh();
    }

    private void getHeader() {
        UrlService.SERVICE.getInformation(new GameReqDto(gameId).toEncodeString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new ResponseSubscriber<GameDetailResDto>() {
                    @Override
                    public void onSuccess(GameDetailResDto dto) throws Exception {
                        super.onSuccess(dto);
                        headerDto = dto;
                        updateTitleBar();
                        if (headerDto != null) {
                            if (!(adapter.getmDatas().size() > 0 && adapter.getmDatas().get(0).isHeader)) { //第一条不是广告
                                adapter.getmDatas().add(0, new GameCommentListResDto.CommentListBean(true)); // 为广告留位置
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(int code, String message) throws Exception {
                        super.onFailure(code, message);
                        showToast(message);
                    }
                });
    }

    private void updateTitleBar() {
        tvCenter.setText(headerDto.getBoardTitle());
        imRight2.setImageResource(headerDto.getIsCollect() == 0? R.mipmap.forum_btn_collect_nor : R.mipmap.forum_btn_collect_sel);
    }


    private RcyCommonAdapter<GameCommentListResDto.CommentListBean> getAdapter() {
        return new RcyCommonAdapter<GameCommentListResDto.CommentListBean>(this, new ArrayList<GameCommentListResDto.CommentListBean>(), true, rv) {
            @Override
            public void convert(RcyViewHolder holder, GameCommentListResDto.CommentListBean o) {
                if (o.isHeader) {
                    fetchHeader(holder);
                } else {
                    fetchContent(holder, o);
                }
            }

            private void fetchHeader(RcyViewHolder holder) {
                ImageView imBgAvatar = holder.getView(R.id.im_bg_avatar);
                ImageView imAvatar = holder.getView(R.id.im_avatar);
                TextView tvName = holder.getView(R.id.tv_name);
                TextView tvDate = holder.getView(R.id.tv_date);
                TextView tvCountry = holder.getView(R.id.tv_country);
                TextView tvDesign = holder.getView(R.id.tv_design);
                TextView tvPlayCount = holder.getView(R.id.tv_play_count);
                TextView tvPlayTime = holder.getView(R.id.tv_play_time);
                TextView tvScore = holder.getView(R.id.tv_score);
                TextView tvHardScore = holder.getView(R.id.tv_hard_score);
                TextView tvDescription = holder.getView(R.id.tv_description);

                ImageLoader.getInstance().displayOnlineImage(headerDto.getBoardBackgroundUrl(), imBgAvatar, 0, 0);
                ImageLoader.getInstance().displayOnlineImage(headerDto.getBoardImgUrl(), imAvatar, 0, 0);
                tvName.setText(headerDto.getBoardEnglishTitle());
                tvDate.setText(headerDto.getIssueYear());
                tvCountry.setText(headerDto.getIssuePlace());
                tvDesign.setText(headerDto.getBoardDesigner());
                tvPlayCount.setText(headerDto.getPlayers() + " Players");
                tvPlayTime.setText(headerDto.getGameTime() + " Min");
                tvScore.setText(headerDto.getGameScore() + "");
                tvHardScore.setText(headerDto.getDifficultyDegree() + "");
                tvDescription.setText(headerDto.getBoardAbstract());
            }

            private void fetchContent(RcyViewHolder holder, final GameCommentListResDto.CommentListBean o) {
                ImageView imAvatar = holder.getView(R.id.im_avatar);
                ImageView imLike = holder.getView(R.id.im_like);
                TextView tvNickname = holder.getView(R.id.tv_nickname);
                TextView tvTime = holder.getView(R.id.tv_time);
                TextView tvLikeCount = holder.getView(R.id.tv_like_count);
                TextView tvComment = holder.getView(R.id.tv_comment);
                ImageLoader.getInstance().displayOnlineRoundImage(o.getAvatarUrl(), imAvatar);
                tvNickname.setText(o.getNickname());
                tvTime.setText(o.getCommentTime());
                tvComment.setText(o.getCommentContent());
                tvLikeCount.setText(o.getCommentCount() + "");
                int rid = 0;
                if(o.getIsRecommend() == 1){// 推荐
                    if(o.getIsClick() == 1){ // 点击过
                        rid = R.mipmap.gamedata_btn_like_sel;
                    }else {
                        rid = R.mipmap.gamedata_btn_like_nor;
                    }
                }else { // 未推荐
                    if(o.getIsClick() == 1){ // 点击过
                        rid = R.mipmap.gamedata_btn_unlike_sel;
                    }else {
                        rid = R.mipmap.gamedata_btn_unlike_nor;
                    }
                }
                imLike.setImageResource(rid);
                final int index = mDatas.indexOf(o);
                imLike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(o.getIsClick() == 1){ // 点击过了,取消操作
                            UrlService.SERVICE.cancelThumbsUp(new GameCommentRecommendReqDto(o.getCommentId()).toEncodeString())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribeOn(Schedulers.io())
                                    .subscribe(new ResponseSubscriber<Object>() {
                                        @Override
                                        public void onSuccess(Object dto) throws Exception {
                                            super.onSuccess(dto);
                                            o.setIsClick(0);
                                            o.setCommentCount(o.getCommentCount() - 1);
                                            notifyItemChanged(index);
                                        }

                                        @Override
                                        public void onFailure(int code, String message) throws Exception {
                                            super.onFailure(code, message);
                                            showToast(message);
                                        }
                                    });
                        }else {// 未点击过了,推荐操作
                            UrlService.SERVICE.thumbsUp(new GameCommentRecommendReqDto(o.getCommentId()).toEncodeString())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribeOn(Schedulers.io())
                                    .subscribe(new ResponseSubscriber<Object>() {
                                        @Override
                                        public void onSuccess(Object dto) throws Exception {
                                            super.onSuccess(dto);
                                            o.setIsClick(1);
                                            o.setCommentCount(o.getCommentCount() + 1);
                                            notifyItemChanged(index);
                                        }

                                        @Override
                                        public void onFailure(int code, String message) throws Exception {
                                            super.onFailure(code, message);
                                            showToast(message);
                                        }
                                    });
                        }
                    }
                });
            }

            @Override
            public int getLayoutId(int position) {
                GameCommentListResDto.CommentListBean bean = mDatas.get(position);
                if (bean.isHeader) {
                    return R.layout.layout_header_game;
                } else {
                    return R.layout.item_game_comment_list;
                }
            }
        };
    }

    @Override
    public void onRefresh() {
        getData(true);
    }

    private void getData(final boolean refresh) {
        if (refresh) {
            page = 1;
        }
        CommentGameListReqDto reqDto = new CommentGameListReqDto();
        reqDto.setBoardId(gameId);
        reqDto.setPageNum(page);
        reqDto.setPageSize(10);
        UrlService.SERVICE.getCommentList(reqDto.toEncodeString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new ResponseSubscriber<GameCommentListResDto>() {
                    @Override
                    public void onSuccess(GameCommentListResDto dto) throws Exception {
                        super.onSuccess(dto);
                        page++;
                        if (refresh) {
                            if (headerDto != null) {
                                dto.getCommentList().add(0, new GameCommentListResDto.CommentListBean(true)); // 为广告留位置
                            }
                            adapter.refresh(dto.getCommentList());
                            srl.setRefreshing(false);
                        } else {
                            adapter.loadMore(dto.getCommentList());
                        }
                        if (dto.getIsLastPage() == 1) {
                            adapter.loadMoreFinish();
                        }
                    }

                    @Override
                    public void onFailure(int code, String message) throws Exception {
                        super.onFailure(code, message);
                        showToast(message);
                    }
                });
    }



    @OnClick({R.id.im_left, R.id.im_right1, R.id.im_right2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.im_left:
                finish();
                break;
            case R.id.im_right1:
                if(headerDto != null){
                    GameCommentActivity.startMethod(this, gameId);
                }
                break;
            case R.id.im_right2:
                if(headerDto != null){
                    changeCollectStatus();
                }
                break;
        }
    }

    private void changeCollectStatus() {
        if(headerDto.getIsCollect() == 0){
            UrlService.SERVICE.collectBoardGame(new GameReqDto(gameId).toEncodeString())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new ResponseSubscriber<Object>() {
                        @Override
                        public void onSuccess(Object dto) throws Exception {
                            super.onSuccess(dto);
                            headerDto.setIsCollect(1);
                            updateTitleBar();
                        }

                        @Override
                        public void onFailure(int code, String message) throws Exception {
                            super.onFailure(code, message);
                            showToast(message);
                        }
                    });
        }else {
            UrlService.SERVICE.cancelCollectBoardGame(new GameReqDto(gameId).toEncodeString())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new ResponseSubscriber<Object>() {
                        @Override
                        public void onSuccess(Object dto) throws Exception {
                            super.onSuccess(dto);
                            headerDto.setIsCollect(0);
                            updateTitleBar();
                        }

                        @Override
                        public void onFailure(int code, String message) throws Exception {
                            super.onFailure(code, message);
                            showToast(message);
                        }
                    });
        }

    }

    public static void startMethod(Context context, String gameId) {
        context.startActivity(new Intent(context, GameDetailActivity.class).
                putExtra("gameId", gameId));
    }
}
