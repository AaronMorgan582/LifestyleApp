package com.example.lifestyleapp;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

//TODO: Still need to define an insert() for this class to enter new users into the database.
public class UsersViewModel extends AndroidViewModel {
    private MutableLiveData<User> selected;
    private UserRepository userRepository;

    public UsersViewModel(Application application){
        super(application);
        userRepository = UserRepository.getInstance(application);
        UserDao userDao = AppDatabase.getDatabase(application).userDao();
    }

    public void initActiveUser(String str){
        if(selected != null) {
            return;
        }
        selected = userRepository.getData(str);
    }

    public void select(User user){
        selected.setValue(user);
    }
    public LiveData<User> getSelected(){
        return selected;
    }
    public void insert(User user){
        userRepository.insertUserToDB(user);
    }


}
