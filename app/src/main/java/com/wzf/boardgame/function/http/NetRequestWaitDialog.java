package com.wzf.boardgame.function.http;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.wzf.boardgame.R;


/**
 * Created by wangzhenfei on 2016/11/2.
 */
public class NetRequestWaitDialog extends Dialog {
    TextView tv;
    private Context mContext;
    private String message;

    public NetRequestWaitDialog(Context context, String content) {
        super(context);
        mContext = context;
        this.message = content;
    }

    public void setMessage(String message) {
        this.message = message;
        if(tv != null){
            tv.setText(message);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = this.getWindow();
        //设置无标题  需在setContentView之前
        window.requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_net_request);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = (int)(Utils.getScreenWidth(mContext) * 3.0 / 4);
        setCancelable(false);
        tv = (TextView) findViewById(R.id.tv);
        setMessage();
    }

    private void setMessage() {
        if(tv != null){
            tv.setText(message);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        dismiss();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        mContext = null;
    }
}
