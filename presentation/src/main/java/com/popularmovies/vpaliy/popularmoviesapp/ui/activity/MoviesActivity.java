package com.popularmovies.vpaliy.popularmoviesapp.ui.activity;

import android.os.Bundle;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.App;
import com.popularmovies.vpaliy.popularmoviesapp.ui.fragment.MoviesFragment;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.events.ExposeDetailsEvent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.squareup.otto.Subscribe;

import static com.popularmovies.vpaliy.popularmoviesapp.ui.utils.Constants.MOVIES_TAG;

public class MoviesActivity extends BaseActivity {

    private static final String TAG=MoviesActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        if(savedInstanceState==null) {
            setUI();
        }
    }

    private void setUI(){
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.movies,new MoviesFragment(), MOVIES_TAG)
                .commit();
    }


    @Override
    void inject() {;
        App.appInstance().appComponent().inject(this);
    }

    @Override
    void register() {
        eventBus.register(this);
    }


    @Override
    void unregister() {
        eventBus.unregister(this);
    }

    @Subscribe
    public void showDetails(@NonNull ExposeDetailsEvent event){
        navigator.showDetails(this,event);

    }

}
