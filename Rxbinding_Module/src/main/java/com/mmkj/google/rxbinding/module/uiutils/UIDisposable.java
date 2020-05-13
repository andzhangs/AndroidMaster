package com.mmkj.google.rxbinding.module.uiutils;

import android.annotation.SuppressLint;
import android.util.Log;

import com.mmkj.google.rxbinding.module.BuildConfig;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 创建和回收Disposable
 * 推荐在项目Activity的基类中注册、解绑
 * @author zhangshuai
 */
public class UIDisposable {

    private static final String TAG = "MUIDisposable";

    private static CompositeDisposable mDisposable;

    /**
     * 创建回收容器
     */
    public static void register() {
        if (mDisposable == null) {
            mDisposable = new CompositeDisposable();
            logInfo("register");
        }
    }

    /**
     * 添加实例化的Disposable到容器中
     * @param disposable 目标对象
     */
    public static void addDisposable(Disposable disposable) {
        register();
        mDisposable.add(disposable);
        logInfo("addDisposable");
    }

    /**
     * 清空容器中的所有Disposable实例
     */
    public static void unregister() {
        if (mDisposable != null) {
            logInfo("unregister");
            mDisposable.clear();
        }
    }

    static void logInfo(String methodName){
        if (BuildConfig.DEBUG) {
            Log.i(TAG, "log: "+methodName);
        }
    }

}
