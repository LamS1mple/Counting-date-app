package com.example.countingdate.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.countingdate.Model.ImageMemory;

import java.util.List;

@Dao
public interface ImageMemoryDAO {

    @Query("select * from imagememory")
    List<ImageMemory> selectAll();

}
