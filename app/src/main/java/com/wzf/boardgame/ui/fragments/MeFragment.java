package com.wzf.boardgame.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wzf.boardgame.R;
import com.wzf.boardgame.ui.base.BaseFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wzf on 2017/7/5.
 */

public class MeFragment extends BaseFragment {
    
    View mRootView;
    @Bind(R.id.tv_center)
    TextView tvCenter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = bActivity.getLayoutInflater().inflate(R.layout.fragment_community, null);
            ButterKnife.bind(this, mRootView);
            initData();
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        return mRootView;
    }

    private void initData() {
        tvCenter.setText("个人资料");
        tvCenter.setVisibility(View.VISIBLE);
    }

}
