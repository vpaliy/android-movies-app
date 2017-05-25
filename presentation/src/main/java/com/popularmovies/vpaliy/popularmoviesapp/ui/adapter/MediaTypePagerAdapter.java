package com.popularmovies.vpaliy.popularmoviesapp.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;

import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MediaContract;
import com.popularmovies.vpaliy.popularmoviesapp.ui.fragment.MoviesFragment;
import com.popularmovies.vpaliy.popularmoviesapp.ui.fragment.PersonalFragment;
import com.popularmovies.vpaliy.popularmoviesapp.ui.fragment.TvShowsFragment;

public class MediaTypePagerAdapter extends FragmentStatePagerAdapter {

    private SparseArray<MediaContract.View> views;
    private Context context;

    public MediaTypePagerAdapter(FragmentManager manager, Context context){
        super(manager);
        this.context=context;
        views=new SparseArray<>();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Movies";
            case 1:
                return "TV";
            case 2:
                return "Personal";
            case 3:
                return "Tv";
        }
        return super.getPageTitle(position);
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new MoviesFragment();
            case 1:
                return new TvShowsFragment();
            case 2:
                return new PersonalFragment();
            case 3:
                return new TvShowsFragment();
        }
        return null;
    }

    public MediaContract.View viewAt(int index){
        return views.get(index);
    }
}
