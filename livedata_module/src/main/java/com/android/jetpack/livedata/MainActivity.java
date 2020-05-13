package com.android.jetpack.livedata;

import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

/**
 * @author zhangshuai
 */
public class MainActivity extends AppCompatActivity {

    private NameViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView tv = findViewById(R.id.tv_name);

        mViewModel = ViewModelProviders.of(this).get(NameViewModel.class);

        mViewModel.getCurrentName().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tv.setText(s);
            }
        });


        findViewById(R.id.btn_setValue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                synchronized (this) {
                    NetWorkLiveData.getInstance(MainActivity.this).observe(MainActivity.this, new Observer<NetworkInfo>() {
                        @Override
                        public void onChanged(NetworkInfo networkInfo) {
                            Log.w("onChanged", "onChanged: " + networkInfo.toString());
                            mViewModel.getCurrentName().setValue(networkInfo.toString());
                        }
                    });
                }
            }
        });
    }
}
