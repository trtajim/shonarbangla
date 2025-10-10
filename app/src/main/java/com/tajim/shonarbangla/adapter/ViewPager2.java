package com.tajim.shonarbangla.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.tajim.shonarbangla.frag.CalcFragment;
import com.tajim.shonarbangla.frag.PricFragment;

public class ViewPager2 extends FragmentStateAdapter {
    public ViewPager2(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new PricFragment();
            case 1:
                return new CalcFragment();
            default:
                return new PricFragment();
        }

    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
