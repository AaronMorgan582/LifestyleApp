package com.example.lifestyleapp;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UsersViewModel extends ViewModel {
    private MutableLiveData<User> selected;
    private UserRepository userRepository;
    /*
    public UsersViewModel(String str){
        userData = UserRepository.getInstance(str);
        selected = userData.getData(str);

    }
*/
    public void initActiveUser(String str){
        if(selected != null) {
            return;
        }
        userRepository = UserRepository.getInstance(str);
        selected = userRepository.getData(str);

    }

    public void select(User user){
        selected.setValue(user);
    }
    public LiveData<User> getSelected(){
        return selected;
    }


}
