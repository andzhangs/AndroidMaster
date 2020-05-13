package com.android.jetpack.zs;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Maybe;
import io.reactivex.MaybeEmitter;
import io.reactivex.MaybeObserver;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleObserver;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.internal.schedulers.RxThreadFactory;
import io.reactivex.plugins.RxJavaPlugins;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;

/**
 * @author zhangshuai
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MMainActivity";


    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    boolean isFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        Button tv = findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                single();
//                CompletableMethod();
//                MaybeMethod();
//                isFlag = !isFlag;

                rxJavaPluginMethod();
            }
        });


//        RxAndroidPlugins
//        RxThreadFactory
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    /**
     * 只发射一条单一的数据，或者一条异常通知，不能发射完成通知，其中数据与通知只能发射一个
     */
    public void single() {
        Single.create(new SingleOnSubscribe<String>() {
            @Override
            public void subscribe(SingleEmitter<String> emitter) throws Exception {
                if (isFlag) {
                    emitter.onSuccess("Single：发送通知成功!");
                } else {
                    emitter.onError(new NullPointerException("Single：老子就是烦空了!"));
                }

            }
        }).subscribe(new SingleObserver<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                if (d.isDisposed()) {

                }
            }

            @Override
            public void onSuccess(String s) {
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 只发射一条完成通知，或者一条异常通知，不能发射数据，
     * 其中完成通知与异常通知只能发射一个
     */
    private void CompletableMethod() {
        Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter emitter) throws Exception {
                if (isFlag) {
                    emitter.onComplete();
                } else {
                    emitter.onError(new NullPointerException("Completable：接收错误失败!"));
                }
            }
        }).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onComplete() {
                Toast.makeText(MainActivity.this, "Completable：接收通知成功!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 可发射一条单一的数据，以及发射一条完成通知，或者一条异常通知，
     * 其中完成通知和异常通知只能发射一个，发射数据只能在发射完成通知或者异常通知之前，
     * 否则发射数据无效。
     */
    private void MaybeMethod() {
        Maybe.create(new MaybeOnSubscribe<String>() {
            @Override
            public void subscribe(MaybeEmitter<String> emitter) throws Exception {
                if (isFlag) {
                    emitter.onSuccess("Maybe：发送通知成功！");
                } else {
                    emitter.onError(new NullPointerException("Maybe：发送通知失败!!!"));
                }
            }
        }).subscribe(new MaybeObserver<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(String s) {
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                if (BuildConfig.DEBUG) {
                    Log.i(TAG, "onSuccess: ");
                }
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onComplete() {
                Toast.makeText(MainActivity.this, "完成", Toast.LENGTH_SHORT).show();
                if (BuildConfig.DEBUG) {

                    boolean str = "哈哈哈哈".isEmpty();
                    boolean str2 = TextUtils.isEmpty("hahah");
                    Log.i(TAG, "onComplete: " + str + "\t\t" + str2);
                }
            }
        });

    }

    private void rxJavaPluginMethod(){
        RxJavaPlugins.setOnMaybeSubscribe(new BiFunction<Maybe, MaybeObserver, MaybeObserver>() {
            @Override
            public MaybeObserver apply(Maybe maybe, MaybeObserver maybeObserver) throws Exception {
                return new WrapDownStreamObserver(maybeObserver);
            }
        });
    }

    class WrapDownStreamObserver<String> implements MaybeObserver<String>{

        private MaybeObserver<String> mMaybeObserver;

        public WrapDownStreamObserver(MaybeObserver<String> maybeObserver) {
            mMaybeObserver = maybeObserver;
        }

        @Override
        public void onSubscribe(Disposable d) {
            mMaybeObserver.onSubscribe(d);
            if (BuildConfig.DEBUG) {
                Log.i(TAG, "onSubscribe: ");
            }
        }

        @Override
        public void onSuccess(String str) {
            mMaybeObserver.onSuccess(str);
            if (BuildConfig.DEBUG) {
                Log.i(TAG, "onSuccess: "+str);
            }
        }

        @Override
        public void onError(Throwable e) {
            mMaybeObserver.onError(e);
            if (BuildConfig.DEBUG) {
                Log.i(TAG, "onError: "+e.getMessage());
            }
        }

        @Override
        public void onComplete() {
            mMaybeObserver.onComplete();
            if (BuildConfig.DEBUG) {
                Log.i(TAG, "onComplete: ");
            }
        }
    }

}
