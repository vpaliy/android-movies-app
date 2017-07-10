package com.popularmovies.vpaliy.popularmoviesapp.ui.navigator;


import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;

import com.popularmovies.vpaliy.popularmoviesapp.ui.base.bus.events.ExposeEvent;
import com.popularmovies.vpaliy.popularmoviesapp.ui.details.DetailsActivity;
import com.popularmovies.vpaliy.popularmoviesapp.ui.details.Dummy;
import com.popularmovies.vpaliy.popularmoviesapp.ui.more.MoreMediaActivity;
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.bus.events.ExposeDetailsEvent;
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.bus.events.ViewAllEvent;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.Constants;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.Permission;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.wrapper.TransitionWrapper;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;
import android.view.View;

public class Navigator {

    private static final String TAG=Navigator.class.getSimpleName();

    public void showDetails(@NonNull Activity activity, @NonNull ExposeDetailsEvent event){
        Intent intent=new Intent(activity,Dummy.class);
        TransitionWrapper wrapper=event.getWrapper();
        intent.putExtras(wrapper.getData());
        if(Permission.checkForVersion(Build.VERSION_CODES.LOLLIPOP)){
            ActivityOptionsCompat options=ActivityOptionsCompat
                    .makeSceneTransitionAnimation(activity,wrapper.getImage(), wrapper.getImage().getTransitionName());
            activity.startActivity(intent,options.toBundle());
        }else{
            activity.startActivity(intent);
        }

    }

    public void navigate(@NonNull Activity activity, @NonNull ExposeEvent event){
        Intent intent=new Intent(activity,Dummy.class);
        intent.putExtras(event.data);
        if(Permission.checkForVersion(Build.VERSION_CODES.LOLLIPOP)){
            ActivityOptionsCompat optionsCompat=ActivityOptionsCompat
                    .makeSceneTransitionAnimation(activity,event.pack);
            activity.startActivity(intent,optionsCompat.toBundle());
            return;
        }
        activity.startActivity(intent);
    }

    public void viewAll(@NonNull Activity activity, @NonNull ViewAllEvent event){
        Intent intent=new Intent(activity, MoreMediaActivity.class);
        intent.putExtras(event.getExtras());
        activity.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
    }
}
