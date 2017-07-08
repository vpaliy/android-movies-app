package com.popularmovies.vpaliy.popularmoviesapp.ui.details;

import android.os.Bundle;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.App;
import com.popularmovies.vpaliy.popularmoviesapp.bus.events.ExposeDetailsEvent;
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BaseActivity;
import com.popularmovies.vpaliy.popularmoviesapp.ui.details.fragment.MovieDetailsFragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import static com.popularmovies.vpaliy.popularmoviesapp.ui.utils.Constants.EXTRA_DATA;
import static com.popularmovies.vpaliy.popularmoviesapp.ui.utils.Constants.MOVIE_DETAILS_TAG;

public class DetailsActivity extends BaseActivity {

    private Bundle extraData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportPostponeEnterTransition();
        setContentView(R.layout.activity_movie_details);
        if(savedInstanceState==null){
            extraData=getIntent().getExtras();
            setUI();
        }else{
            extraData=savedInstanceState.getBundle(EXTRA_DATA);
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
    public void inject() {
        App.appInstance().appComponent().inject(this);
    }

    @Override
    public void handleEvent(@NonNull Object event) {
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
