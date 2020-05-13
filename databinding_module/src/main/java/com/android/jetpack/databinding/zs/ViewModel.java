package com.android.jetpack.databinding.zs;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class ViewModel extends BaseObservable {

    @Bindable
    private String userName;

    @Bindable
    private String userSex;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
        notifyPropertyChanged(BR.userName);
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
        notifyPropertyChanged(BR.userSex);
    }
}
