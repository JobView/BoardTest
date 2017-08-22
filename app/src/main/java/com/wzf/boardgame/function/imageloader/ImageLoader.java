package com.wzf.boardgame.function.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.wzf.boardgame.MyApplication;
import com.wzf.boardgame.R;
import com.wzf.boardgame.utils.StringUtils;

import java.io.File;

/**
 * Created by zhenfei.wang on 2016/7/20.
 * 图片加载
 * 参考 ：http://www.cnblogs.com/yuzhongzheng/p/5228354.html
 */
public class ImageLoader {
    private static ImageLoader mInstance;

    public void displayLocalImage(String path, ImageView imageView, int defaultId, int radiusDp){
        Glide.with(MyApplication.getAppInstance())
                .load((Uri.fromFile(new File(path))))
                .placeholder(defaultId == 0 ? R.mipmap.image_loading : defaultId)
                .transform(new GlideRoundTransform(MyApplication.getAppInstance(), radiusDp))
                .centerCrop()
                .into(imageView);
    }

    public void displayOnlineImage(String path, ImageView imageView, int defaultId, int radiusDp){
        path = StringUtils.getResourcePath(path);
        Glide.with(MyApplication.getAppInstance())
                .load(path)
                .placeholder(defaultId == 0 ? R.mipmap.image_loading : defaultId)
                .transform(new GlideRoundTransform(MyApplication.getAppInstance(), radiusDp))
                /**
                 * DiskCacheStrategy.NONE 什么都不缓存
                 DiskCacheStrategy.SOURCE 仅仅只缓存原来的全分辨率的图像
                 DiskCacheStrategy.RESULT 仅仅缓存最终的图像，即降低分辨率后的（或者是转换后的）
                 DiskCacheStrategy.ALL 缓存所有版本的图像（默认行为）
                 */
                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                  .centerCrop()
                .into(imageView);
    }

    public void displayTargeSizeImage(String path, ImageView imageView, int w, int h){
        path = StringUtils.getResourcePath(path);
        Glide.with(MyApplication.getAppInstance())
                .load(path)
                .override(w == 0 ? 100 : w, h == 0 ? 100 : w)
                .placeholder( R.mipmap.image_loading)
                /**
                 * DiskCacheStrategy.NONE 什么都不缓存
                 DiskCacheStrategy.SOURCE 仅仅只缓存原来的全分辨率的图像
                 DiskCacheStrategy.RESULT 仅仅缓存最终的图像，即降低分辨率后的（或者是转换后的）
                 DiskCacheStrategy.ALL 缓存所有版本的图像（默认行为）
                 */
                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                  .centerCrop()
                .into(imageView);
    }

    public void displayLocalRoundImage(String path, ImageView imageView){
        Glide.with(MyApplication.getAppInstance())
                .load((Uri.fromFile(new File(path))))
                .placeholder(R.mipmap.icon_default_boy_circle)
                .transform(new GlideCircleTransform(MyApplication.getAppInstance()))
//                        .centerCrop()
                .into(imageView);
    }

    public void displayOnlineRoundImage(String path, ImageView imageView){
        path = StringUtils.getResourcePath(path);
        Glide.with(MyApplication.getAppInstance())
                .load(path)
                .placeholder(R.mipmap.icon_default_boy_circle)
                .transform(new GlideCircleTransform(MyApplication.getAppInstance()))
                /**
                 * DiskCacheStrategy.NONE 什么都不缓存
                 DiskCacheStrategy.SOURCE 仅仅只缓存原来的全分辨率的图像
                 DiskCacheStrategy.RESULT 仅仅缓存最终的图像，即降低分辨率后的（或者是转换后的）
                 DiskCacheStrategy.ALL 缓存所有版本的图像（默认行为）
                 */
                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                            .centerCrop()
                .into(imageView);
    }

    public void urlToBitmap(String url,  final ImageLoaderToBitmapListener listener){
        Glide.with(MyApplication.getAppInstance()).
                load(url)
                .asBitmap()
//                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                if(listener != null){
                    listener.onLoadFinish(resource);
                }

            }
        });
    }

    public void fileToBitmap(String path,int w, int h,  final ImageLoaderToBitmapListener listener){
        Glide.with(MyApplication.getAppInstance()).
                load((Uri.fromFile(new File(path))))
                .asBitmap()
                .override(w, h)
                .centerCrop()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        if(listener != null){
                            listener.onLoadFinish(resource);
                        }

                    }
                });
    }


    /**
     * 需要在子线程执行
     * 阻塞
     * @param context
     * @param url
     * @return
     */
    public static Bitmap load(Context context, String url) {
        try {
            return Glide.with(context)
                    .load(url)
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ImageLoader getInstance() {
        if (mInstance == null) {
            synchronized (ImageLoader.class) {
                if (mInstance == null) {
                    mInstance = new ImageLoader();
                }
            }
        }
        return mInstance;
    }
}
