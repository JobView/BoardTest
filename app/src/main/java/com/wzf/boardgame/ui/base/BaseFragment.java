package com.wzf.boardgame.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by zhenfei.wang on 2016/9/9.
 */
public class BaseFragment extends Fragment {
    protected BaseActivity bActivity;
    public boolean hidden;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        bActivity = (BaseActivity) context;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        this.hidden = hidden;
        if(hidden){
            onPause();
        }else {
            onResume();
        }
    }

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(bActivity == null){
            bActivity = (BaseActivity) getActivity();
        }
    }
}
