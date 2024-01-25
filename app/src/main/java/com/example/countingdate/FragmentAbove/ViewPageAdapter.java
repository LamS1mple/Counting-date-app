package com.example.countingdate.FragmentAbove;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.countingdate.FragmentAbove.FragmentOne;
import com.example.countingdate.FragmentAbove.FragmentTwo;

public class ViewPageAdapter extends FragmentStateAdapter {
    public ViewPageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position == 0){
            return  new FragmentOne();
        }
        return new FragmentTwo();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
