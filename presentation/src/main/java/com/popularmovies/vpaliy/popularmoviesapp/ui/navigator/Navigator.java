package com.popularmovies.vpaliy.popularmoviesapp.ui.navigator;


import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.util.Log;
import com.popularmovies.vpaliy.popularmoviesapp.ui.activity.DetailsActivity;
import com.popularmovies.vpaliy.popularmoviesapp.ui.eventBus.events.ExposeDetailsEvent;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.Constants;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.Permission;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.wrapper.TransitionWrapper;
import android.support.annotation.NonNull;

public class Navigator {

    private static final String TAG=Navigator.class.getSimpleName();

    public void showDetails(@NonNull Activity activity, @NonNull ExposeDetailsEvent event){
        Intent intent=new Intent(activity,DetailsActivity.class);
        TransitionWrapper wrapper=event.getWrapper();
        intent.putExtras(wrapper.getData());
        if(Permission.checkForVersion(Build.VERSION_CODES.LOLLIPOP)){
            intent.putExtra(Constants.EXTRA_TRANSITION_NAME,wrapper.getImage().getTransitionName());
            ActivityOptionsCompat options=ActivityOptionsCompat
                    .makeSceneTransitionAnimation(activity,wrapper.getImage(),wrapper.getImage().getTransitionName());
            activity.startActivity(intent,options.toBundle());
        }else{
            activity.startActivity(intent);
        }

    }
}
