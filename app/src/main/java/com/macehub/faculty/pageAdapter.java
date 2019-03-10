package com.macehub.faculty;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class pageAdapter extends FragmentStatePagerAdapter {
    int mNoOfTabs;

    public int getItemPosition(Object obj) {
        return -2;
    }

    public pageAdapter(FragmentManager fragmentManager, int i) {
        super(fragmentManager);
        this.mNoOfTabs = i;
    }

    public pageAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new Current_Staffs();
            case 1:
                return new Retired_Staffs();
            default:
                return null;
        }
    }

    public int getCount() {
        return this.mNoOfTabs;
    }
}
