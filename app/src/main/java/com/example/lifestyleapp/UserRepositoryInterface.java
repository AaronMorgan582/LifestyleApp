package com.example.lifestyleapp;

import androidx.lifecycle.MutableLiveData;

public interface UserRepositoryInterface {

    public MutableLiveData<User> getData(String str);
    public void insertUserToDB(User user);
}
