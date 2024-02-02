package com.example.countingdate;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.countingdate.DAO.MemoryDAO;
import com.example.countingdate.Model.Memory;

@Database(entities = {Memory.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MemoryDAO memoryDAO();

}
