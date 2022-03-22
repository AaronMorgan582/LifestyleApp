package com.example.lifestyleapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UsersViewModel extends ViewModel {
    private MutableLiveData<User> selected;
    private UserRepository userData;

    public UsersViewModel(User user){
        userData = UserRepository.getInstance(user);
        selected = userData.getData();

    }

    public void select(User user){
        selected.setValue(user);
    }
    public LiveData<User> getSelected(){
        return selected;
    }

    public LiveData<User> getUser(){
        if(selected == null){
            selected = new MutableLiveData<User>();
            loadUserFromDB();
        }
        return selected;
    }

    private void loadUserFromDB(){
        System.out.println("Loading User");
    }
}
