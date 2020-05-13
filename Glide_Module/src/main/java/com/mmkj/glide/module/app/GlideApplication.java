package com.mmkj.glide.module.app;

import android.annotation.SuppressLint;
import android.app.Application;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;


/**
 * @author zhangshuai
 */
public class GlideApplication extends Application {

    private static GlideApplication sApplication;

    public static synchronized GlideApplication getApplication() {
        return sApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
    }


//    public static void loadView(@NonNull ImageView img,@NonNull String path) {
//        GlideApp.with(img)
//                .load(path)
//                .placeholder(R.drawable.img_zhaosi)
//                .fitCenter()
//                .into(img);
//    }
}
