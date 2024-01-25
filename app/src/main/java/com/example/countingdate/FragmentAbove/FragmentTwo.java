package com.example.countingdate.FragmentAbove;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.countingdate.MainActivity;
import com.example.countingdate.R;
import com.example.countingdate.SendDataFragmentOneActi;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FragmentTwo extends Fragment {

    TextView txtYear, txtMonth, txtWeek, txtDate, txtHour, txtMinute, txtSecond, txtStartDate;
    SimpleDateFormat simpleDateFormat;
    SendDataFragmentOneActi sendDataFragmentActi;
    MainActivity cotext;

    Calendar calendar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_two, container, false);
        sendDataFragmentActi = (SendDataFragmentOneActi) getActivity();
        cotext = (MainActivity) getActivity();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtYear = view.findViewById(R.id.year);
        txtMonth = view.findViewById(R.id.month);
        txtWeek = view.findViewById(R.id.week);
        txtDate = view.findViewById(R.id.date);
        txtHour = view.findViewById(R.id.hour);
        txtMinute = view.findViewById(R.id.minute);
        txtSecond = view.findViewById(R.id.second);
        txtStartDate = view.findViewById(R.id.start_date);
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        calendar = cotext.getDateSelected();

        txtStartDate.setText(simpleDateFormat.format(calendar.getTime()));
        calculatorTime();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                handler.postDelayed(this::run, 1000);
                calculatorTime();
            }
        }, 1000);
    }
    public void calculatorTime(){
        int[] arrDate = sendDataFragmentActi.getFragmentTime();
        txtYear.setText(String.valueOf(arrDate[0]));
        txtMonth.setText(String.valueOf(arrDate[1]));
        txtWeek.setText(String.valueOf(arrDate[2]));
        txtDate.setText(String.valueOf(arrDate[3]));
        txtHour.setText(String.valueOf(arrDate[4]));
        txtMinute.setText(String.valueOf(arrDate[5]));
        txtSecond.setText(String.valueOf(arrDate[6]));
    }


    @Override
    public void onResume() {
        super.onResume();
        calendar = cotext.getDateSelected();

        txtStartDate.setText(simpleDateFormat.format(calendar.getTime()));
    }
}
