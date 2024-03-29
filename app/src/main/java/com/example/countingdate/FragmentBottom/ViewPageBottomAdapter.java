package com.example.countingdate.FragmentBottom;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.countingdate.FragmentBottom.FragmentBottomOne;

public class ViewPageBottomAdapter extends FragmentStateAdapter {
    public ViewPageBottomAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0){
            return new FragmentBottomOne();
        }

        return new FragmentBottomTwo();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
