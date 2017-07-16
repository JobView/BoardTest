package com.wzf.boardgame.function.imageloader;

import android.app.Activity;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lzy.imagepicker.loader.ImageLoader;
import com.wzf.boardgame.R;

import java.io.File;

/**
 * Created by Administrator on 2016/7/19.
 */
public class ImagePickerImageLoader implements ImageLoader {
    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        if (path != null && imageView != null) {
            if (path.startsWith("http")) {
                Glide.with(activity)
                        .load(path)
                        .placeholder(R.mipmap.image_loading)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imageView);
            } else {
                Glide.with(activity)
                        .load(Uri.fromFile(new File(path)))
                        .placeholder(R.mipmap.image_loading)
//                        .diskCacheStrategy(DiskCacheStrategy.RESULT)
                        .into(imageView);
            }
        }
    }

    @Override
    public void clearMemoryCache() {
    }
}
