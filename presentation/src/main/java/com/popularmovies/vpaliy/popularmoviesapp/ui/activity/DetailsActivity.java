package com.popularmovies.vpaliy.popularmoviesapp.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.App;
import com.popularmovies.vpaliy.popularmoviesapp.bus.events.ExposeDetailsEvent;
import com.popularmovies.vpaliy.popularmoviesapp.ui.fragment.MovieDetailsFragment;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.Permission;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import static com.popularmovies.vpaliy.popularmoviesapp.ui.utils.Constants.EXTRA_DATA;
import static com.popularmovies.vpaliy.popularmoviesapp.ui.utils.Constants.MOVIE_DETAILS_TAG;

public class DetailsActivity extends BaseActivity {

    private Bundle extraData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTransition();
        setContentView(R.layout.activity_movie_details);
        if(savedInstanceState==null){
            extraData=getIntent().getExtras();
            setUI();
        }else{
            extraData=savedInstanceState.getBundle(EXTRA_DATA);
        }
    }

    private void initTransition(){
        if(Permission.checkForVersion(Build.VERSION_CODES.LOLLIPOP)){
            postponeEnterTransition();
        }
        if(Permission.checkForVersion(Build.VERSION_CODES.JELLY_BEAN)){
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }

    private void setUI(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.movies, MovieDetailsFragment.newInstance(extraData),MOVIE_DETAILS_TAG)
                .commit();
    }

    private void showDetails(@NonNull ExposeDetailsEvent event){
        navigator.showDetails(this,event);
    }

    @Override
    void inject() {
        App.appInstance().appComponent().inject(this);
    }

    @Override
    void handleEvent(@NonNull Object event) {
        if(event instanceof ExposeDetailsEvent){
            showDetails(ExposeDetailsEvent.class.cast(event));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBundle(EXTRA_DATA,extraData);
        super.onSaveInstanceState(outState);
    }
}
