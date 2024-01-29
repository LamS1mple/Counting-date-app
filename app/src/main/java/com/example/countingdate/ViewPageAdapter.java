package com.example.countingdate.FragmentAbove;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.countingdate.FragmentAbove.FragmentOne;
import com.example.countingdate.FragmentAbove.FragmentTwo;
import com.example.countingdate.FragmentAcOne;
import com.example.countingdate.FragmentAcThree;
import com.example.countingdate.FragmentAcTwo;

public class ViewPageAdapter extends FragmentStateAdapter {
    public ViewPageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position == 0){
            return  new FragmentAcOne();
        }
        if(position == 1){
            return new FragmentAcTwo();
        }
        return new FragmentAcThree();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
