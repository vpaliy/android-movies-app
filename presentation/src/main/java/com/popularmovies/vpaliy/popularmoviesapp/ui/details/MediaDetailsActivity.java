package com.popularmovies.vpaliy.popularmoviesapp.ui.details;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;

import com.popularmovies.vpaliy.popularmoviesapp.App;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BaseActivity;
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.bus.events.ExposeEvent;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.Constants;


public class MediaDetailsActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_details);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        if (savedInstanceState == null) {
            savedInstanceState=getIntent().getExtras();
            setEnterTransition(savedInstanceState);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame,MediaDetailsFragment.newInstance(savedInstanceState),Constants.MOVIE_DETAILS_TAG)
                    .commit();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setEnterTransition(Bundle args){
        if(args!=null){
            int res=args.getInt(Constants.EXTRA_TRANSITION_ID,-1);
            if(res!=-1){
                Transition transition= TransitionInflater.from(this)
                        .inflateTransition(res);
                getWindow().setEnterTransition(transition);
                transition=TransitionInflater.from(this)
                        .inflateTransition(R.transition.details_shared_poster_exit);
                getWindow().setSharedElementReturnTransition(transition);
            }
        }
    }

    @Override
    public void onBackPressed() {
        Fragment fragment=getSupportFragmentManager().findFragmentByTag(Constants.MOVIE_DETAILS_TAG);
        if(fragment!=null){
            MovieDetailsFragment detailsFragment=MovieDetailsFragment.class.cast(fragment);
            if(detailsFragment.isDialogOn()){
                detailsFragment.turnOffDialog();
                return;
            }
        }
        super.onBackPressed();
    }

    @Override
    public void inject() {
        App.appInstance().appComponent().inject(this);
    }

    @Override
    public void handleEvent(@NonNull Object event) {
        disposables.clear();
        navigator.navigate(this, ExposeEvent.class.cast(event));
    }
}
