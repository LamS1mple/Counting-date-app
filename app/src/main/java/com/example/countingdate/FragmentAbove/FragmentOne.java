package com.example.countingdate.FragmentAbove;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;


import com.example.countingdate.MainActivity;
import com.example.countingdate.R;
import com.example.countingdate.SendDataFragmentOneActi;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.Calendar;

public class FragmentOne extends Fragment {


    SendDataFragmentOneActi sendDataFragmentActi;
    TextView textTitleAbove, textTitleCenter, textTitleBottom;
    TextView textDialogChangeDate , textChangBackGround
            , txtDialogChangeTitleAbove, txtDialogTitleBottom;

    LinearLayout linearLayoutCenterOne;

    MainActivity context;
    Handler handler;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private Boolean selectedTitle ;

    ActivityResultLauncher<String> mGetContent
            = registerForActivityResult(new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri o) {
                        context.UCrop(o , o);
                }
            });


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, container, false);

        sendDataFragmentActi = (SendDataFragmentOneActi) getActivity();
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("life", "onViewCreated");


        context =(MainActivity) getActivity();
        sharedPreferences = context.getPreferences(Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        textTitleAbove = view.findViewById(R.id.title_above);
        textTitleBottom = view.findViewById(R.id.title_bottom);
        textTitleCenter = view.findViewById(R.id.title_center);
        linearLayoutCenterOne = view.findViewById(R.id.view_group_fragment_one);
        linearLayoutCenterOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onCreateDialog();
            }
        });
        calculatorTime();

        textTitleAbove.setText(sharedPreferences.getString("titleAbove", getString(R.string.title_above)));
        textTitleBottom.setText(sharedPreferences.getString("titleBottom", getString(R.string.title_bottom)));

        handler = new Handler();

    }
    public  void startCallBack(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this::run, 1000);
                calculatorTime();
            }
        }, 1000);
    }
    public void removeCallBack(){
        handler.removeCallbacksAndMessages(null);

    }


    public void onCreateDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_above_fragment_one, null);
        builder.setView(view);

        AlertDialog dialog = builder.create();
        dialog.show();

        textChangBackGround = view.findViewById(R.id.changeBackground);
        textDialogChangeDate = view.findViewById(R.id.changedate);
        txtDialogChangeTitleAbove = view.findViewById(R.id.changeTitleAbove);
        txtDialogTitleBottom = view.findViewById(R.id.changeTitleBottom);

        txtDialogChangeTitleAbove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTitle = true;
                onCreaterDialogChangeTitle();
                dialog.dismiss();

            }
        });
        txtDialogTitleBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTitle = false;

                onCreaterDialogChangeTitle();
                dialog.dismiss();
            }
        });



        textDialogChangeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCreateDialogDate();
                dialog.dismiss();
            }
        });
        textChangBackGround.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGetContent.launch("image/*");
                dialog.dismiss();
            }
        });

    }


    public void onCreaterDialogChangeTitle(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_input, null);

        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

        TextView textView = view.findViewById(R.id.editText);
        Button cancel = view.findViewById(R.id.cancel_dialog_input);
        Button accept = view.findViewById(R.id.accept);

        if (selectedTitle){
            textView.setText(textTitleAbove.getText());

        }
        else {
            textView.setText(textTitleBottom.getText());

        }
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = textView.getText().toString().trim();
                if ( selectedTitle){
                    textTitleAbove.setText(input);
                    editor.putString("titleAbove", input);
                }
                else{
                    editor.putString("titleBottom", input);
                    textTitleBottom.setText(input);
                }
                editor.commit();
                dialog.dismiss();
            }
        });




    }

    public void onCreateDialogDate(){

        Calendar calendar = context.getDateSelected();
        int yearC = calendar.get(Calendar.YEAR);
        int monthC = calendar.get(Calendar.MONTH) ;
        int dayC = calendar.get(Calendar.DATE);
        // chon ngay
        DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                sendDataFragmentActi.setDate(year, month, dayOfMonth);
                calculatorTime();
            }
        }, yearC, monthC, dayC  );


        dialog.getDatePicker().setMaxDate(System.currentTimeMillis() );
        dialog.show();
    }

    public void calculatorTime(){
        long secondData = sendDataFragmentActi.getSecondWithCurrent();
        String date = (secondData / 86400000) +"";
        textTitleCenter.setText(date);
    }


    @Override
    public void onPause() {
        super.onPause();
        removeCallBack();
    }

    @Override
    public void onResume() {
        super.onResume();
        startCallBack();

    }

}
