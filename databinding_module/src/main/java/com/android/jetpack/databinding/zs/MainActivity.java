package com.android.jetpack.databinding.zs;

import android.os.Bundle;

import com.android.jetpack.databinding.zs.databinding.ActivityMainBinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mBinding;
    private ViewModel mViewModel=new ViewModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.setLifecycleOwner(this);
        mViewModel.setUserName("Hello World ！");
        mViewModel.setUserSex("Man");
        mBinding.setViewModel(mViewModel);
    }

}
