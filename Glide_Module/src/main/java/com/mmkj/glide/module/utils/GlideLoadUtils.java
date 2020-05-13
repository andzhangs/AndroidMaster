package com.mmkj.glide.module.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomViewTarget;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.mmkj.glide.module.R;
import com.mmkj.glide.module.app.GlideApplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.BindingAdapter;
import androidx.databinding.ViewDataBinding;

/**
 * @author zhangshuai
 */
public class GlideLoadUtils {


    @BindingAdapter("bind:src")
    public static void loadNormal(ImageView view, String path) {
        Glide.with(view)
                .load(path)
//                .centerCrop()
                .thumbnail(Glide.with(view).load(R.drawable.img_zhaosi))
                .apply(RequestOptions.centerInsideTransform())
//                .placeholder(R.drawable.img_zhaosi)
                .fitCenter()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(view);
    }

//    Bitmap 和 Drawable 加载到 View 之外，你也可以开始异步加载到你的自定义 Target

    /**
     * 方式一
     *
     * @param view
     * @param path
     */
//    @BindingAdapter("app:src")
    public static void loadDrawableImageViewTarget(ImageView view, String path) {
        Glide.with(view)
                .load(path)
                .into(new DrawableImageViewTarget(view));
    }

    /**
     * 方式二
     *
     * @param view
     * @param path
     */
//    @BindingAdapter("app:src")
    public static void loadImageViewTarget(ImageView view, String path) {
        Glide.with(view)
                .load(path)
                .into(new ImageViewTarget<Drawable>(view) {
                    @Override
                    protected void setResource(@Nullable Drawable resource) {
                        view.setImageDrawable(resource);
                    }
                });
    }

    /**
     * 方式三
     *
     * @param view
     * @param path
     */
//    @BindingAdapter("app:src")
    public static void loadCustomViewTarget(AppCompatImageView view, String path) {
        Glide.with(view)
                .load(path)
                .into(new CustomViewTarget<ImageView, Drawable>(view) {
                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {

                    }

                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        view.setImageDrawable(resource);
                    }

                    @Override
                    protected void onResourceCleared(@Nullable Drawable placeholder) {

                    }
                });
    }

    /**
     * 后台线程
     */
    public static void loadFutureTarget(ImageView view, String path) {
//        FutureTarget<Bitmap> futureTarget=Glide.with(view).load(path).submit(200,200);
//        Bitmap bitmap=futureTarget.get();
//        Glide.with(view).clear(futureTarget);
    }
}
