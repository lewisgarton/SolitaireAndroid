package com.example.lewis.solitairtest;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class ScreensPagerAdapter extends FragmentPagerAdapter {
    private final ArrayList<Fragment> fragmentList;

    public ScreensPagerAdapter(FragmentManager fm) {
        super(fm);
        fragmentList = new ArrayList<>();
    }

    public void addFragment(Fragment fragment){
        fragmentList.add(fragment);
    }

    @Override
    public Fragment getItem(int i) {
        return fragmentList.get(i);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public void setFragment(int i, Fragment fragment){
        fragmentList.remove(i);
        fragmentList.add(i, fragment);
    }
}
