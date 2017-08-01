package com.wzf.boardgame.function.share;

/**
 * Created by yjin on 2017/6/19.
 */

import com.mob.MobSDK;
import com.wzf.boardgame.function.share.platform.sina.WeiboShare;
import com.wzf.boardgame.function.share.platform.tencent.qq.QQShare;
import com.wzf.boardgame.function.share.platform.wechat.favorite.WechatFavoriteShare;
import com.wzf.boardgame.function.share.platform.wechat.friends.WechatShare;
import com.wzf.boardgame.function.share.platform.wechat.moments.WechatMomentsShare;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.favorite.WechatFavorite;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * 获取各个平台后进行分享的操作。
 */

public class PlatformShareManager {
	private PlatformActionListener platformActionListener;

	public void setPlatformActionListener(PlatformActionListener platformActionListener){
		this.platformActionListener = platformActionListener;
	}

	public void shareText(String name){
		if(name.equals(SinaWeibo.NAME)){
			WeiboShare weiboShare = new WeiboShare(platformActionListener);
			weiboShare.shareText();
		}else if(name.equals(Wechat.NAME)){
			WechatShare wechatShare = new WechatShare(platformActionListener);
			wechatShare.shareText();
		}else if(name.equals(WechatFavorite.NAME)){
			WechatFavoriteShare wechatFavoriteShare = new WechatFavoriteShare(platformActionListener);
			wechatFavoriteShare.shareText();
		}else if(name.equals(WechatMoments.NAME)){
			WechatMomentsShare wechatMomentsShare = new WechatMomentsShare(platformActionListener);
			wechatMomentsShare.shareText();
		} else {
			Platform platform = ShareSDK.getPlatform(name);
			Platform.ShareParams shareParams = new  Platform.ShareParams();
			shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
			shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
			shareParams.setShareType(Platform.SHARE_TEXT);
			platform.setPlatformActionListener(platformActionListener);
			platform.share(shareParams);
		}
	}

	public void shareImage(String name){
		if(name.equals(SinaWeibo.NAME)){
			WeiboShare weiboShare = new WeiboShare(platformActionListener);
			weiboShare.shareImage();
		}else  if(name.equals(QQ.NAME)){
			QQShare qqShare = new QQShare(platformActionListener);
			qqShare.shareImage();
		}else if(name.equals(Wechat.NAME)){
			WechatShare wechatShare = new WechatShare(platformActionListener);
			wechatShare.shareImage();
		}else if(name.equals(WechatFavorite.NAME)){
			WechatFavoriteShare wechatFavoriteShare = new WechatFavoriteShare(platformActionListener);
			wechatFavoriteShare.shareImage();
		}else if(name.equals(WechatMoments.NAME)){
			WechatMomentsShare wechatMomentsShare = new WechatMomentsShare(platformActionListener);
			wechatMomentsShare.shareImage();
		}else{
			Platform platform = ShareSDK.getPlatform(name);
			Platform.ShareParams shareParams = new  Platform.ShareParams();
			shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
			shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
			shareParams.setImageUrl(ResourcesManager.getInstace(MobSDK.getContext()).getImageUrl());
			shareParams.setImagePath(ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
			shareParams.setImageData(ResourcesManager.getInstace(MobSDK.getContext()).getImageBmp());
			shareParams.setShareType(Platform.SHARE_IMAGE);
			platform.setPlatformActionListener(platformActionListener);
			platform.share(shareParams);
		}
	}

	public void shareWebPager(String name){
		if(name.equals(QQ.NAME)){
			QQShare qqShare = new QQShare(platformActionListener);
			qqShare.shareWebPager();
		}else if(name.equals(Wechat.NAME)){
			WechatShare wechatShare = new WechatShare(platformActionListener);
			wechatShare.shareWebpager();
		} else if(name.equals(WechatMoments.NAME)){
			WechatMomentsShare wechatShare = new WechatMomentsShare(platformActionListener);
			wechatShare.shareWebpager();
		}  else{
			Platform platform = ShareSDK.getPlatform(name);
			Platform.ShareParams shareParams = new  Platform.ShareParams();
			shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
			shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
			shareParams.setImageUrl(ResourcesManager.getInstace(MobSDK.getContext()).getImageUrl());
			shareParams.setUrl(ResourcesManager.getInstace(MobSDK.getContext()).getUrl());
			shareParams.setShareType(Platform.SHARE_WEBPAGE);
			platform.setPlatformActionListener(platformActionListener);
			platform.share(shareParams);
		}
	}

	public void shareVideo(String name){
		if(name.equals(Wechat.NAME)){
			WechatShare wechatShare = new WechatShare(platformActionListener);
			wechatShare.shareVideo();
		}else if(name.equals(WechatFavorite.NAME)){
			WechatFavoriteShare wechatFavoriteShare = new WechatFavoriteShare(platformActionListener);
			wechatFavoriteShare.shareVideo();
		}else if(name.equals(WechatMoments.NAME)){
			WechatMomentsShare wechatMomentsShare = new WechatMomentsShare(platformActionListener);
			wechatMomentsShare.shareVideo();
		}else {
			Platform platform = ShareSDK.getPlatform(name);
			Platform.ShareParams shareParams = new  Platform.ShareParams();
			shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
			shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
			shareParams.setFilePath(ResourcesManager.getInstace(MobSDK.getContext()).getFilePath());
			shareParams.setShareType(Platform.SHARE_VIDEO);
			platform.setPlatformActionListener(platformActionListener);
			platform.share(shareParams);
		}
	}

	public void shareFile(String name){
		if(name.equals(Wechat.NAME)){
			WechatShare wechatShare = new WechatShare(platformActionListener);
			wechatShare.shareFile();
		}else if(name.equals(WechatFavorite.NAME)){
			WechatFavoriteShare wechatFavoriteShare = new WechatFavoriteShare(platformActionListener);
			wechatFavoriteShare.shareFile();
		} else {
			Platform platform = ShareSDK.getPlatform(name);
			Platform.ShareParams shareParams = new  Platform.ShareParams();
			shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
			shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
			shareParams.setFilePath(ResourcesManager.getInstace(MobSDK.getContext()).getFilePath());
			shareParams.setShareType(Platform.SHARE_FILE);
			platform.setPlatformActionListener(platformActionListener);
			platform.share(shareParams);
		}
	}

	public void shareMusic(String name){
		if(name.equals(QQ.NAME)){
			QQShare qqShare = new QQShare(platformActionListener);
			qqShare.shareMusic();
		}else if(name.equals(Wechat.NAME)){
			WechatShare wechatShare = new WechatShare(platformActionListener);
			wechatShare.shareMusic();
		}else if(name.equals(WechatFavorite.NAME)){
			WechatFavoriteShare wechatFavoriteShare = new WechatFavoriteShare(platformActionListener);
			wechatFavoriteShare.shareMusic();
		}else if(name.equals(WechatMoments.NAME)){
			WechatMomentsShare wechatMomentsShare = new WechatMomentsShare(platformActionListener);
			wechatMomentsShare.shareMusic();
		}else {
			 Platform platform = ShareSDK.getPlatform(name);
			 Platform.ShareParams shareParams = new  Platform.ShareParams();
			 shareParams.setText(ResourcesManager.getInstace(MobSDK.getContext()).getText());
			 shareParams.setTitle(ResourcesManager.getInstace(MobSDK.getContext()).getTitle());
			 shareParams.setImageUrl(ResourcesManager.getInstace(MobSDK.getContext()).getImageUrl());
			 shareParams.setImagePath(ResourcesManager.getInstace(MobSDK.getContext()).getImagePath());
			 shareParams.setImageData(ResourcesManager.getInstace(MobSDK.getContext()).getImageBmp());
			 shareParams.setMusicUrl(ResourcesManager.getInstace(MobSDK.getContext()).getMusicUrl());
			 shareParams.setShareType(Platform.SHARE_MUSIC);
			 platform.setPlatformActionListener(platformActionListener);
			 platform.share(shareParams);
		 }
	}

	public void shareApp(String name){
		 if(name.equals(Wechat.NAME)){
			WechatShare wechatShare = new WechatShare(platformActionListener);
			//wechatShare.shareApps();
		}
	}
}
