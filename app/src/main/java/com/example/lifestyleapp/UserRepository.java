package com.example.lifestyleapp;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.HashMap;

public class UserRepository {

    private static UserRepository instance;
    private UserDao userDao;
    private User user;
    private HashMap<String, User> existingUsers = new HashMap<>();

    private UserRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        userDao = db.userDao();
    }
    public static synchronized UserRepository getInstance(Application application){
        if(instance == null){
            instance = new UserRepository(application);
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
