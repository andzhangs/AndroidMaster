package com.zs.bugly;


import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.tencent.bugly.crashreport.CrashReport;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author zhangshuai
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermissions();

        findViewById(R.id.btn_Java_Crash).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermissions()) {
                    CrashReport.testJavaCrash();

                    //必须依赖更新包
//                    Beta.checkUpgrade();

                }
            }
        });

        findViewById(R.id.btn_Java_ANR).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermissions()) {
                    CrashReport.testANRCrash();
                }
            }
        });
    }

    boolean isPassed = false;

    private boolean checkPermissions() {
        AndPermission.with(this)
                .runtime()
                .permission(Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        Toast.makeText(MainActivity.this, "授权成功", Toast.LENGTH_SHORT).show();
                        isPassed = true;
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        Toast.makeText(MainActivity.this, "授权失败!!!", Toast.LENGTH_SHORT).show();
                        isPassed = false;
                    }
                }).start();
        return isPassed;
    }
}
