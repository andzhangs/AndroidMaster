package com.zs.room.app;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


/**
 * @author zhangshuai
 */
public class MyRoomApplication extends Application {

    private static AppDataBase db;

    @Override
    public void onCreate() {
        super.onCreate();
        db = Room.databaseBuilder(this, AppDataBase.class, "user_db")
                .addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        //第一次创建数据库时调用，但 是在创建所有表之后调用的
                    }

                    @Override
                    public void onOpen(@NonNull SupportSQLiteDatabase db) {
                        super.onOpen(db);
                        //当数据库被打开时调用
                    }
                })
                //允许在主线程查询数据
                .allowMainThreadQueries()
                //迁移数据库使用
//                .addMigrations()
                //迁移数据库如果发生错误，将会重新创建数据库
//                .fallbackToDestructiveMigration()
                .build();
    }

    public static synchronized AppDataBase AppDB() {
        return db;
    }
}
