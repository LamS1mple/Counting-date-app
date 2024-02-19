package com.example.countingdate.DAO;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.countingdate.Model.ImageMemory;
import com.example.countingdate.Model.Memory;
import com.example.countingdate.Model.MemoryWithImage;

import java.util.List;

@Dao
public interface MemoryDAO
{
    @Transaction
    @Query("Select * from Memory")
    List<MemoryWithImage> getAllMemory();

    @Transaction
    default void insertMemory(Memory memoryWithImage, List<ImageMemory> imageMemoryList){
        long id = insert(memoryWithImage);
        for (ImageMemory i : imageMemoryList){
            i.setMemoryToId(id);
            insert(i);
        }
    }

    @Insert
    long insert(Memory memory);

    @Insert
    long insert(ImageMemory imageMemory);

}
