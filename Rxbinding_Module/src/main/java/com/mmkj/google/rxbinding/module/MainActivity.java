package com.mmkj.google.rxbinding.module;

import android.os.Bundle;
import android.widget.Toast;

import com.mmkj.google.rxbinding.module.base.BaseActivity;
import com.mmkj.google.rxbinding.module.databinding.ActivityMainBinding;
import com.mmkj.google.rxbinding.module.uiutils.UIRxView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

/**
 * @author zhangshuai
 * 首页
 */
public class MainActivity extends BaseActivity {

    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        UIRxView.click(mBinding.btnClick, () -> Toast.makeText(MainActivity.this, "点击成功", Toast.LENGTH_SHORT).show());
    }
}
