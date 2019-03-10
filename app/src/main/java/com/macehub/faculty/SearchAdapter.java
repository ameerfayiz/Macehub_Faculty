package com.macehub.faculty;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class SearchAdapter extends FragmentStatePagerAdapter {
    int mNoOfTabs;
    String query = BuildConfig.FLAVOR;

    public SearchAdapter(FragmentManager fragmentManager, int i, String str) {
        super(fragmentManager);
        this.mNoOfTabs = i;
        this.query = str;
    }

    public SearchAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    public Fragment getItem(int i) {
        if (i != 0) {
            return null;
        }
        Bundle bundle = new Bundle();
        bundle.putString("query", this.query);
        Search search = new Search();
        search.setArguments(bundle);
        return search;
    }

    public int getCount() {
        return this.mNoOfTabs;
    }
}
