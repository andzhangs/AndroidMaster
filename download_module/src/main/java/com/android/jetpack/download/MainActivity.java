package com.android.jetpack.download;

import android.Manifest;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.SyncStateContract;
import android.util.Log;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tbruyelle.rxpermissions2.RxPermissionsFragment;

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.plugins.RxJavaPlugins;

/**
 * @author zhangshuai
 * https://www.jianshu.com/p/e0496200769c
 */
public class MainActivity extends AppCompatActivity {

    private RxPermissions rxPermissions;
    private DownloadManager downloadManager;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private static final Uri CONTENT_URI = Uri.parse("content://downloads/my_downloads");
    private String apkUrl = "https://qd.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk";
    private DownloadContentObserver mContentObserver = new DownloadContentObserver(mHandler);
    private CompleteReceiver mCompleteReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rxPermissions = new RxPermissions(this);

        initDownload();

        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean) {

                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        mCompleteReceiver=new CompleteReceiver();
        registerReceiver(mCompleteReceiver,new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    /**
     * 2、初始化，调用开始下载
     */
    private void initDownload() {
        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        Uri apkuri = Uri.parse(apkUrl);
        DownloadManager.Request request = new DownloadManager.Request(apkuri);
        //下载的本地路径,表示设置下载地址为SD卡的download文件夹，文件名为mobileqq_android.apk
        request.setDestinationInExternalPublicDir("Download", "mobileqq_android.apk");

        //start 一些非必要的设置
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setVisibleInDownloadsUi(true);
        request.setTitle("哈哈哈");
        request.setMimeType("application/cn.trinea.download.file");
        //end 一些非必要的设置

        downloadManager.enqueue(request);
    }

    //3、下载进度状态的监听及查询
//        downloadManager.getUriForDownloadedFile(downloadId);

    private class DownloadContentObserver extends ContentObserver {

        /**
         * Creates a content observer.
         *
         * @param handler The handler to run {@link #onChange} on, or null if none.
         */
        public DownloadContentObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            updateView();
        }
    }

    int downloadId;
    private void updateView() {
        int[] bytesAndStatus = getBytesAndStatus(downloadId);
        //当前大小
        int currentSize=bytesAndStatus[0];
        //总大小
        int totalSize=bytesAndStatus[1];
        //下载状态
        int status=bytesAndStatus[2];
        Message.obtain(mHandler,0,currentSize,totalSize,status).sendToTarget();
    }

    private int[] getBytesAndStatus(long downloadId) {
        int[] byteAndStatus = new int[]{-1, -1, 0};
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadId);
        Cursor cursor = null;
        try {
            cursor = downloadManager.query(query);
            if (cursor != null && cursor.moveToFirst()) {
                byteAndStatus[0] = cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR);
                byteAndStatus[1] = cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES);
                byteAndStatus[2] = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return byteAndStatus;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getContentResolver().registerContentObserver(CONTENT_URI, true, mContentObserver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getContentResolver().unregisterContentObserver(mContentObserver);
        unregisterReceiver(mCompleteReceiver);
    }

    /**
     * 如果界面上过多元素需要更新，且网速较快不断的执行onChange会对页面性能有一定影响，
     * 或者出现一些异常情况，那么推荐ScheduledExecutorService定期查询，
     * 如下：
     */
    private void mark(){
        ScheduledExecutorService scheduledExecutorService=Executors.newScheduledThreadPool(3);
        Runnable runnable=new Runnable() {
            @Override
            public void run() {

            }
        };
        scheduledExecutorService.scheduleAtFixedRate(runnable,0,3, TimeUnit.SECONDS);
    }

    /**
     * 4、下载进度监听
     */
    private class CompleteReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            //get complete download id
            long completeDownloadId=intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID,-1);
        }
    }

    /**
     * 5、响应通知栏的点击
     */
    private void openDownload(Context context,Cursor cursor){
//        String filename = cursor.getString(cursor.getColumnIndexOrThrow(Downloads.Impl._DATA));
//        String mimetype = cursor.getString(cursor.getColumnIndexOrThrow(Downloads.Impl.COLUMN_MIME_TYPE));
//        Uri path = Uri.parse(filename);
//        // If there is no scheme, then it must be a file
//        if (path.getScheme() == null) {
//            path = Uri.fromFile(new File(filename));
//        }
//        Intent activityIntent = new Intent(Intent.ACTION_VIEW);
//        mimetype = DownloadDrmHelper.getOriginalMimeType(context, filename, mimetype);
//        activityIntent.setDataAndType(path, mimetype);
//        activityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        try {
//            context.startActivity(activityIntent);
//        } catch (ActivityNotFoundException ex) {
//            Log.d("end", "no activity for " + mimetype, ex);
//        }
    }

//    private Handler mUpdateHandler =new Handler(Looper.getMainLooper());
//
//    private Handler.Callback mUpdateCallback =new Handler.Callback() {
//        @Override
//        public boolean handleMessage(Message msg) {
//
//            return false;
//        }
//    }


}
