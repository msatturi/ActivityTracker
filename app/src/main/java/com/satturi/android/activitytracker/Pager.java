package com.satturi.android.activitytracker;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class Pager extends FragmentStatePagerAdapter {
    int tabCount;
    public Pager(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount= tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0 :
                Profile p=new Profile();
                return p;
            case 1:
                WeightEntry weightEntry=new WeightEntry();
                return weightEntry;
            case 2:
                Statistics s=new Statistics();
                return s;
            case 3:
                History h=new History();
                return h;

            default: return null;        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }

}
