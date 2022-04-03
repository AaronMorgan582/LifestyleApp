package com.example.lifestyleapp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//TODO: If adding a Weather Table, add the class to the "entities" set.
@Database(entities = {User.class,Weather.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase instance;
    public abstract UserDao userDao();
    public abstract WeatherDao weatherDao();
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(4);

    static synchronized AppDatabase getDatabase(final Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "app.db").addCallback(initCallBack).build();
        }

        return instance;
    }

    private static RoomDatabase.Callback initCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db){
            super.onCreate(db);
            //Sets up some fake data to see if the database gets set up correctly.
            databaseExecutor.execute(()->{
                UserDao dao = instance.userDao();
                User userTable = new User("Fake", "Person", "123", "Male",
                        "Gondor", "MiddleEartch", "80", "110");
                dao.insert(userTable);
            });
        }
    };
}
