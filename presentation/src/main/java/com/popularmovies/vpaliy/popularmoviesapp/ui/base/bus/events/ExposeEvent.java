package com.popularmovies.vpaliy.popularmoviesapp.ui.base.bus.events;

import android.os.Bundle;
import android.support.v4.util.Pair;
import android.view.View;


public class ExposeEvent {

    public static final int CODE_MOVIE_DETAILS =1;
    public static final int CODE_ACTOR_DETAILS=2;
    public static final int CODE_TV_DETAILS=3;

    public final Bundle data;
    public final Pair<View,String>[] pack;
    public final int code;

    private ExposeEvent(Bundle data, Pair<View,String>[] pack, int code){
        this.data=data;
        this.pack=pack;
        this.code=code;
    }

    public static ExposeEvent exposeMovieDetails(Bundle data, Pair<View,String>... pack){
        return new ExposeEvent(data,pack, CODE_MOVIE_DETAILS);
    }

    public static ExposeEvent exposeActorDetails(Bundle data, Pair<View,String>... pack){
        return new ExposeEvent(data,pack,CODE_ACTOR_DETAILS);
    }

    public static ExposeEvent exposeTvShowDetails(Bundle data, Pair<View,String>... pack){
        return new ExposeEvent(data,pack,CODE_TV_DETAILS);
    }
}
