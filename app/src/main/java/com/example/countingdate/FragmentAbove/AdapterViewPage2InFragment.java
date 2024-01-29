package com.example.countingdate.FragmentAbove;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPage2InFragment extends FragmentStateAdapter {
    public ViewPage2InFragment(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if(position == 0){
            new FragmentOne();
        }
        if (position == 1){
            new FragmentTwo();
        }
        return new FragmentThree();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
