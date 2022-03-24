package com.example.lifestyleapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.HashMap;

public class UserRepository {

    private static UserRepository instance;
    private User user;
    private HashMap<String, User> existingUsers = new HashMap<>();


    public static UserRepository getInstance(String str){
        if(instance == null){
            instance = new UserRepository();
        }
        return instance;
    }

    public MutableLiveData<User> getData(String str){
        loadUser(str);
        MutableLiveData<User> user = new MutableLiveData<>();
        user.setValue(this.user);
        return user;
    }

    public void loadUser(String str){
        User user = new User("Gabriel", "Garcia-Marquez", "Male", "Aracataca", "Colombia", "80", "67");
        user.setRegistered(true);
        existingUsers.put("gabo@hotmail.com", user);

        if(existingUsers.containsKey(str)){
            this.user = existingUsers.get(str);
        }
        else{
            this.user = new User("","","","","","","");
            this.user.setRegistered(false);
        }


    }
}
