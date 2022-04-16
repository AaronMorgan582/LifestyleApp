package com.example.lifestyleapp;

import static org.junit.Assert.*;

import android.app.Application;
import android.content.Context;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.time.Instant;
import java.util.List;

@RunWith(RobolectricTestRunner.class)
public class UsersViewModelTest {

    UsersViewModel vm;
    LiveData<User> liveData;
    Observer<User> observer;

    @Before
    public void setUp() throws Exception {

        Context context = ApplicationProvider.getApplicationContext();
        vm = new UsersViewModel(ApplicationProvider.getApplicationContext());

        liveData = vm.getSelected();
    }

    @Test
    public void assertLiveDataNotNull(){
        vm.initActiveUser("Alejandro");
        assertNotNull(vm.getSelected());
    }

    @Test
    public void assertSelectLiveData(){
        vm.initActiveUser("Alejandro");
        User testUser2 = new User("Jerry", "Seinfeld", "67", "Male", "New York", "USA", "80", "71");
        vm.select(testUser2);
        vm.getSelected().observeForever(user -> {});

        liveData = vm.getSelected();

        User u = liveData.getValue();
        assertEquals(u.getFirstName(), testUser2.getFirstName());
    }

    @Test
    public void liveDataHasChanged(){
        vm.initActiveUser("Alejandro");
        User testUser2 = new User("Alejandra", "Pizarnik", "28", "Female", "Buenos Aires", "Argentina", "80", "71");
        User testUser3 = new User("Alejandra", "Pizarnik", "28", "Female", "Buenos Aires", "Argentina", "60", "71");

        vm.select(testUser2);
        liveData = vm.getSelected();

        assertEquals(liveData.getValue().getWeight(), testUser2.getWeight());

        vm.select(testUser3);

        liveData = vm.getSelected();

        assertEquals(liveData.getValue().getWeight(), testUser3.getWeight());

    }
}