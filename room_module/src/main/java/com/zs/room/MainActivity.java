package com.zs.room;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.zs.room.app.MyRoomApplication;
import com.zs.room.daos.UserDao;
import com.zs.room.entities.UserBean;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author zhangshuai
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MMainActivity";
    private int i = 0;
    UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button btnAdd = findViewById(R.id.btn_Add_Search);

        AndPermission.with(this)
                .runtime()
                .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        Log.i(TAG, "onGranted: " + data.size());
                        btnAdd.setEnabled(data.size() == 2);
                        if (data.size() == 2) {
                            userDao = MyRoomApplication.AppDB().getUserDao();
                        }
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        Log.i(TAG, "onDenied: " + data.size());
                    }
                })
                .start();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int j = 0; j < 10; j++) {
                    loadRoom();
                }
            }
        });
    }

    private synchronized void loadRoom() {
        UserBean userBean = new UserBean();
        userBean.setFirstName("姓" + i);
        userBean.setLastName("名" + i);
            userDao.insertUser(userBean);
            Log.i(TAG, "添加: " + userBean.toString());

        List<UserBean> userBeans = userDao.getAllUsers();
        Log.i(TAG, "打印: " + userBeans.get(i).getUid() + "," + userBeans.get(i).getFirstName() + "," + userBeans.get(i).getLastName() + "\n");
        i++;
    }
}
