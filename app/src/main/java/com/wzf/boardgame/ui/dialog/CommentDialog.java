package com.wzf.boardgame.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.wzf.boardgame.R;
import com.wzf.boardgame.ui.base.BaseActivity;
import com.wzf.boardgame.utils.ScreenUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by YOUNG on 2017-05-02.
 */

public abstract class CommentDialog extends Dialog {

    private final Context context;
    private int maxLength;
    private String hint;
    private String text;
    @Bind(R.id.et_input_text)
    EditText etInputText;
    @Bind(R.id.tv_confirm_input)
    TextView tvConfirmInput;

    public CommentDialog(Context context, String text, String hint, int max) {
        super(context, R.style.InputTextTheme);
        this.context = context;
        this.maxLength = max;
        this.hint = hint;
        this.text = text;
    }

    public CommentDialog(Context context, int max) {
        super(context, R.style.InputTextTheme);
        this.context = context;
        this.maxLength = max;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        setContentView(R.layout.dialog_comment);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.gravity = Gravity.CENTER;
        attributes.width = ScreenUtils.getScreenWidth(context);
        ButterKnife.bind(this);
        setCanceledOnTouchOutside(true);
        if (maxLength != 0) {
            etInputText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        }
        if(!TextUtils.isEmpty(hint)){
            etInputText.setHint(hint);
        }

        if(!TextUtils.isEmpty(text)){
            etInputText.setText(text);
        }
    }

    @OnClick(R.id.tv_confirm_input)
    public void onClick() {
        if (etInputText.getText().toString().length() > 0) {
            sendText(etInputText.getText().toString().replace(" ", ""));
            dismiss();
        } else {
            ((BaseActivity) context).showToast("不能为空");
        }
    }

    abstract public void sendText(String text);

    @Override
    public void onBackPressed() {
        dismiss();
    }
}
