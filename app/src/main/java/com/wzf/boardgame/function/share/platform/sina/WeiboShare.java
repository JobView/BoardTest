package com.wzf.boardgame.function.share.platform.sina;

import com.mob.MobSDK;
import com.wzf.boardgame.function.share.DemoUtils;
import com.wzf.boardgame.function.share.ResourcesManager;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;

/**
 * Created by yjin on 2017/6/22.
 */

public class WeiboShare {
	private PlatformActionListener platformActionListener;

	public WeiboShare(PlatformActionListener mListener){
		this.platformActionListener = mListener;
	}

	public void shareText(){
		Platform platform = ShareSDK.getPlatform(SinaWeibo.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		if(!DemoUtils.isValidClientSina("com.sina.weibo")){
			shareParams.setUrl("http://www.mob.com");
		}
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

	public void shareImage(){
		Platform platform = ShareSDK.getPlatform(SinaWeibo.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setImageUrl(ResourcesManager.getInstace(MobSDK.getContext()).getImageUrl());
		shareParams.setImagePath(ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
		if(!DemoUtils.isValidClientSina("com.sina.weibo")){
			shareParams.setUrl("http://www.mob.com");
		}
		shareParams.setShareType(Platform.SHARE_IMAGE);
		shareParams.setLongitude(ResourcesManager.longitude);
		shareParams.setLatitude(ResourcesManager.Latitude);
		platform.setPlatformActionListener(platformActionListener);
		platform.share(shareParams);
	}

	public void shareText(PlatformActionListener mListener){
		Platform platform = ShareSDK.getPlatform(SinaWeibo.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
		if(!DemoUtils.isValidClientSina("com.sina.weibo")){
			shareParams.setUrl(ResourcesManager.getInstace(MobSDK.getContext()).getUrl());
		}
		platform.setPlatformActionListener(mListener);
		platform.share(shareParams);
	}

	public void shareImage(PlatformActionListener mListener){
		Platform platform = ShareSDK.getPlatform(SinaWeibo.NAME);
		Platform.ShareParams shareParams = new  Platform.ShareParams();
		shareParams.setImageUrl(ResourcesManager.getInstace(MobSDK.getContext()).getImageUrl());
		shareParams.setImagePath(ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
		if(!DemoUtils.isValidClientSina("com.sina.weibo")){
			shareParams.setUrl(ResourcesManager.getInstace(MobSDK.getContext()).getUrl());
		}
		shareParams.setLongitude(ResourcesManager.longitude);
		shareParams.setLatitude(ResourcesManager.Latitude);
		platform.setPlatformActionListener(mListener);
		platform.share(shareParams);
	}
}
