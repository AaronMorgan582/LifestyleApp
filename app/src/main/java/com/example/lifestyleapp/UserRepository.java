package com.example.lifestyleapp;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.core.os.HandlerCompat;
import androidx.lifecycle.MutableLiveData;

import com.amplifyframework.core.Amplify;

import java.io.File;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class UserRepository {

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

    public MutableLiveData<User> getData(String str){
        mUser = new MutableLiveData<>();
        loadUser(str);
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

    public void insertUserToDB(User user){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                userDao.insert(user);
            }

        });
        uploadFile();
    }

    private void uploadFile() {



        File exampleFile = null;
        try {
            exampleFile = new File("/data/data/com.example.lifestyleapp/databases/app.db");
        } catch (Exception e) {
            e.printStackTrace();
        }


        Amplify.Storage.uploadFile(
                "app.db",
                exampleFile,
                result -> Log.i("MyAmplifyApp", "Successfully uploaded: " + result.getKey()),
                storageFailure -> Log.e("MyAmplifyApp", "Upload failed", storageFailure)
        );
    }
}
