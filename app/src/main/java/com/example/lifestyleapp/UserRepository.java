package com.example.lifestyleapp;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.os.HandlerCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.HashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import kotlin.Unit;
import kotlin.coroutines.Continuation;

public class UserRepository implements UserRepositoryInterface{

    private static UserRepository instance;
    private UserDao userDao;
    public User user;
    private final Executor executor = Executors.newSingleThreadExecutor();
    private Handler mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());
    AppDatabase db;
    MutableLiveData<User> mUser;

    private UserRepository(Application application){
        db = AppDatabase.getDatabase(application);
        userDao = db.userDao();
    }
    public static synchronized UserRepository getInstance(Application application){
        if(instance == null){
            instance = new UserRepository(application);
        }
        return instance;
    }

    @Override
    public MutableLiveData<User> getData(String str){
        mUser = new MutableLiveData<>();
        loadUser(str);
       // mUser.setValue(this.user);
        return mUser;
    }

    private void loadUser(String str){
        final User[] retrievedUser = new User[1];
        executor.execute(new Runnable() {
            @Override
            public void run() {
                retrievedUser[0] = userDao.getActiveUser(str);
                postToMainThread(retrievedUser[0]);
            }
        });
    }

    private void postToMainThread(User user){
        mainThreadHandler.post(()->{
            if(user != null){
                mUser.setValue(user);
            }else{
                mUser.setValue(new User("", "", "", "", "", "", "",""));
            }
        });
    }

    @Override
    public void insertUserToDB(User user){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                userDao.insert(user);
            }
        });

    }

}
