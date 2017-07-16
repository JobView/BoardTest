package com.wzf.boardgame.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.wzf.boardgame.R;
import com.wzf.boardgame.utils.SoftInputUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wangzhenfei on 2016/10/12.
 * 通用的提示窗口
 */
public class AlertCommonDialog extends Dialog implements View.OnClickListener {
    @Bind(R.id.tv_content)
    TextView tvContent;
    @Bind(R.id.tv_positive)
    TextView tvPositive;
    @Bind(R.id.tv_negative)
    TextView tvNegative;
    @Bind(R.id.cut_line)
    View cutLine;
    private Context mContext;

    private String content;
    private String positiveText;
    private String negativeText;
    private boolean isCancel;

    /**
     * @param context
     * @param content      内容
     * @param positiveText 确定
     * @param negativeText 取消 只需要一个按钮传null
     */
    public AlertCommonDialog(Context context, String content, String positiveText, String negativeText) {
        super(context);
        mContext = context;
        this.content = content;
        this.positiveText = positiveText;
        this.negativeText = negativeText;
    }

    /**
     * @param context
     * @param content      内容
     * @param positiveText 确定
     * @param negativeText 取消 只需要一个按钮传null
     * @param isCancel     点击弹框其他地方是否消失 默认false
     */
    public AlertCommonDialog(Context context, String content, String positiveText, String negativeText, boolean isCancel) {
        super(context);
        mContext = context;
        this.content = content;
        this.positiveText = positiveText;
        this.negativeText = negativeText;
        this.isCancel = isCancel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = this.getWindow();
        //设置无标题  需在setContentView之前
        window.requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_common_alert);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.gravity = Gravity.CENTER;
        setCancelable(isCancel);
        ButterKnife.bind(this);
        initDatas();
    }

    private void initDatas() {
        if (TextUtils.isEmpty(negativeText)) {
            cutLine.setVisibility(View.GONE);
            tvNegative.setVisibility(View.GONE);
        } else {
            tvNegative.setText(negativeText);
        }
        tvContent.setText(content);
        tvPositive.setText(positiveText);
        tvPositive.setOnClickListener(this);
        tvNegative.setOnClickListener(this);

    }


    @Override
    public void dismiss() {
        SoftInputUtil.closeSoftInput(mContext);
        super.dismiss();
    }

    @OnClick({R.id.tv_positive, R.id.tv_negative})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_positive:
                onPositiveClick();
                break;
            case R.id.tv_negative:
                onNegativeClick();
                break;
        }
        dismiss();
    }

    public void onPositiveClick() {
    }

    public void onNegativeClick() {
    }
}
