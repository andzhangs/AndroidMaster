package com.zs.bugly;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;


/**
 * @author zhangshuai
 */
public class TestBuglyApplication extends MultiDexApplication {

    public static final String BUGLY_APP_ID="a2e4abf8f6";
    public static final String BUGLY_CHANNEL="Test_Bugly_Demo";

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Context context = getApplicationContext();
        String packetName = context.getPackageName();
        String processName = appGetProcessName(android.os.Process.myPid());
        //设置是否上报进程
        CrashReport.UserStrategy userStrategy = new CrashReport.UserStrategy(context);
        userStrategy.setUploadProcess(processName == null || processName.equals(packetName));
        //初始化
        CrashReport.initCrashReport(context, BUGLY_APP_ID, true,userStrategy);
        CrashReport.setAppChannel(context,BUGLY_CHANNEL);
        CrashReport.setAppVersion(context,"1.0");
        //必须依赖更新包
//        Bugly.init(context,BUGLY_APP_ID,true,userStrategy);
//        Bugly.setAppChannel(context,BUGLY_CHANNEL);
    }

    private String appGetProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
