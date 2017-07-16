package com.popularmovies.vpaliy.popularmoviesapp.ui.actor;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.popularmovies.vpaliy.popularmoviesapp.App;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.di.component.DaggerViewComponent;
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BaseActivity;

public class ActorActivity extends BaseActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actor);


    }

    @Override
    public void inject() {
        App.appInstance().appComponent().inject(this);
    }

    @Override
    public void handleEvent(@NonNull Object event) {

    }
}
