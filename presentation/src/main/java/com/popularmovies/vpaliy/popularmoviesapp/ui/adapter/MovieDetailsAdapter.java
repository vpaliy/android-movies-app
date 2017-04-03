package com.popularmovies.vpaliy.popularmoviesapp.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.popularmovies.vpaliy.popularmoviesapp.ui.fragment.MovieInfoFragment;


public class MovieDetailsAdapter extends FragmentStatePagerAdapter {

    private static final int INFO=0;
    private static final int CAST=1;

    private int movieId;


    public MovieDetailsAdapter(@NonNull FragmentManager manager, int movieId){
        super(manager);
        this.movieId=movieId;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case INFO:
                return new MovieInfoFragment();
            case CAST:
                return new MovieInfoFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }
}
