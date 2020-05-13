package com.android.jetpack.camerax;

import android.Manifest;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Environment;
import android.util.Rational;
import android.util.Size;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraX;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageAnalysisConfig;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureConfig;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.core.PreviewConfig;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author zhangshuai
 */
public class MainActivity extends AppCompatActivity implements LifecycleOwner {

    private RxPermissions permissions;
    private TextureView mTextureView;
    private ImageCapture imageCapture;

    String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/TestCameraX/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //无title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        permissions = new RxPermissions(this);

        findViewById(R.id.acBtn_openCamera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageCapture.takePicture(new File(path + System.currentTimeMillis() + ".jpg"), new ImageCapture.OnImageSavedListener() {
                    @Override
                    public void onImageSaved(@NonNull File file) {
                        Toast.makeText(MainActivity.this, "拍摄成功：" + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@NonNull ImageCapture.UseCaseError useCaseError, @NonNull String message, @Nullable Throwable cause) {
                        Toast.makeText(MainActivity.this, "拍摄成功：" + message, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        mTextureView = findViewById(R.id.textureView);

    }

    @Override
    protected void onStart() {
        super.onStart();
        checkPermissions();
    }

    private void checkPermissions() {
        permissions.request(Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            demo();
                        } else {
                            Toast.makeText(MainActivity.this, "授权失败!!!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    /**
     * 入门
     */
    void demo() {

        CameraX.unbindAll();
        Rational rational = new Rational(mTextureView.getWidth(), mTextureView.getHeight());
        Size size = new Size(mTextureView.getWidth(), mTextureView.getHeight());

        PreviewConfig config = new PreviewConfig.Builder()
                .setTargetAspectRatio(rational)
                .setTargetResolution(size)
                .setTargetRotation(getWindowManager().getDefaultDisplay().getRotation()).build();

        Preview preview = new Preview(config);

        preview.setOnPreviewOutputUpdateListener(new Preview.OnPreviewOutputUpdateListener() {
            @Override
            public void onUpdated(Preview.PreviewOutput output) {
                ViewGroup parent = (ViewGroup) mTextureView.getParent();
                parent.removeView(mTextureView);
                parent.addView(mTextureView, 0);

                mTextureView.setSurfaceTexture(output.getSurfaceTexture());

                //updateTransform
                Matrix matrix = new Matrix();
                float centerX = mTextureView.getWidth() / 2f;
                float centerY = mTextureView.getHeight() / 2f;

                int degrees = (int) mTextureView.getRotation();
                switch (mTextureView.getDisplay().getRotation()) {
                    case Surface.ROTATION_0: {
                        degrees = 0;
                        break;
                    }
                    case Surface.ROTATION_90: {
                        degrees = 90;
                        break;
                    }
                    case Surface.ROTATION_180: {
                        degrees = 180;
                        break;
                    }
                    case Surface.ROTATION_270: {
                        degrees = 270;
                        break;
                    }
                    default:
                        break;
                }

                matrix.postRotate((float) degrees, centerX, centerY);
                mTextureView.setTransform(matrix);

            }
        });

        ImageCaptureConfig imageCaptureConfig = new ImageCaptureConfig.Builder()
                .setTargetAspectRatio(rational)
                .setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY)
                .setTargetRotation(getWindowManager().getDefaultDisplay().getRotation())
                .build();

        imageCapture = new ImageCapture(imageCaptureConfig);

        CameraX.bindToLifecycle(this, preview, imageCapture);

    }

    /**
     * 自定义
     */
    class CustomLifecycle implements LifecycleOwner {

        private LifecycleRegistry mLifecycleRegistry;

        public CustomLifecycle() {
            mLifecycleRegistry = new LifecycleRegistry(this);
            mLifecycleRegistry.markState(Lifecycle.State.CREATED);
        }

        void doOnResume() {
            mLifecycleRegistry.markState(Lifecycle.State.RESUMED);
        }

        void doOnStart() {
            mLifecycleRegistry.markState(Lifecycle.State.STARTED);
        }

        void doOnDestroy() {
            mLifecycleRegistry.markState(Lifecycle.State.DESTROYED);
        }

        @NonNull
        @Override
        public Lifecycle getLifecycle() {
            return mLifecycleRegistry;
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        CameraX.unbindAll();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
