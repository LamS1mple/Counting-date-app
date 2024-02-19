package com.example.countingdate;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.example.countingdate.DAO.ImageMemoryDAO;
import com.example.countingdate.DAO.MemoryDAO;
import com.example.countingdate.Model.Converters;
import com.example.countingdate.Model.ImageMemory;
import com.example.countingdate.Model.Memory;

@Database(entities = {Memory.class, ImageMemory.class}, version = 1)
@TypeConverters(value = {Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase db;
    private final static String name = "lam";
    public static AppDatabase getInstance(Context context){
        if (db == null){
            db = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, name)
                    .allowMainThreadQueries()
                    .build();
        }
        return db;
    }
    public abstract MemoryDAO memoryDAO();
    public abstract ImageMemoryDAO imageMemoryDAO();

}
