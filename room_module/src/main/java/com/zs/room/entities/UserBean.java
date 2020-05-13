package com.zs.room.entities;

import android.print.PrinterId;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * * @author zhangshuai
 * tableName：表名
 * indices：索引
 */
@Entity(tableName = "UserTable", indices = {@Index("uid")})
public class UserBean {

    /**
     * 设置主键
     */
    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "FirstName")
    private String firstName;

    @ColumnInfo(name = "LastName")
    private String lastName;

    public int getUid() {
        return uid;
    }

    public void setUid(int uId) {
        this.uid = uid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"uid\":")
                .append(uid);
        sb.append(",\"firstName\":\"")
                .append(firstName).append('\"');
        sb.append(",\"lastName\":\"")
                .append(lastName).append('\"');
        sb.append('}');
        return sb.toString();
    }
}
