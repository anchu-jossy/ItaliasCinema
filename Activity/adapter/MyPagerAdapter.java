package com.ItaliasCinemas.ajit.Italiascinema.Activity.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ItaliasCinemas.ajit.Italiascinema.Activity.fragments.FeatureFragment;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.fragments.LatestFragment;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.fragments.RecentFragment;
import com.ItaliasCinemas.ajit.Italiascinema.Activity.fragments.TrendingFragment;

public class MyPagerAdapter extends FragmentPagerAdapter  {
    private static int NUM_ITEMS = 4;
    Context context;
    private String tabTitles[] = new String[]{"Feature", "Trending", "Latest", "Recent"};

    public MyPagerAdapter(FragmentManager fragmentManager, Context context) {

        super(fragmentManager);
        this.context = context;


    }

    @Override
    public int getItemPosition(Object object) {


        return POSITION_NONE;
    }


    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    // Returns the fragment to display for a particular page.
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FeatureFragment();

            case 1:
                return new TrendingFragment();

            case 2:
                return new LatestFragment();
            case 3:
                return new RecentFragment();

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