package com.example.countingdate;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.countingdate.Model.ImageMemory;
import com.example.countingdate.Model.Memory;
import com.example.countingdate.Model.MemoryWithImage;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;

public class MainActivity2 extends AppCompatActivity {
    ImageButton back;

    TextView save;
    ImageView selectImage;
    RecyclerView listImageRecyc;
    EditText date, content;
    ImageAdapter imageAdapter;
    private MemoryWithImage memoryWithImage;

    private List<ImageMemory> imageMemory;

    ConstraintLayout constraintLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        constraintLayout = findViewById(R.id.offType);
        back = findViewById(R.id.back);
        save = findViewById(R.id.save);
        selectImage = findViewById(R.id.imageView);
        listImageRecyc = findViewById(R.id.select_item_list_memory);
        listImageRecyc.setNestedScrollingEnabled(false);
        date = findViewById(R.id.date_memory);
        content = findViewById(R.id.content_memory_ac);


        imageAdapter = new ImageAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listImageRecyc.setLayoutManager(linearLayoutManager);
        listImageRecyc.setAdapter(imageAdapter);


        memoryWithImage = new MemoryWithImage();
        memoryWithImage.setMemory(new Memory());
        imageMemory = new ArrayList<>();
        imageAdapter.setList(imageMemory);

        content.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus){
                    InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(MainActivity2.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity2.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String date = String.format("%02d/%02d/%d", dayOfMonth , (month + 1) , year);
                        calendar.set(year, month + 1, dayOfMonth);
                        memoryWithImage.getMemory().setDate(calendar.getTime());
                        MainActivity2.this.date.setText(date);
                    }
                }, year , month , day);
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() );
                datePickerDialog.show();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                memoryWithImage.setImageMemories(imageMemory);
                memoryWithImage.getMemory().setTitle(content.getText().toString());
                AppDatabase.getInstance(MainActivity2.this)
                        .memoryDAO()
                        .insertMemory(memoryWithImage.getMemory(), memoryWithImage.getImageMemories());

            }
        });
        imageAdapter.setSendToAc(new ImageAdapter.sendToAc() {
            @Override
            public void deleteItem(int position) {
                imageMemory.remove(position);
                imageAdapter.setList(imageMemory);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity2.super.getOnBackPressedDispatcher().onBackPressed();
            }
        });

        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
            }
        });

    }



    private void checkPermission(){
        TedPermission.create()
                .setPermissions(Manifest.permission.CAMERA
                        , Manifest.permission.READ_EXTERNAL_STORAGE
                ,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        selectMultiImage();
                    }

                    @Override
                    public void onPermissionDenied(List<String> deniedPermissions) {

                    }
                })
                .check();
    }
    private void selectMultiImage(){
        TedBottomPicker.with(this)
                .setPeekHeight(1600)
                .showTitle(false)
                .setCompleteButtonText(R.string.select)
                .setEmptySelectionText(R.string.choose_image)

                .showMultiImage(new TedBottomSheetDialogFragment.OnMultiImageSelectedListener() {
                    @Override
                    public void onImagesSelected(List<Uri> uriList) {

                        for (Uri i : uriList){
                            ImageMemory item = new ImageMemory();
                            item.setUriImage(i);
                            imageMemory.add(item);
                        }
                        imageAdapter.setList(imageMemory);
                    }
                });
    }
}