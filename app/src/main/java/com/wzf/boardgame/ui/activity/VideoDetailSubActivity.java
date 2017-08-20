package com.wzf.boardgame.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wzf.boardgame.R;
import com.wzf.boardgame.ui.base.BaseActivity;
import com.wzf.boardgame.ui.base.BaseFragment;
import com.wzf.boardgame.ui.fragments.GameContentFragment;
import com.wzf.boardgame.ui.fragments.GameVideoListFragment;
import com.wzf.boardgame.ui.views.NestRadioGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wzf on 2017/8/19.
 */

public class VideoDetailSubActivity extends BaseActivity {
    public static final int EXTRA_VALUE_0 = 0; //  社区fragment
    public static final int EXTRA_VALUE_1 = 1; // 游戏fragment
    public static final int EXTRA_VALUE_2 = 2; // 个人fragment
    BaseFragment videoFragment;
    BaseFragment ruleFragment;
    BaseFragment extendFragment;
    private Fragment[] fragments;
    private int index;
    private int currentTabIndex;// 当前fragment的index

    @Bind(R.id.im_left)
    ImageView imLeft;
    @Bind(R.id.tv_center)
    TextView tvCenter;
    @Bind(R.id.main_radio)
    NestRadioGroup mainRadio;

    private String boardId;
    private int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_sub);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        boardId = intent.getStringExtra("boardId");
        imLeft.setVisibility(View.VISIBLE);
        tvCenter.setText(intent.getStringExtra("name"));
        tvCenter.setVisibility(View.VISIBLE);
        position = intent.getIntExtra("position", 0);
//        getSupportFragmentManager().beginTransaction().add(R.id.fm_container,  GameVideoListFragment.getInstance(boardId)).commit();
        initFragment();
    }

    private void initFragment() {
        videoFragment =  GameVideoListFragment.getInstance(boardId);
        ruleFragment = GameContentFragment.getInstance(boardId, 1);
        extendFragment = GameContentFragment.getInstance(boardId, 2);
        fragments = new Fragment[]{videoFragment, ruleFragment, extendFragment};

        switch (position){
            case 0:
                mainRadio.check(R.id.radio_video);
                currentTabIndex = EXTRA_VALUE_0;
                //默认加载
                getSupportFragmentManager().beginTransaction().add(R.id.fm_container, videoFragment).commit();
                break;
            case 1:
                mainRadio.check(R.id.radio_rule);
                currentTabIndex = EXTRA_VALUE_1;
                //默认加载
                getSupportFragmentManager().beginTransaction().add(R.id.fm_container, ruleFragment).commit();
                break;
            case 2:
                mainRadio.check(R.id.radio_extent);
                currentTabIndex = EXTRA_VALUE_2;
                //默认加载
                getSupportFragmentManager().beginTransaction().add(R.id.fm_container, extendFragment).commit();
                break;
        }


        mainRadio.setOnCheckedChangeListener(new NestRadioGroup.OnSelectChangeListener() {
            @Override
            public void onCheckedChanged(NestRadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_video:
                        index = EXTRA_VALUE_0;
                        break;
                    case R.id.radio_rule:
                        index = EXTRA_VALUE_1;
                        break;
                    case R.id.radio_extent:
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

    @OnClick({R.id.im_left, R.id.im_right1})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.im_left:
                finish();
                break;
        }
    }

    public static void startMethod(Context context, String boardId, String name,  int position){
        context.startActivity(new Intent(context, VideoDetailSubActivity.class)
                .putExtra("boardId", boardId)
                .putExtra("name", name)
                .putExtra("position", position));
    }
}
