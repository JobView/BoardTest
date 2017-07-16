package com.wzf.boardgame.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;

import java.util.ArrayList;

/**
 * Created by zhenfei.wang on 2016/7/19.
 * 对ImagePicker的浏览大图修改，动态显示是否删除删除按钮
 */
public class MImagePreviewDelActivity extends ImagePreviewDelActivity {
    public static String SHOW_DELETE_BUTTON = "SHOW_DELETE_BUTTON";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ActivityCollector.addActivity(this);
        ImageView mBtnDel = (ImageView) findViewById(com.lzy.imagepicker.R.id.btn_del);
        if(getIntent().getBooleanExtra(SHOW_DELETE_BUTTON, false)){
            mBtnDel.setVisibility(View.VISIBLE);
        }else {
            mBtnDel.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        ActivityCollector.removeActivity(this);
    }

    /** 单击时，隐藏头和尾 */
    @Override
    public void onImageSingleTap() {
        if (topBar.getVisibility() == View.VISIBLE) {
//            topBar.setAnimation(AnimationUtils.loadAnimation(this, com.lzy.imagepicker.R.anim.top_out));
//            topBar.setVisibility(View.GONE);
//            tintManager.setStatusBarTintResource(com.lzy.imagepicker.R.color.transparent);//通知栏所需颜色
//            //给最外层布局加上这个属性表示，Activity全屏显示，且状态栏被隐藏覆盖掉。
//            if (Build.VERSION.SDK_INT >= 16) content.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
            finish();
        } else {
            topBar.setAnimation(AnimationUtils.loadAnimation(this, com.lzy.imagepicker.R.anim.top_in));
            topBar.setVisibility(View.VISIBLE);
            tintManager.setStatusBarTintResource(com.lzy.imagepicker.R.color.status_bar);//通知栏所需颜色
            //Activity全屏显示，但状态栏不会被隐藏覆盖，状态栏依然可见，Activity顶端布局部分会被状态遮住
            if (Build.VERSION.SDK_INT >= 16) content.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }


    /**
     * 启动方法
     * @param activity
     * @param data   列表图片数据
     * @param viewPosition  起始浏览位置
     * @param showDeleteButton  是否能够删除图片
     * @param requestCode  请求码
     */
    public static void startMethod(Activity activity, ArrayList<ImageItem> data,
                                   int viewPosition,
                                   boolean showDeleteButton,
                                   int requestCode){
        Intent intent = new Intent(activity, MImagePreviewDelActivity.class);
        intent.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, data);
        intent.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, viewPosition);
        intent.putExtra(ImagePicker.EXTRA_FROM_ITEMS, true);
        intent.putExtra(MImagePreviewDelActivity.SHOW_DELETE_BUTTON, showDeleteButton);
        activity.startActivityForResult(intent, requestCode);

    }
}
