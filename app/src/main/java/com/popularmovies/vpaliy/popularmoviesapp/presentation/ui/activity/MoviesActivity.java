package com.popularmovies.vpaliy.popularmoviesapp.presentation.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.presentation.App;
import com.popularmovies.vpaliy.popularmoviesapp.presentation.ui.fragment.MoviesFragment;
import com.popularmovies.vpaliy.popularmoviesapp.presentation.ui.utils.events.ExposeDetailsEvent;
import com.squareup.otto.Subscribe;

import static com.popularmovies.vpaliy.popularmoviesapp.presentation.ui.utils.Constants.MOVIES_TAG;

public class MoviesActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        setUI();
    }

    private void setUI(){
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.movies,new MoviesFragment(), MOVIES_TAG)
                .commit();
    }

    @Override
    void inject() {
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

    }

}
