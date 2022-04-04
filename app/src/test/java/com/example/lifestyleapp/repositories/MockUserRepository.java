package com.example.lifestyleapp.repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.lifestyleapp.User;
import com.example.lifestyleapp.UserRepositoryInterface;

import java.util.ArrayList;
import java.util.HashMap;

public class MockUserRepository implements UserRepositoryInterface {
    HashMap<String, User> users = new HashMap<>();
    MutableLiveData<User> liveUser = new MutableLiveData<>();

    @Override
    public MutableLiveData<User> getData(String str) {

        if(users.containsKey(str)){
            liveUser.setValue(users.get(str));
        }
        else{
            User user = new User(str, "", "", "", "", "", "", "");
            users.put(str, user);
            liveUser.setValue(user);
        }
        return liveUser;
    }

    @Override
    public void insertUserToDB(User user) {
        users.put(user.getFirstName(), user);

    }

    public void setLiveData(User user){

    }
}
