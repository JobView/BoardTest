package com.wzf.boardgame.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


import com.mob.MobSDK;
import com.wzf.boardgame.MyApplication;
import com.wzf.boardgame.R;
import com.wzf.boardgame.function.share.ResourcesManager;
import com.wzf.boardgame.ui.base.BaseActivity;
import com.wzf.boardgame.utils.ScreenUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;

/**
 * @Description: 分享的封装
 * @author: wangzhenfei
 * @date: 2017-05-10 18:35
 */

public class ShareDialog extends Dialog {
    private BaseActivity mActivity;
    /**
     * 分享体
     */
    private String imgUrl;
    private String title;
    private String content;
    private String targetUrl;

    public ShareDialog(BaseActivity context, String imgUrl, String title, String content
            , String targetUrl) {
        super(context, R.style.InputTextTheme);
        this.mActivity = context;
        this.imgUrl = imgUrl;
        this.title = title;
        this.content = content;
        this.targetUrl = targetUrl;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);
//        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        setContentView(R.layout.dialog_share);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.gravity = Gravity.BOTTOM;
        attributes.width = ScreenUtils.getScreenWidth(MyApplication.getAppInstance());
        setCanceledOnTouchOutside(true);
        ButterKnife.bind(this);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.tv_wechat, R.id.tv_circle_friends, R.id.tv_qq, R.id.tv_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_wechat:
                break;
            case R.id.tv_circle_friends:
                break;
            case R.id.tv_qq:
                Platform platform = ShareSDK.getPlatform(QQ.NAME);
                Platform.ShareParams shareParams = new  Platform.ShareParams();
                shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
                shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
                shareParams.setTitleUrl(ResourcesManager.getInstace(MobSDK.getContext()).getTitleUrl());
                shareParams.setShareType(Platform.SHARE_WEBPAGE);
//                platform.setPlatformActionListener(platformActionListener);
                platform.share(shareParams);
                break;
            case R.id.tv_cancel:

                break;
        }
        dismiss();
    }
}
