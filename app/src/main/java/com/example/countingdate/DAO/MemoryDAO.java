package com.example.countingdate.DAO;


import androidx.room.Dao;
import androidx.room.Query;

import com.example.countingdate.Model.Memory;

import java.util.List;

@Dao
public class MemoryDAO
{
    @Query("Select * from memory")
    List<Memory> getAllMemory;
}
