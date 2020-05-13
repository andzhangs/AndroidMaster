package com.android.jetpack.amination.module;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.core.widget.NestedScrollView;

import android.animation.Animator;
import android.os.Bundle;
import android.view.View;

public class AnimShowAndHideViewActivity extends AppCompatActivity {

    private NestedScrollView mNestedScrollView;
    private ContentLoadingProgressBar mProgressBar;
    private int shortAnimationDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim_show_and_hide_view);

        mNestedScrollView = findViewById(R.id.nScrollView);
        mProgressBar = findViewById(R.id.clProgressBar);

        mNestedScrollView.setVisibility(View.GONE);

        shortAnimationDuration = getResources().getInteger(android.R.integer.config_shortAnimTime);
        crossFade();
    }

    void crossFade() {
        mNestedScrollView.setAlpha(0f);
        mNestedScrollView.setVisibility(View.VISIBLE);
        mNestedScrollView.animate().alpha(1f).setDuration(shortAnimationDuration).setListener(null);

        mProgressBar.animate().alpha(0f).setDuration(shortAnimationDuration).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

}
