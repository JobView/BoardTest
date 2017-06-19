package com.wzf.boardgame.ui.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;


import com.wzf.boardgame.MyApplication;
import com.wzf.boardgame.function.http.NetRequestWaitDialog;
import com.wzf.boardgame.utils.ActivityCollector;
import com.wzf.boardgame.utils.SoftInputUtil;


import butterknife.ButterKnife;

/**
 * Created by zhenfei.wang on 2016/8/8.
 */
public class BaseActivity extends FragmentActivity {
    private final String TAG = getClass().getSimpleName();
    private NetRequestWaitDialog dialog;
    protected boolean hasShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 竖屏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);// 不锁屏
        ActivityCollector.addActivity(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        hasShow = true;
//        ActivityCollector.setCurrentActivity(this);
    }

    /**
     * 弹出吐丝
     * @param text
     */
    Toast toast;
    public void showToast(String text){
        if(toast != null){
            toast.cancel();
        }
        toast = Toast.makeText(MyApplication.getAppInstance(), text, Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * 弹出吐丝
     * @param s
     */
    public void showDialog(String s){
        if (dialog == null) {
            dialog = new NetRequestWaitDialog(this, s);
        }
        dialog.setMessage(s);
        if (dialog.isShowing()) {
            return;
        }
        if(!this.isFinishing()){
            dialog.show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        dissMissDialog();
        if(toast != null){
            toast.cancel();
        }
        dialog = null;
    }
    public void dissMissDialog(){
        if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }
    }

    @Override
    public void finish() {
        SoftInputUtil.closeSoftInput(this);
        super.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
        ButterKnife.unbind(this);
    }
}
