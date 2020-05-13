package com.zs.room.app;

import com.zs.room.daos.UserDao;
import com.zs.room.entities.UserBean;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

/**
 * @author zhangshuai
 */
@Database(entities = {UserBean.class},version = 1)
public abstract class AppDataBase extends RoomDatabase {

    public abstract UserDao getUserDao();

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {

    }
}
