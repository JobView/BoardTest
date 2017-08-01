package com.wzf.boardgame.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wzf.boardgame.R;
import com.wzf.boardgame.ui.base.BaseActivity;
import com.wzf.boardgame.ui.fragments.StoreGameFragment;
import com.wzf.boardgame.ui.fragments.StorePostFragment;
import com.wzf.boardgame.ui.model.UserInfo;
import com.wzf.boardgame.ui.views.flipview.FlipFragmentView;
import com.wzf.boardgame.ui.views.flipview.FlipFragmentViewSetting;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @Description:
 * @author: wangzhenfei
 * @date: 2017-08-01 17:33
 */

public class StoreActivity extends BaseActivity {
    @Bind(R.id.ffv_star)
    FlipFragmentView ffvStar;
    @Bind(R.id.im_left)
    ImageView imLeft;
    @Bind(R.id.tv_center)
    TextView tvCenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        imLeft.setVisibility(View.VISIBLE);
        tvCenter.setText("我的收藏");
        tvCenter.setVisibility(View.VISIBLE);
        FlipFragmentViewSetting setting = new FlipFragmentViewSetting();
        setting.addPage("帖子", new StorePostFragment());
        setting.addPage("桌游", new StoreGameFragment());
        setting.setBgLineColor(getResources().getColor(R.color.random1));
        setting.setTitleCheckTextColor(getResources().getColor(R.color.colorPrimary));
        setting.setTitleUnCheckTextColor(getResources().getColor(R.color.textHint));
        setting.setFragmentManager(getSupportFragmentManager());
        ffvStar.setViewSettingAndShow(setting);
    }

    @OnClick({R.id.im_left})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.im_left:
                finish();
                break;
        }
    }
}
