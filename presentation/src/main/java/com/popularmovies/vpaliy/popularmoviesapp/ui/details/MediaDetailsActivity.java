package com.popularmovies.vpaliy.popularmoviesapp.ui.details;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.popularmovies.vpaliy.popularmoviesapp.App;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BaseActivity;

public class MediaDetailsActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_details);
        if (savedInstanceState == null) {
            savedInstanceState=getIntent().getExtras();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame,MediaDetailsFragment.newInstance(savedInstanceState))
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
