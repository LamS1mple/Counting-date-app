package com.example.countingdate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

public interface SendDataFragmentOneActi {
    public void setDate(int year, int month, int date);
    public long getSecondWithCurrent();

    public int[] getFragmentTime(Calendar dateSelected);
}
