package com.example.lifestyleapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Query("SELECT * from user_table ORDER BY first_name ASC")
    LiveData<List<UserTable>> getAll();

    //TODO: Will likely need to make a query for a specific user.
}
