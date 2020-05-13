package com.mmkj.google.rxbinding.module.base;

import android.os.Bundle;

import com.mmkj.google.rxbinding.module.uiutils.UIDisposable;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


/**
 * @author zhangshuai
 * 活动页基类
 * 初始化复用函数和工具类等
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UIDisposable.register();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UIDisposable.unregister();
    }
}
