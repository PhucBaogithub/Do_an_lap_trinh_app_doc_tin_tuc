package com.example.projectreaderapp.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import fragment.CategoryFragment;
import fragment.NewsFragment;
import fragment.TrendingFragment;
import fragment.UtilitiesFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new CategoryFragment();
            case 1:
                return new NewsFragment();
            case 2:
                return new TrendingFragment();
            case 3:
                return new UtilitiesFragment();
            default:
                return new NewsFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
