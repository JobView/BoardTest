package com.wzf.boardgame.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.View;

import com.wzf.boardgame.R;
import com.wzf.boardgame.ui.base.BaseActivity;
import com.wzf.boardgame.ui.fragments.CommunityFragment;
import com.wzf.boardgame.ui.fragments.GameFragment;
import com.wzf.boardgame.ui.fragments.MeFragment;
import com.wzf.boardgame.ui.views.NestRadioGroup;
import com.wzf.boardgame.ui.views.RedPoint;
import com.wzf.boardgame.utils.AppDeviceInfo;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wzf on 2017/7/5.
 */

public class MenuActivity extends BaseActivity {
    public static final int EXTRA_VALUE_0 = 0; //  社区fragment
    public static final int EXTRA_VALUE_1 = 1; // 游戏fragment
    public static final int EXTRA_VALUE_2 = 2; // 个人fragment
    CommunityFragment communityFragment;
    GameFragment gameFragment;
    MeFragment meFragment;
    private Fragment[] fragments;
    private int index;
    private int currentTabIndex;// 当前fragment的index

    @Bind(R.id.redpoint_game)
    RedPoint redpointGame;
    @Bind(R.id.redpoint_me)
    RedPoint redpointMe;
    @Bind(R.id.main_radio)
    NestRadioGroup mainRadio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);
        initFragment();
    }

    private void initFragment() {
        communityFragment = new CommunityFragment();
        gameFragment = new GameFragment();
        meFragment = new MeFragment();
        fragments = new Fragment[]{communityFragment, gameFragment, meFragment};
        //默认加载
        getSupportFragmentManager().beginTransaction().add(R.id.fm_container, communityFragment).commit();
        mainRadio.check(R.id.radio_community);
        currentTabIndex = EXTRA_VALUE_0;

        mainRadio.setOnCheckedChangeListener(new NestRadioGroup.OnSelectChangeListener() {
            @Override
            public void onCheckedChanged(NestRadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_community:
                        index = EXTRA_VALUE_0;
                        break;
                    case R.id.radio_game:
                        index = EXTRA_VALUE_1;
                        break;
                    case R.id.radio_me:
                        index = EXTRA_VALUE_2;
                        break;
                }

                if (currentTabIndex != index) {
                    FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
                    trx.hide(fragments[currentTabIndex]);
                    if (!fragments[index].isAdded()) {
                        trx.add(R.id.fm_container, fragments[index]);
                    }
                    trx.show(fragments[index]).commit();
                }
                currentTabIndex = index;
            }
        });
    }

    @Override
    public void onBackPressed() {
        AppDeviceInfo.goHome(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (index) {
            case EXTRA_VALUE_0:
                communityFragment.onActivityResult(requestCode, resultCode, data);
                break;
            case EXTRA_VALUE_1:
                gameFragment.onActivityResult(requestCode, resultCode, data);
                break;
            case EXTRA_VALUE_2:
                meFragment.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }
}
