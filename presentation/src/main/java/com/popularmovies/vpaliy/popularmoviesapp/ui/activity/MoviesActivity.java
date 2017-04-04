package com.popularmovies.vpaliy.popularmoviesapp.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.App;
import com.popularmovies.vpaliy.popularmoviesapp.ui.fragment.MoviesFragment;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.Permission;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.events.ExposeDetailsEvent;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.wrapper.TransitionWrapper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.squareup.otto.Subscribe;

import static com.popularmovies.vpaliy.popularmoviesapp.ui.utils.Constants.MOVIES_TAG;

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
        Intent intent=new Intent(this,DetailsActivity.class);
        TransitionWrapper wrapper=event.getWrapper();
        intent.putExtras(wrapper.getData());
        if(Permission.checkForVersion(Build.VERSION_CODES.LOLLIPOP)){
            ActivityOptionsCompat options=ActivityOptionsCompat
                    .makeSceneTransitionAnimation(this,wrapper.getImage(),wrapper.getImage().getTransitionName());
            this.startActivity(intent,options.toBundle());
        }else{
            this.startActivity(intent);
        }

    }

}
