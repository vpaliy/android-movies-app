package com.popularmovies.vpaliy.popularmoviesapp.ui.activity;

import android.os.Bundle;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.App;
import com.popularmovies.vpaliy.popularmoviesapp.ui.eventBus.events.ExposeDetailsEvent;
import com.popularmovies.vpaliy.popularmoviesapp.ui.fragment.MoviesFragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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
    void inject() {
        App.appInstance().appComponent().inject(this);
    }

    @Override
    void handleEvent(@NonNull Object event) {
        if(event instanceof  ExposeDetailsEvent){
            showDetails(ExposeDetailsEvent.class.cast(event));
        }
    }

    private void showDetails(@NonNull ExposeDetailsEvent event){
        navigator.showDetails(this,event);

    }

}
