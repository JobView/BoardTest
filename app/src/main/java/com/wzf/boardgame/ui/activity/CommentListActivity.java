package com.wzf.boardgame.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.text.util.Linkify;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wzf.boardgame.MyApplication;
import com.wzf.boardgame.R;
import com.wzf.boardgame.constant.UrlService;
import com.wzf.boardgame.function.autolink.AutoLinkUtils;
import com.wzf.boardgame.function.http.ResponseSubscriber;
import com.wzf.boardgame.function.http.dto.request.PostReqDto;
import com.wzf.boardgame.function.http.dto.request.ReplyCommentReqDto;
import com.wzf.boardgame.function.http.dto.response.CommentListResDto;
import com.wzf.boardgame.function.imageloader.ImageLoader;
import com.wzf.boardgame.ui.adapter.OnRecyclerScrollListener;
import com.wzf.boardgame.ui.adapter.RcyCommonAdapter;
import com.wzf.boardgame.ui.adapter.RcyViewHolder;
import com.wzf.boardgame.ui.base.BaseActivity;
import com.wzf.boardgame.ui.dialog.CommentDialog;
import com.wzf.boardgame.ui.model.UserInfo;
import com.wzf.boardgame.utils.ScreenUtils;
import com.wzf.boardgame.utils.StringUtils;
import com.wzf.boardgame.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

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
        imRight1.setImageResource(R.mipmap.forum_btn_comment_nor);
        imRight1.setVisibility(View.VISIBLE);
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
                        srl.setRefreshing(false);
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
                ImageView imV= holder.getView(R.id.im_v);
                TextView tvFloor = holder.getView(R.id.tv_floor);
                TextView tvTime = holder.getView(R.id.tv_time);
                LinearLayout llCommentContent = holder.getView(R.id.ll_comment_content);
                final LinearLayout llComment = holder.getView(R.id.ll_comment);
                ImageLoader.getInstance().displayOnlineRoundImage(replyListBean.getAvatarUrl(), imAvatar);
                imAvatar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UserInfoActivity.startMethod(CommentListActivity.this, replyListBean.getUserId());
                    }
                });
                tvName.setText(replyListBean.getNickname());
                imV.setVisibility(replyListBean.getAuthLevel() == 0 ? View.GONE : View.VISIBLE);
                tvFloor.setText(replyListBean.getStorey() + "楼");
                tvTime.setText(replyListBean.getReplyTime());
                //内容填充
                llCommentContent.removeAllViews();
                String content = replyListBean.getReplyContent();
                String[] cs = content.split("\\{\\$img\\$\\}");
                TextView tvContent;
                ImageView im;
                for (int i = 0; i < cs.length; i++) {
                    tvContent = new TextView(CommentListActivity.this);
                    tvContent.setTextSize(16);
                    tvContent.setText(cs[i].replace("\n", ""));
                    tvContent.setAutoLinkMask(Linkify.WEB_URLS);
                    AutoLinkUtils.interceptHyperLink(tvContent);
                    llCommentContent.addView(tvContent);
                    if (replyListBean.getReplyImgsUrl().size() > i) {
                        im = new ImageView(CommentListActivity.this);
                        im.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        int w = ScreenUtils.getScreenWidth(MyApplication.getAppInstance())/ 2;
                        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(w, w);
                        im.setLayoutParams(params);
                        ImageLoader.getInstance().displayOnlineImage(replyListBean.getReplyImgsUrl().get(i), im, 0, 0);
                        llCommentContent.addView(im);
                        final int position = i;
                        im.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ViewUtils.previewPicture(CommentListActivity.this,position, replyListBean.getReplyImgsUrl());
                            }
                        });
                    }
                }
                imReplyMain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new CommentDialog(CommentListActivity.this, "", "请输入回复内容", 200) {
                            @Override
                            public void sendText(String text) {
                                if(!TextUtils.isEmpty(text)){
                                    //模拟一份数据
                                    int index = mDatas.indexOf(replyListBean);
                                    commentFloor(replyListBean.getReplyId(), replyListBean.getUserId(),replyListBean.getNickname(), text, index);
                                }
                            }
                        }.show();
                    }
                });

                final View.OnClickListener clickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        //对楼层回复
                        new CommentDialog(CommentListActivity.this, "", "请输入回复内容", 200) {
                            @Override
                            public void sendText(String text) {
                                if(!TextUtils.isEmpty(text)){
                                    //模拟一份数据
                                    int index = mDatas.indexOf(replyListBean);
                                    Integer tag = (Integer) v.getTag();
                                    tag = tag == null ? 0 : tag;
                                    commentFloor(replyListBean.getReplyId(), replyListBean.getReplyAnswerList().get(tag).getAnswerUserId(),
                                            replyListBean.getReplyAnswerList().get(tag).getAnswerNickname(), text, index);
                                }
                            }
                        }.show();
                    }
                };
                llComment.removeAllViews();
                final List<CommentListResDto.ReplyListBean.ReplyAnswerListBean> replyAnswerList = replyListBean.getReplyAnswerList();
                if(replyAnswerList.size() > 0){
                    if(replyAnswerList.size() > 2){//需要拓展
                        for (int i = 0 ; i < 2; i ++){
                            TextView tv  = getTextView();
                            String str;
                            if(replyListBean.getUserId().equals(replyAnswerList.get(i).getBeAnswerUserId())){ //回复楼主，不需要加@
                                str = StringUtils.concat("<font color='#5677fc'>", replyAnswerList.get(i).getAnswerNickname(), " : </font>" ,
                                        replyAnswerList.get(i).getAnswerContent());
                            }else {
                                str =  StringUtils.concat("<font color='#5677fc'>", replyAnswerList.get(i).getAnswerNickname() , " : " ,
                                        "</font>回复" , replyAnswerList.get(i).getBeAnswerNickname() , " : " , replyAnswerList.get(i).getAnswerContent());
                            }

                            tv.setText(Html.fromHtml(str));
                            tv.setTag(i);

                            tv.setOnClickListener(clickListener);
                            llComment.addView(tv);
                        }
                        TextView tv = getTextView();
                        tv.setGravity(Gravity.CENTER);
                        tv.setPadding(10, 5, 10, 5);
                        tv.setText(Html.fromHtml("更多" + (replyAnswerList.size() - 2) + "条回复"));
                        llComment.addView(tv);
                        tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                llComment.removeAllViews();
                                for (int i = 0 ; i < replyAnswerList.size(); i ++){
                                    TextView tv = getTextView();
                                    String str;
                                    if(replyListBean.getUserId().equals(replyAnswerList.get(i).getBeAnswerUserId())){ //回复楼主，不需要加@
                                        str = StringUtils.concat("<font color='#5677fc'>", replyAnswerList.get(i).getAnswerNickname(), " : </font>" ,
                                                replyAnswerList.get(i).getAnswerContent());
                                    }else {
                                        str =  StringUtils.concat("<font color='#5677fc'>", replyAnswerList.get(i).getAnswerNickname() , " : " ,
                                                "</font>回复" , replyAnswerList.get(i).getBeAnswerNickname() , " : " , replyAnswerList.get(i).getAnswerContent());
                                    }
                                    tv.setText(Html.fromHtml(str));
                                    llComment.addView(tv);
                                    tv.setOnClickListener(clickListener);
                                    tv.setTag(i);
                                    view.setVisibility(View.GONE);
                                }
                            }
                        });
                    }else {
                        for (int i = 0 ; i < replyAnswerList.size(); i ++){
                            TextView tv = getTextView();
                            String str;
                            if(replyListBean.getUserId().equals(replyAnswerList.get(i).getBeAnswerUserId())){ //回复楼主，不需要加@
                                str = StringUtils.concat("<font color='#5677fc'>", replyAnswerList.get(i).getAnswerNickname(), " : </font>" ,
                                        replyAnswerList.get(i).getAnswerContent());
                            }else {
                                str =  StringUtils.concat("<font color='#5677fc'>", replyAnswerList.get(i).getAnswerNickname() , " : " ,
                                        "</font>回复" , replyAnswerList.get(i).getBeAnswerNickname() , ": " , replyAnswerList.get(i).getAnswerContent());
                            }
                            tv.setText(Html.fromHtml(str));
                            tv.setOnClickListener(clickListener);
                            tv.setTag(i);
                            llComment.addView(tv);
                        }
                    }
                    llComment.setVisibility(View.VISIBLE);
                }else {
                    llComment.setVisibility(View.GONE);
                }
            }

            private void commentFloor(String replyId, String userId, String nickname, String text, int index) {
                CommentListResDto.ReplyListBean.ReplyAnswerListBean bean = new CommentListResDto.ReplyListBean.ReplyAnswerListBean();
                bean.setAnswerContent(text);
                bean.setAnswerNickname(UserInfo.getInstance().getNickname());
                bean.setAnswerUserId(UserInfo.getInstance().getUid());
                bean.setBeAnswerNickname(nickname);
                bean.setBeAnswerUserId(userId);
                mDatas.get(index).getReplyAnswerList().add(bean);
                notifyItemChanged(index);

                ReplyCommentReqDto reqDto = new ReplyCommentReqDto();
                reqDto.setReplyId(replyId);
                reqDto.setBeAnswerUserId(userId);
                reqDto.setAnswerContent(text);
                UrlService.SERVICE.replyComment(reqDto.toEncodeString())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new ResponseSubscriber<Object>() {
                            @Override
                            public void onSuccess(Object dto) throws Exception {
                                super.onSuccess(dto);
                            }

                            @Override
                            public void onFailure(int code, String message) throws Exception {
                                super.onFailure(code, message);
                                showToast(message);
                            }
                        });
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
                if(postId != null){
                    EditCommentActivity.startMethod(this, postId);
                }
                break;
        }
    }

    public static void startMethod(Context context, String postId) {
        context.startActivity(new Intent(context, CommentListActivity.class).
                putExtra("postId", postId));
    }


}
