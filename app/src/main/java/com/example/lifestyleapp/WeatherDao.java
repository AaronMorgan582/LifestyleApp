package com.example.lifestyleapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Weather weather);

    @Query("SELECT * from weather_table ORDER BY city_name ASC")
    LiveData<List<WeatherTable>> getAll();

    @Query("SELECT * FROM weather_table where city_name = :city")
    Weather getWeather(String city);

}
