package com.popularmovies.vpaliy.popularmoviesapp.presentation.ui.activity;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.presentation.App;
import com.popularmovies.vpaliy.popularmoviesapp.presentation.ui.fragment.MovieDetailsFragment;
import com.popularmovies.vpaliy.popularmoviesapp.presentation.ui.utils.Permission;

import static com.popularmovies.vpaliy.popularmoviesapp.presentation.ui.utils.Constants.EXTRA_DATA;
import static com.popularmovies.vpaliy.popularmoviesapp.presentation.ui.utils.Constants.MOVIE_DETAILS_TAG;

public class DetailsActivity extends BaseActivity{

    private Bundle extraData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Permission.checkForVersion(Build.VERSION_CODES.LOLLIPOP)){
            postponeEnterTransition();
        }

        if(Permission.checkForVersion(16)){
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_movie_details);
        if(savedInstanceState==null){
            extraData=getIntent().getExtras();
        }else{
            extraData=savedInstanceState.getBundle(EXTRA_DATA);
        }
        setUI();
    }

    private void setUI(){
        getSupportFragmentManager().beginTransaction()
                .add(R.id.movies, MovieDetailsFragment.newInstance(extraData),MOVIE_DETAILS_TAG)
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBundle(EXTRA_DATA,extraData);
        super.onSaveInstanceState(outState);
    }
}
