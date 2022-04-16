package com.example.lifestyleapp;

import static org.junit.Assert.*;

import android.app.Application;
import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.time.Instant;
import java.util.List;

@RunWith(RobolectricTestRunner.class)
public class UsersViewModelTest {
    UsersViewModel vm;
    LiveData<User> liveData;
    Observer<User> observer;
    UserDao userDao;
    AppDatabase db;

    @Before
    public void setUp() throws Exception {
        Context context = ApplicationProvider.getApplicationContext();
        vm = new UsersViewModel(ApplicationProvider.getApplicationContext());

        liveData = vm.getSelected();

        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        userDao = db.userDao();

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

    @After
    public void tearDown() throws Exception {
        db.close();
    }
}