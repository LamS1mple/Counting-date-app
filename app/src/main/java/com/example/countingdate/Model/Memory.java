package com.example.countingdate.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;


@Entity
public class Memory {

    @PrimaryKey(autoGenerate = true)
    private long memoryId;


    private String title;
    private Date date;

    public long getMemoryId() {
        return memoryId;
    }

    public void setMemoryId(long memoryId) {
        this.memoryId = memoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Memory{" +
                "memoryId=" + memoryId +
                ", title='" + title + '\'' +
                ", date=" + date +
                '}';
    }
}
