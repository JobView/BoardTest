package com.wzf.boardgame.ui.views.flipview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

/**
 * Created by zhenfei.wang on 2016/6/24.
 */
public abstract class BaseView extends FrameLayout implements BaseViewSetting {
    protected Context mContext;

    public BaseView(Context context) {
        super(context);
        init(context);
    }

    public BaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BaseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    protected  void init(Context context){
        this.mContext = context;
        addView(LayoutInflater.from(mContext).inflate(getLayuoutId(), null, false));
    }
    protected abstract void setViewSettingAndShow(BaseViewSetting setting);
    protected abstract BaseViewSetting getViewSetting();
    protected abstract int getLayuoutId();
    protected abstract void updateView();
}
