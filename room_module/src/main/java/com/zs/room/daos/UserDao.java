package com.zs.room.daos;

import com.zs.room.entities.UserBean;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UserDao {

    @Insert
    void insertUser(UserBean... userBeans);

    @Delete
    void deleteUser(UserBean userBean);

    @Update
    void updateUser(UserBean... userBeans);

    @Query("SELECT * FROM UserTable")
    List<UserBean> getAllUsers();

}
