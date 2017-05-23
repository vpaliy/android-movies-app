package com.popularmovies.vpaliy.popularmoviesapp.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;

import com.popularmovies.vpaliy.domain.configuration.ISortConfiguration.SortType;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MediaContract;
import com.popularmovies.vpaliy.popularmoviesapp.ui.fragment.MoviesFragment;

public class MovieTypeAdapter extends FragmentStatePagerAdapter {

    private SparseArray<MediaContract.View> views;
    private Context context;

    public MovieTypeAdapter(FragmentManager manager, Context context){
        super(manager);
        this.context=context;
        views=new SparseArray<>();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return context.getString(R.string.ByPopularity);
            case 1:
                return context.getString(R.string.ByTopRated);
            case 2:
                return context.getString(R.string.ByLatest);
            case 3:
                return context.getString(R.string.ByNowPlaying);
            case 4:
                return context.getString(R.string.ByUpcoming);
        }
        return super.getPageTitle(position);
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Fragment getItem(int position) {
        MoviesFragment moviesFragment=null;
        return moviesFragment;
    }

    public MediaContract.View viewAt(int index){
        return views.get(index);
    }
}
