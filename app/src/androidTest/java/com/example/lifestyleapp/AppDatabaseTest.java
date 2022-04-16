package com.example.lifestyleapp;

import static org.junit.Assert.*;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AppDatabaseTest {
    private UserDao userDao;
    private AppDatabase db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        userDao = db.userDao();
    }

    @Test
    public void simpleInsert() {
        User user = new User("Cosmo", "Kramer", "72", "Male", "New York", "USA", "80", "71");

        userDao.insert(user);

        User userFromDB = userDao.getActiveUser("Cosmo");

        assertEquals(user.getFirstName(), userFromDB.getFirstName());

    }

    @Test
    public void insertManyTest() {
        User user = new User("Cosmo", "Kramer", "72", "Male", "New York", "USA", "80", "71");
        User user2 = new User("Elaine", "Benes", "54", "Female", "New York", "USA", "80", "71");
        User user3 = new User("George", "Costanza", "54", "Male", "New York", "USA", "80", "71");
        User user4 = new User("Larry", "David", "54", "Male", "New York", "USA", "80", "71");

        userDao.insert(user);
        userDao.insert(user2);
        userDao.insert(user3);
        userDao.insert(user4);

        User userFromDB1 = userDao.getActiveUser("Cosmo");
        User userFromDB2 = userDao.getActiveUser("Elaine");
        User userFromDB3 = userDao.getActiveUser("George");
        User userFromDB4 = userDao.getActiveUser("Larry");

        assertEquals(user.getFirstName(), userFromDB1.getFirstName());
        assertEquals(user2.getFirstName(), userFromDB2.getFirstName());
        assertEquals(user3.getFirstName(), userFromDB3.getFirstName());
        assertEquals(user4.getFirstName(), userFromDB4.getFirstName());

    }

    @Test
    public void checkDBForUpdatedValue(){
        User user = new User("Cosmo", "Kramer", "72", "Male", "New York", "USA", "80", "71");
        User user2 = new User("Cosmo", "Kramer", "72", "Male", "New York", "USA", "60", "71");

        userDao.insert(user);

        User u1 = userDao.getActiveUser("Cosmo");

        userDao.insert(user2);

        User u2 = userDao.getActiveUser("Cosmo");

        assertNotEquals(u1.getWeight(), u2.getWeight());
    }


    @After
    public void closeDb() throws IOException {
        db.close();
    }
}