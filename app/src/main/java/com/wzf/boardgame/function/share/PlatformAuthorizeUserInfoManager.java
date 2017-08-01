package com.wzf.boardgame.function.share;

import android.app.Activity;
import android.widget.Toast;

import com.mob.MobSDK;
import com.wzf.boardgame.utils.DebugLog;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.favorite.WechatFavorite;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * Created by yjin on 2017/6/21.
 */

public class PlatformAuthorizeUserInfoManager {
	private Activity mActivity;
	private MyPlatformActionListener myPlatformActionListener = null;

	public PlatformAuthorizeUserInfoManager(Activity mACt){
		this.mActivity = mACt;
		this.myPlatformActionListener = new MyPlatformActionListener();
	}
	public void WeiXinAuthorize(){
		Platform weiXin = ShareSDK.getPlatform(Wechat.NAME);
		doAuthorize(weiXin);
	}

	public void sinaAuthorize(){
		Platform sina = ShareSDK.getPlatform(SinaWeibo.NAME);
		doAuthorize(sina);
	}

	public void whatMomentsAuthorize(){
		Platform moments = ShareSDK.getPlatform(WechatMoments.NAME);
		doAuthorize(moments);
	}

	public void wechatFavoriteAuthorize(){
		Platform wechatFavorite = ShareSDK.getPlatform(WechatFavorite.NAME);
		doAuthorize(wechatFavorite);
	}

	public void qqShareAuthorize(){
		Platform qqShare = ShareSDK.getPlatform(QQ.NAME);
		doAuthorize(qqShare);
	}


	/**
	 * 授权的代码
	 */
	public void doAuthorize(Platform platform){
		if(platform != null){
			platform.setPlatformActionListener(myPlatformActionListener);
			if(platform.isAuthValid()){
				platform.removeAccount(true);
				return ;
			}
//			platform.SSOSetting(true);
			platform.authorize();
		}
	}

	/**
	 * 授权的代码
	 */
	public void doAuthorize(Platform platform, PlatformActionListener listener){
		if(platform != null){
			platform.setPlatformActionListener(listener);
			platform.removeAccount(true);
			platform.authorize();
		}
	}

	/**
	 * 用户信息的代码
	 */
	public void doUserInfo(Platform platform){
		if(platform != null){
			platform.showUser(null);
		}
	}

	/**
	 * 用户信息的代码
	 */
	public void doUserInfo(Platform platform, PlatformActionListener listener){
		if(platform != null){
			platform.setPlatformActionListener(listener);
			platform.showUser(null);
		}
	}

	/**
	 *
	 * @param platform 平台名称
	 * @param shareType 分享类型
	 */
	/**
	 * 用户信息的代码
	 */
	public void doUserInfo(Platform platform, String account){
		if(platform != null){
			platform.showUser(account);
		}
	}

	/**
	 *
	 * @param platform 平台名称
	 * @param shareType 分享类型
	 */
	/**
	 * 用户信息的代码
	 */
	public void doUserInfo(Platform platform, String account, PlatformActionListener listener){
		if(platform != null){
			platform.setPlatformActionListener(listener);
			platform.showUser(account);
		}
	}

	/**
	 * 回调代码
	 */
	class MyPlatformActionListener implements PlatformActionListener {
		@Override
		public void onComplete(final Platform platform, int i, final HashMap<String, Object> hashMap) {
			mActivity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					DebugLog.i(platform.getDb().exportData());
					Toast.makeText(MobSDK.getContext(),"授权成功", Toast.LENGTH_SHORT).show();
				}
			});
		}

		@Override
		public void onError(Platform platform, int i, Throwable throwable) {
			throwable.printStackTrace();
			System.out.println("=======授权失败======="+((platform != null)?platform.getName():" ")+":"+throwable.toString());
			mActivity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(MobSDK.getContext(),"授权失败", Toast.LENGTH_SHORT).show();
				}
			});
		}

		@Override
		public void onCancel(Platform platform, int i) {
			Toast.makeText(MobSDK.getContext(),"授权取消", Toast.LENGTH_SHORT).show();
		}
	}
}
