package com.wzf.boardgame.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wzf.boardgame.R;
import com.wzf.boardgame.constant.UrlService;
import com.wzf.boardgame.function.http.ResponseSubscriber;
import com.wzf.boardgame.function.http.dto.request.PostReqDto;
import com.wzf.boardgame.function.http.dto.response.CommentListResDto;
import com.wzf.boardgame.function.imageloader.ImageLoader;
import com.wzf.boardgame.ui.adapter.OnRecyclerScrollListener;
import com.wzf.boardgame.ui.adapter.RcyCommonAdapter;
import com.wzf.boardgame.ui.adapter.RcyViewHolder;
import com.wzf.boardgame.ui.base.BaseActivity;
import com.wzf.boardgame.ui.dialog.CommentDialog;
import com.wzf.boardgame.ui.model.UserInfo;
import com.wzf.boardgame.utils.ViewUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wzf on 2017/7/23.
 */

public class CommentListActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{
    @Bind(R.id.im_left)
    ImageView imLeft;
    @Bind(R.id.tv_center)
    TextView tvCenter;
    @Bind(R.id.im_right1)
    ImageView imRight1;
    @Bind(R.id.rv)
    RecyclerView rv;
    @Bind(R.id.srl)
    SwipeRefreshLayout srl;
    private RcyCommonAdapter<CommentListResDto.ReplyListBean> adapter;
    private String postId;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_list);
        ButterKnife.bind(this);
        initView();
        initData();
    }


    private void initView() {
        imLeft.setVisibility(View.VISIBLE);
        tvCenter.setText("评论列表");
        tvCenter.setVisibility(View.VISIBLE);
        imRight1.setImageResource(R.mipmap.tabbar_btn_game_nor);
//        imRight1.setVisibility(View.VISIBLE);
    }


    private void initData() {
        postId = getIntent().getStringExtra("postId");
        srl.setOnRefreshListener(this);
        ViewUtils.setSwipeRefreshLayoutSchemeResources(srl);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        adapter = getAdapter();
        rv.setAdapter(adapter);
        rv.addOnScrollListener(new OnRecyclerScrollListener(adapter, srl, layoutManager) {
            @Override
            public void loadMore() {
                if (!adapter.isLoadFinish()) {
                    loadData(false);
                }
            }
        });
        onRefresh();
    }

    @Override
    public void onRefresh() {
        loadData(true);
    }

    private void loadData(final boolean fresh) {
        final PostReqDto reqDto = new PostReqDto(postId);
        if(fresh){
            page = 1;
        }
        reqDto.setPageNum(page);
        UrlService.SERVICE.getPostReplyList(reqDto.toEncodeString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new ResponseSubscriber<CommentListResDto>(this, true) {
                    @Override
                    public void onSuccess(CommentListResDto responseDto) throws Exception {
                        super.onSuccess(responseDto);
                        page ++;
                        if(fresh){
                            adapter.refresh(responseDto.getReplyList(), responseDto.getIsLastPage() == 1);
                            srl.setRefreshing(false);
                        }else {
                            adapter.loadMore(responseDto.getReplyList(), responseDto.getIsLastPage() == 1);
                        }

                    }

                    @Override
                    public void onFailure(int code, String message) throws Exception {
                        super.onFailure(code, message);
                        showToast(message);
                    }
                });
    }

    private RcyCommonAdapter<CommentListResDto.ReplyListBean> getAdapter() {
        return new RcyCommonAdapter<CommentListResDto.ReplyListBean>(this, new ArrayList<CommentListResDto.ReplyListBean>(), true, rv) {
            @Override
            public void convert(RcyViewHolder holder, final CommentListResDto.ReplyListBean replyListBean) {
                ImageView imAvatar = holder.getView(R.id.im_avatar);
                ImageView imReplyMain = holder.getView(R.id.im_reply_main);
                TextView tvName = holder.getView(R.id.tv_name);
                TextView tvFloor = holder.getView(R.id.tv_floor);
                TextView tvTime = holder.getView(R.id.tv_time);
                TextView tvCommentContent = holder.getView(R.id.tv_comment_content);
                final LinearLayout llComment = holder.getView(R.id.ll_comment);
                ImageLoader.getInstance().displayOnlineRoundImage(replyListBean.getAvatarUrl(), imAvatar);
                tvName.setText(replyListBean.getNickname());
                tvFloor.setText(replyListBean.getStorey() + "楼");
                tvTime.setText(replyListBean.getReplyTime());
                tvCommentContent.setText(replyListBean.getReplyContent());
                imReplyMain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                llComment.removeAllViews();
                View.OnClickListener clickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //对楼层回复
                        new CommentDialog(CommentListActivity.this, "", "请输入回复内容", 200) {
                            @Override
                            public void sendText(String text) {
                                if(!TextUtils.isEmpty(text)){
                                    //模拟一份数据
                                    int index = mDatas.indexOf(replyListBean);
                                    CommentListResDto.ReplyListBean.ReplyAnswerListBean bean = new CommentListResDto.ReplyListBean.ReplyAnswerListBean();
                                    bean.setAnswerContent(text);
                                    bean.setAnswerNickname(UserInfo.getInstance().getNickname());
                                    bean.setAnswerUserId(UserInfo.getInstance().getUid());
                                    bean.setBeAnswerNickname(replyListBean.getNickname());
                                    bean.setBeAnswerUserId(replyListBean.getUserId());
                                    replyListBean.getReplyAnswerList().add(bean);
                                    notifyItemChanged(index);
                                    commentFloor(replyListBean.getReplyId(), replyListBean.getUserId(),replyListBean.getNickname(), text);
                                }
                            }
                        }.show();
                    }
                };
                for (int i = 0 ; i < 2; i ++){
                    TextView tv  = getTextView();
                    String str = "<font color='#5677fc'>"+ "王德荣誉" +"</font>"+ " : " + "你这个逗比"+ i * 13;
                    tv.setText(Html.fromHtml(str));
                    tv.setOnClickListener(clickListener);
                    llComment.addView(tv);
                }
                TextView tv = getTextView();
                tv.setGravity(Gravity.CENTER);
                tv.setPadding(10, 5, 10, 5);
                tv.setText(Html.fromHtml("更多3条回复"));
                llComment.addView(tv);
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for (int i = 0 ; i < 5; i ++){
                            TextView tv = getTextView();
                            String str = "<font color='#5677fc'>"+ "王德荣誉" +"</font>"+ " : " + "你这个逗比"+ i * 13;
                            tv.setText(Html.fromHtml(str));
                            llComment.addView(tv);
                            view.setVisibility(View.GONE);
                        }
                    }
                });
                llComment.setVisibility(View.VISIBLE);
            }

            private void commentFloor(String replyId, String userId, String nickname, String text) {

            }

            public TextView getTextView(){
                TextView tv = new TextView(CommentListActivity.this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                tv.setLayoutParams(params);
                params.setMargins(0,5,0,5);
                tv.setTextColor(getResources().getColor(R.color.textHint));
                tv.setTextSize(14);
                return tv;
            }

            @Override
            public int getLayoutId(int position) {
                return R.layout.item_comment_list;
            }
        };
    }

    @OnClick({R.id.im_left, R.id.im_right1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.im_left:
                finish();
                break;
            case R.id.im_right1:
                break;
        }
    }

    public static void startMethod(Context context, String postId) {
        context.startActivity(new Intent(context, CommentListActivity.class).
                putExtra("postId", postId));
    }


}
