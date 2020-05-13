package com.mmkj.google.rxbinding.module.uiutils;

import android.annotation.SuppressLint;
import android.view.View;

import com.jakewharton.rxbinding2.view.RxView;
import com.mmkj.google.rxbinding.module.uiutils.Impls.OnUIClickListener;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * @author zhangshuai
 */
public class UIRxView {

    @SuppressLint("CheckResult")
    public static void click(View v, final OnUIClickListener clickCallback) {
        UIDisposable.addDisposable(
                RxView.clicks(v)
                        .distinct()
                        .throttleFirst(1, TimeUnit.SECONDS)
                        .subscribe(new Consumer<Object>() {
                            @Override
                            public void accept(Object o) throws Exception {
                                clickCallback.onCallback();
                            }
                        })
        );
    }


}
