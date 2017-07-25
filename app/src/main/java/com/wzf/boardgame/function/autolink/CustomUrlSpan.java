package com.wzf.boardgame.function.autolink;

import android.content.Context;
import android.text.style.ClickableSpan;
import android.view.View;

import com.wzf.boardgame.ui.activity.WebViewActivity;

/**
 * Created by wzf on 2017/7/23.
 */

public class CustomUrlSpan extends ClickableSpan {
    private Context context;
    private String url;
    public CustomUrlSpan(Context context, String url){
        this.context = context;
        this.url = url;
    }
    @Override
    public void onClick(View widget) {
        // 在这里可以做任何自己想要的处理
        WebViewActivity.startMethod(context, url);
    }
}
