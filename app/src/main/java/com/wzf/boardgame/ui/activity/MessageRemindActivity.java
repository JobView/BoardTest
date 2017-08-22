package com.wzf.boardgame.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wzf.boardgame.R;
import com.wzf.boardgame.constant.UrlService;
import com.wzf.boardgame.e.E_Relationship;
import com.wzf.boardgame.function.http.ResponseSubscriber;
import com.wzf.boardgame.function.http.dto.request.GetUserRelationReqDto;
import com.wzf.boardgame.function.http.dto.response.FollowLiseResDto;
import com.wzf.boardgame.function.http.dto.response.MessageRemindResDto;
import com.wzf.boardgame.function.imageloader.ImageLoader;
import com.wzf.boardgame.ui.adapter.OnRecyclerScrollListener;
import com.wzf.boardgame.ui.adapter.RcyCommonAdapter;
import com.wzf.boardgame.ui.adapter.RcyViewHolder;
import com.wzf.boardgame.ui.base.BaseActivity;
import com.wzf.boardgame.ui.model.UserInfo;
import com.wzf.boardgame.utils.StringUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @Description:
 * @author: wangzhenfei
 * @date: 2017-08-22 08:56
 */

public class MessageRemindActivity extends BaseActivity {
    @Bind(R.id.im_left)
    ImageView imLeft;
    @Bind(R.id.tv_center)
    TextView tvCenter;
    @Bind(R.id.im_right1)
    ImageView imRight1;
    @Bind(R.id.rv)
    RecyclerView rv;
    private RcyCommonAdapter adapter;
    private int page = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_remind);
        ButterKnife.bind(this);
        initView();
        getData();
    }

    private void initView() {
        imLeft.setVisibility(View.VISIBLE);
        tvCenter.setText("消息提醒");
        tvCenter.setVisibility(View.VISIBLE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        adapter = getAdapter();
        rv.setAdapter(adapter);
        rv.addOnScrollListener(new OnRecyclerScrollListener(adapter, null, layoutManager) {
            @Override
            public void loadMore() {
                if(!adapter.isLoadFinish()){
                    getData();
                }
            }
        });
    }

    private RcyCommonAdapter getAdapter() {
        return new RcyCommonAdapter<MessageRemindResDto.MsgListBean>(this, new ArrayList<MessageRemindResDto.MsgListBean>(), true, rv){
            @Override
            public void convert(RcyViewHolder holder, MessageRemindResDto.MsgListBean o) {
                TextView tvTime = holder.getView(R.id.tv_time);
                TextView tvContent = holder.getView(R.id.tv_content);
                TextView tvNickname = holder.getView(R.id.tv_nickname);
                ImageView imV = holder.getView(R.id.im_v);
                tvTime.setText(o.getReplyTime());
                tvContent.setText(o.getNickname() + "回复了您的帖子:" + o.getReplySummary());
                tvNickname.setText(o.getNickname());
                imV.setVisibility(o.getAuthLevel() == 0? View.GONE : View.VISIBLE);
            }

            @Override
            public int getLayoutId(int position) {
                return R.layout.item_message_remind;
            }

            @Override
            public void onItemClickListener(int position) {
                super.onItemClickListener(position);
                PostDetailActivity.startMethod(MessageRemindActivity.this, mDatas.get(position).getPostId());
            }
        };
    }

    private void getData() {
        GetUserRelationReqDto reqDto = new GetUserRelationReqDto();
        reqDto.setPageNum(page);
        UrlService.SERVICE.getPostMsgList(reqDto.toEncodeString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new ResponseSubscriber<MessageRemindResDto>(this, true) {
                    @Override
                    public void onSuccess(MessageRemindResDto responseDto) throws Exception {
                        super.onSuccess(responseDto);
                        page ++;
                        adapter.loadMore(responseDto.getMsgList(), responseDto.getIsLastPage() == 1);
                    }
                    @Override
                    public void onFailure(int code, String message) throws Exception {
                        super.onFailure(code, message);
                        showToast(message);
                    }
                });
    }


    @OnClick({R.id.im_left, R.id.im_right1})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.im_left:
                finish();
                break;
            case R.id.im_right1:
                startActivity(new Intent(this, AddFriendsActivity.class));
                break;
        }
    }
}
