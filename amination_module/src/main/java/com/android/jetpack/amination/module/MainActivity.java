package com.android.jetpack.amination.module;

import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.drawable.AnimatedImageDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author zhangshuai
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView tv = findViewById(R.id.tv_info);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimatorSet animatorSet = new AnimatorSet();

                ObjectAnimator animMove = ObjectAnimator.ofFloat(tv, "TranslationX", 100f);
                animMove.setDuration(1000);
                animMove.start();
                animatorSet.play(animMove);

                ObjectAnimator animAlpha = ObjectAnimator.ofFloat(tv, "alpha", 1f, 0f);
                animAlpha.setDuration(1000);
                animAlpha.start();

                animatorSet.play(animAlpha);

                animatorSet.start();
            }
        });

        final ImageButton imageButton=findViewById(R.id.acImgBtn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //方式一
//                ObjectAnimator animX=ObjectAnimator.ofFloat(imageButton,"x",50f);
//                ObjectAnimator animY=ObjectAnimator.ofFloat(imageButton,"y",100f);
//                AnimatorSet animSet=new AnimatorSet();
//                animSet.playTogether(animX,animY);
//                animSet.start();

                //方式二
//                PropertyValuesHolder pvhX=PropertyValuesHolder.ofFloat("x",50f);
//                PropertyValuesHolder pvhY=PropertyValuesHolder.ofFloat("y",100f);
//                ObjectAnimator.ofPropertyValuesHolder(imageButton,pvhX,pvhY).start();

                //方式三
                imageButton.animate().x(50f).y(100f).start();


//                AnimatorSet set= (AnimatorSet) AnimatorInflater.loadAnimator(MainActivity.this,R.animator.property_animator);
//                set.setTarget(imageButton);
//                set.start();

//                ValueAnimator valueAnimator= (ValueAnimator) AnimatorInflater.loadAnimator(MainActivity.this,R.animator.animator_value);
//                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                    @Override
//                    public void onAnimationUpdate(ValueAnimator animation) {
//                        float animValue= (float) animation.getAnimatedValue();
//                        imageButton.setTranslationX(animValue);
//                    }
//                });
//                valueAnimator.start();
            }
        });

        ImageView imageView=findViewById(R.id.acImg_animList);
        imageView.setBackgroundResource(R.drawable.rocket_thrust);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
            final AnimatedImageDrawable imageDrawable= (AnimatedImageDrawable) imageView.getBackground();
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageDrawable.start();
                }
            });
        }
    }

    public class FloatEvaluator implements TypeEvaluator {

        @Override
        public Object evaluate(float fraction, Object startValue, Object endValue) {
            float startFloat = ((Number) startValue).floatValue();
            return startFloat + fraction * (((Number) endValue).floatValue() - startFloat);
        }

    }

}
