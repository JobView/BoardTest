package com.wzf.boardgame.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.wzf.boardgame.R;
import com.wzf.boardgame.constant.UrlService;
import com.wzf.boardgame.function.autolink.AutoLinkUtils;
import com.wzf.boardgame.function.http.ResponseSubscriber;
import com.wzf.boardgame.function.http.dto.request.GameContentReqDto;
import com.wzf.boardgame.function.http.dto.response.GameRuleResDto;
import com.wzf.boardgame.ui.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by wzf on 2017/8/19.
 */

public class GameContentFragment extends BaseFragment {
    View mRootView;
    @Bind(R.id.wb)
    WebView wb;
    @Bind(R.id.tv_empty)
    TextView tvEmpty;
    private String boardId;
    private int type;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = bActivity.getLayoutInflater().inflate(R.layout.fragment_game_rule, null);
            ButterKnife.bind(this, mRootView);
            init();
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        return mRootView;
    }

    private void init() {
        tvEmpty.setVisibility(View.GONE);
        boardId = getArguments() == null ? "" : getArguments().getString("boardId");
        type = getArguments() == null ? 1 : getArguments().getInt("type", 1);
//        tv.setAutoLinkMask(Linkify.WEB_URLS);
//        AutoLinkUtils.interceptHyperLink(tv);
        WebSettings webSettings = wb.getSettings();//获取webview设置属性
        webSettings.setJavaScriptEnabled(true);//支持js
        wb.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                imgReset();//重置webview中img标签的图片大小
                // html加载完成之后，添加监听图片的点击js函数
//                                    addImageClickListner();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });


//        wb.addJavascriptInterface(new JavaScriptInterface(this), "imagelistner")
        loadData();
    }

    /**
     * 对图片进行重置大小，宽度就是手机屏幕宽度，高度根据宽度比便自动缩放
     **/
    private void imgReset() {
        wb.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName('img'); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "var img = objs[i];   " +
                "    img.style.maxWidth = '100%'; img.style.height = 'auto';  " +
                "}" +
                "})()");
    }

    private void loadData() {
        GameContentReqDto reqDto = new GameContentReqDto();
        reqDto.setBoardId(boardId);
        reqDto.setContentType(type);
        UrlService.SERVICE.getBoardGameContent(reqDto.toEncodeString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new ResponseSubscriber<GameRuleResDto>(bActivity, true) {
                    @Override
                    public void onSuccess(GameRuleResDto responseDto) throws Exception {
                        super.onSuccess(responseDto);
                        if (TextUtils.isEmpty(responseDto.getContent())) {
                            tvEmpty.setText(type == 1 ? "暂无规则" : "暂无扩展");
                            tvEmpty.setVisibility(View.VISIBLE);
                        } else {
//                            tv.setText(Html.fromHtml(responseDto.getContent()));
                            //  加载、并显示HTML代码
                            wb.loadDataWithBaseURL(null, responseDto.getContent(), "text/html", "utf-8", null);
                            tvEmpty.setVisibility(View.GONE);
                        }

                    }

                    @Override
                    public void onFailure(int code, String message) throws Exception {
                        super.onFailure(code, message);
                        bActivity.showToast(message);
                    }
                });
    }

    public static BaseFragment getInstance(String boardId, int type) {
        GameContentFragment fragment = new GameContentFragment();
        Bundle bundle = new Bundle();
        bundle.putString("boardId", boardId);
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }
}
