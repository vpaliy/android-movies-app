package com.popularmovies.vpaliy.popularmoviesapp.ui.season;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.popularmovies.vpaliy.popularmoviesapp.App;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BaseActivity;

public class SeasonActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_season);
        if(savedInstanceState==null){
            savedInstanceState=getIntent().getExtras();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame,SeasonFragment.newInstance(savedInstanceState))
                    .commit();
        }

    }

    @Override
    public void inject() {
        App.appInstance().appComponent().inject(this);
    }

    @Override
    public void handleEvent(@NonNull Object event) {

    }
}
