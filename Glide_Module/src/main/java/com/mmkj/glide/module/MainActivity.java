package com.mmkj.glide.module;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapThumbnailImageViewTarget;
import com.bumptech.glide.request.target.CustomViewTarget;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.mmkj.glide.module.app.GlideApplication;
import com.mmkj.glide.module.databinding.ActivityMainBinding;

import java.net.URL;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.DataBindingUtil;

/**
 * @author zhangshuai
 */
public class MainActivity extends AppCompatActivity {

    private String IMG_PAHT = "http://x.annihil.us/u/prod/marvel/i/mg/5/a0/526033a73dca3/standard_xlarge.jpg";
    private Uri uri = Uri.parse(IMG_PAHT);
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setImageUrl("");
        binding.acImgGlideView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.setImageUrl(IMG_PAHT);
            }
        });
    }
}
