package com.example.countingdate.Model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import androidx.room.ProvidedTypeConverter;
import androidx.room.TypeConverter;

import java.io.ByteArrayOutputStream;
import java.sql.Blob;
import java.util.Date;

@ProvidedTypeConverter
public class Converters {


    public static Converters getInstance(){
        return new Converters();
    }
    @TypeConverter
    public static Date toDateMemory(Long value){
        return value == null ? null : new Date(value);
    }



    @TypeConverter
    public static long toSaveDateRoom(Date date){
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static Uri toUri(String path){
        return Uri.parse(path);
    }

    @TypeConverter
    public static String toStringImage(Uri uri){
        return uri.toString();
    }



}
