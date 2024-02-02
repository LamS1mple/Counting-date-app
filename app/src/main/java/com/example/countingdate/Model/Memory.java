package com.example.countingdate.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Memory {

    @PrimaryKey(autoGenerate = true)
    public int id;


    @ColumnInfo(name = "title")
    public String title;
//    @ColumnInfo(name = "date")
//    public Date date;

}
