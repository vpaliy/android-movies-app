package com.popularmovies.vpaliy.popularmoviesapp.ui.media;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.ui.media.MoviesFragment;
import com.popularmovies.vpaliy.popularmoviesapp.ui.media.PersonalFragment;
import com.popularmovies.vpaliy.popularmoviesapp.ui.media.TvShowsFragment;

public class MediaTypePagerAdapter extends FragmentStatePagerAdapter {

    private Context context;

    public MediaTypePagerAdapter(FragmentManager manager, Context context){
        super(manager);
        this.context=context;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return context.getString(R.string.movies);
            case 1:
                return context.getString(R.string.tv_shows);
            case 2:
                return context.getString(R.string.personal);
        }
        return super.getPageTitle(position);
    }

    @Override
    public int getCount() {
        return 3;
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
        }
        return null;
    }

}
