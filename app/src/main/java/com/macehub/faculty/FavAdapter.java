package com.macehub.faculty;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class FavAdapter extends FragmentStatePagerAdapter {
    int mNoOfTabs;

    public FavAdapter(FragmentManager fragmentManager, int i) {
        super(fragmentManager);
        this.mNoOfTabs = i;
    }

    public FavAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    public Fragment getItem(int i) {
        return i != 0 ? null : new favourites();
    }

    public int getCount() {
        return this.mNoOfTabs;
    }
}
