package com.example.countingdate.FragmentAbove;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class AdapterViewPage2InFragment extends FragmentStateAdapter {

    public AdapterViewPage2InFragment(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        if (position == 0) {
            return new FragmentOne();
        }
        if (position == 1) {
            return new FragmentTwo();
        }
        return new FragmentThree();

    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
