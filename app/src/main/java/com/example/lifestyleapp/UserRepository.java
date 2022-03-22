package com.example.lifestyleapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class UserRepository {

    private static UserRepository instance;
    private final MutableLiveData<User> user = new MutableLiveData<User>();

    public static UserRepository getInstance(User user){
        return instance;
    }

    public MutableLiveData<User> getData(){
        return user;
    }

    public void loadUser(){
        // load user from db
    }
}
