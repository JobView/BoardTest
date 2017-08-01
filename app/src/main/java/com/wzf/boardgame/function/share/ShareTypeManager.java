package com.wzf.boardgame.function.share;

import android.util.Log;
import android.widget.Toast;

import com.mob.MobSDK;
import com.wzf.boardgame.ui.base.BaseActivity;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

/**
 * Created by yjin on 2017/5/17.
 * 分享的操作类，各个平台的分享代码写在这里。
 * 这里可以直接拷贝代码，粘贴到合适的位置。
 */

public class ShareTypeManager {
	private BaseActivity mContext = null;
	private Platform mPlatform = null;
	private MyPlatformActionListener myPlatformActionListener = null;

	public ShareTypeManager(BaseActivity context, Platform platform){
		this.mContext = context;
		this.mPlatform = platform;
		myPlatformActionListener = new MyPlatformActionListener();
	}

	public void shareShow(int platform){
		switch (platform){
			case Platform.SHARE_TEXT:
				shareText();
				break;
			case Platform.SHARE_VIDEO:
				shareVideo();
				break;
			case Platform.SHARE_IMAGE:
				shareImage();
				break;
			case Platform.SHARE_APPS:
				shareApp();
				break;
			case Platform.SHARE_FILE:
				shareFiles();
				break;
			case Platform.SHARE_EMOJI:
			case Platform.SHARE_WEBPAGE:
				shareWebPage();
				break;
			case Platform.SHARE_MUSIC:
				shareMusic();
				break;
		}
	}

	public void shareText() {
		PlatformShareManager platformShareManager = new PlatformShareManager();
		platformShareManager.setPlatformActionListener(myPlatformActionListener);
		platformShareManager.shareText(mPlatform.getName());
	}

	public void shareVideo() {
		PlatformShareManager platformShareManager = new PlatformShareManager();
		platformShareManager.setPlatformActionListener(myPlatformActionListener);
		platformShareManager.shareVideo(mPlatform.getName());
	}

	public void shareImage() {
		PlatformShareManager platformShareManager = new PlatformShareManager();
		platformShareManager.setPlatformActionListener(myPlatformActionListener);
		platformShareManager.shareImage(mPlatform.getName());
	}

	public void shareApp() {
		PlatformShareManager platformShareManager = new PlatformShareManager();
		platformShareManager.setPlatformActionListener(myPlatformActionListener);
		platformShareManager.shareApp(mPlatform.getName());
	}

	public void shareFiles() {
		PlatformShareManager platformShareManager = new PlatformShareManager();
		platformShareManager.setPlatformActionListener(myPlatformActionListener);
		platformShareManager.shareFile(mPlatform.getName());
	}



	public void shareWebPage() {
		PlatformShareManager platformShareManager = new PlatformShareManager();
		platformShareManager.setPlatformActionListener(myPlatformActionListener);
		platformShareManager.shareWebPager(mPlatform.getName());
	}

	public void shareMusic() {
		PlatformShareManager platformShareManager = new PlatformShareManager();
		platformShareManager.setPlatformActionListener(myPlatformActionListener);
		platformShareManager.shareMusic(mPlatform.getName());
	}

	  class MyPlatformActionListener implements PlatformActionListener {
		@Override
		public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
			mContext.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(MobSDK.getContext(),"分享成功", Toast.LENGTH_SHORT).show();
				}
			});
		}

		@Override
		public void onError(Platform platform, int i, Throwable throwable) {
			throwable.printStackTrace();
			Log.d(platform.getName(),"platform===type:"+i+"throwable:"+throwable);
			final String error = throwable.toString();
			System.out.println("=======分享失败======="+((platform != null)?platform.getName():" ")+":"+throwable.toString());
			mContext.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(MobSDK.getContext(),"分享失败"+error, Toast.LENGTH_SHORT).show();
				}
			});
		}

		@Override
		public void onCancel(Platform platform, int i) {
			Toast.makeText(MobSDK.getContext(),"取消分享", Toast.LENGTH_SHORT).show();
		}
	}

}
