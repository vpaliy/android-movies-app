package com.popularmovies.vpaliy.popularmoviesapp.presentation.ui.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.presentation.App;

public class DetailsActivity extends BaseActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        setUI();
    }

    private void setUI(){
        FragmentManager manager=getSupportFragmentManager();

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

}
