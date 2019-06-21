package com.ItaliasCinemas.ajit.Italiascinema.Activity.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ItaliasCinemas.ajit.Italiascinema.Activity.fragments.SearchFragment;

public class SearchPagerAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 1;
    private String tabTitles[] = new String[]{"Movies"};

    public SearchPagerAdapter(FragmentManager fragmentManager) {

        super(fragmentManager);




    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    // Returns total number of pages.
    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    // Returns the fragment to display for a particular page.
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new SearchFragment();
                default:
                return null;
        }
    }


    // Returns the page title for the top indicator
    @Override
    public CharSequence getPageTitle(int position) {

        return tabTitles[position];
    }

}