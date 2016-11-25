package com.shtainyky.backinghelper.utils;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.shtainyky.backinghelper.ListThemesFragment;

public class TabPagerFragmentAdapter extends FragmentPagerAdapter {
    private String[] tabs;
    private Context context;

    public TabPagerFragmentAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
        tabs = new String[]{"Tab1", "Tab2", "Tab3", "Tab4", "Tab5", "Tab6"};

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new ListThemesFragment(0);
            case 1:
                return new ListThemesFragment(1);
            case 2:
                return new ListThemesFragment(2);
            case 3:
                return new ListThemesFragment(3);
            case 4:
                return new ListThemesFragment(4);
            case 5:
                return new ListThemesFragment(5);
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabs.length;
    }


}
