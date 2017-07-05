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
import butterknife.OnClick;

/**
 * Created by wzf on 2017/7/5.
 */

public class GameFragment extends BaseFragment {
    View mRootView;
    @Bind(R.id.tv_center)
    TextView tvCenter;
    @Bind(R.id.im_right1)
    ImageView imRight1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = bActivity.getLayoutInflater().inflate(R.layout.fragment_game, null);
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
        tvCenter.setText("桌游百科");
        tvCenter.setVisibility(View.VISIBLE);
        imRight1.setImageResource(R.mipmap.game_btn_mali_nor);
        imRight1.setVisibility(View.VISIBLE);
    }


    @OnClick(R.id.im_right1)
    public void onViewClicked() {

    }
}
