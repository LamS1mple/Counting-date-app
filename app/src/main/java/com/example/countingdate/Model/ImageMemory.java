package com.example.countingdate.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.ByteArrayOutputStream;

@Entity
public class ImageMemory {
    @PrimaryKey(autoGenerate = true)
    private long imageMemoryId;

    private long memoryToId;
   private Uri uriImage;

    public long getImageMemoryId() {
        return imageMemoryId;
    }

    public void setImageMemoryId(long imageMemoryId) {
        this.imageMemoryId = imageMemoryId;
    }

    public long getMemoryToId() {
        return memoryToId;
    }

    public void setMemoryToId(long memoryToId) {
        this.memoryToId = memoryToId;
    }

    public Uri getUriImage() {
        return uriImage;
    }

    public void setUriImage(Uri uriImage) {
        this.uriImage = uriImage;
    }

    @Override
    public String toString() {
        return "ImageMemory{" +
                "imageMemoryId=" + imageMemoryId +
                ", memoryToId=" + memoryToId +
                ", uriImage=" + uriImage +
                '}';
    }
}
