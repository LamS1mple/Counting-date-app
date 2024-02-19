package com.example.countingdate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.countingdate.FragmentBottom.ViewPageBottomAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.squareup.picasso.Picasso;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.Calendar;
import java.util.UUID;


public class MainActivity extends AppCompatActivity implements SendDataFragmentOneActi {
    ViewPager2 viewPager2Main;
    ViewPageAdapter viewPageAdapter;

    TabLayout tabLayoutMain;
    Calendar dateSelected ;

    SharedPreferences sharedPreferences;

    ImageView backGroundAc;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mappingView();

        viewPageAdapter = new ViewPageAdapter(this);
        viewPager2Main.setAdapter(viewPageAdapter);
        viewPager2Main.setCurrentItem(1);

        new TabLayoutMediator(tabLayoutMain, viewPager2Main, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if (position == 0){
                    tab.setIcon(R.drawable.history);
                }
                if (position == 2){
                    tab.setIcon(R.drawable.setting);
                }
                if (position == 1){
                    tab.setText(getResources().getText(R.string.s1mple_love));
                }
            }
        }).attach();


        setDate();
        setImg();

   }

   public void setEnableViewPager2(boolean check){
        viewPager2Main.setUserInputEnabled(check);
   }



    private void mappingView(){
        sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        backGroundAc = findViewById(R.id.backGroundAc);
        viewPager2Main = findViewById(R.id.view_page2_main);
        tabLayoutMain = findViewById(R.id.tabLayout);
    }




    public void setBackGroundAc(Uri i){
        String s = i.toString();
//        backGroundAc.setImageURI(i);
        Picasso.get()
                .load(i)
                .into(backGroundAc);
    }
    private void setDate(){
        dateSelected = Calendar.getInstance();


        dateSelected.set(Calendar.YEAR, sharedPreferences
                .getInt("year",dateSelected.get(Calendar.YEAR) ));
        dateSelected.set(Calendar.MONTH, sharedPreferences
                .getInt("month", dateSelected.get(Calendar.MONTH)));
        dateSelected.set(Calendar.DAY_OF_MONTH,sharedPreferences
                .getInt("date", dateSelected.get(Calendar.DAY_OF_MONTH)));

        dateSelected.set(Calendar.HOUR, 0);
        dateSelected.set(Calendar.MINUTE, 0);
        dateSelected.set(Calendar.SECOND, 0);
        dateSelected.set(Calendar.MILLISECOND, 0);
    }

    private void setImg(){
        String imgBackground = sharedPreferences.getString("imgBackground", null);
        if (imgBackground != null){
            Picasso.get()
                    .load(imgBackground)
                    .into(backGroundAc);
        }
        else{
            Picasso.get()
                    .load(R.drawable.back1)
                    .into(backGroundAc);
        }
    }
    @Override
    public void setDate(int year, int month, int date) {
        editor.putInt("year" , year);
        editor.putInt("month" , month);
        editor.putInt("date" , date);
        editor.apply();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month,date,0,0,0);
        dateSelected = calendar;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK &&  requestCode== UCrop.REQUEST_CROP){
            Log.d("hello","in");
            Uri resultUri = UCrop.getOutput(data);
            editor.putString("imgBackground", resultUri.toString());
            editor.apply();
            setBackGroundAc(resultUri);
        }
        else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);

        }
    }



    @Override
    public long getSecondWithCurrent() {

        if (dateSelected == null) return 0;
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime().getTime() - dateSelected.getTime().getTime();
    }

    @Override
    public int[] getFragmentTime(Calendar dateSelected) {
        if (dateSelected == null){
            dateSelected = this.dateSelected;
        }
        int[] arrdate = new int[7];
        Calendar current = Calendar.getInstance();
        int year , monthOfYear, weekOfMonth, dayOfWeek;

        int dayCurrent = current.get(Calendar.DAY_OF_MONTH);
        int daySelected = dateSelected.get(Calendar.DAY_OF_MONTH);

        int monthCurrent = current.get(Calendar.MONTH);
        int monthSelected = dateSelected.get(Calendar.MONTH);


        int yearCurrent = current.get(Calendar.YEAR);
        int yearSelected = dateSelected.get(Calendar.YEAR);

        int maxDayOfMonth = dateSelected.getActualMaximum(Calendar.DATE);
        if (current.get(Calendar.DATE) < dateSelected.get(Calendar.DATE)){
            int date = maxDayOfMonth - daySelected + dayCurrent;
            dayOfWeek = date % 7 ;
            weekOfMonth = date / 7;

            if (  monthCurrent <= monthSelected ) {
                year = yearCurrent - yearSelected - 1;

                monthOfYear = 11 - monthSelected + monthCurrent ;

            }
            else{
                year = yearCurrent - yearSelected ;
                monthOfYear = monthCurrent - monthSelected ;

            }
        }
        else{
            int date = dayCurrent - daySelected;
            dayOfWeek = date % 7 ;
            weekOfMonth = date / 7;
            if (monthCurrent < monthSelected ){
                year = yearCurrent - yearSelected - 1;

                monthOfYear = 11 - monthSelected + monthCurrent + 1;
            }
            else{
                year = yearCurrent - yearSelected ;

                monthOfYear = monthCurrent - monthSelected ;
            }
        }
        monthOfYear = monthOfYear % 12 ;
        arrdate[0] = year;
        arrdate[1] = monthOfYear;
        arrdate[2] = weekOfMonth;
        arrdate[3] = dayOfWeek;
        arrdate[4] = current.get(Calendar.HOUR_OF_DAY);
        arrdate[5] = current.get(Calendar.MINUTE);
        arrdate[6] = current.get(Calendar.SECOND);




        return arrdate;
    }

    public Calendar getDateSelected() {
        return dateSelected;
    }

    public void UCrop(Uri sourceUri ,Uri destinationUri ){
        String chlid = UUID.randomUUID() + ".jpg";
        UCrop.of(sourceUri, Uri.fromFile(new File( getCacheDir(), chlid)))
                .withAspectRatio(9, 16)
                .start(MainActivity.this );
    }
}