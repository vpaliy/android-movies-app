package com.popularmovies.vpaliy.popularmoviesapp.ui.utils.events;

import android.support.annotation.NonNull;

import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.wrapper.TransitionWrapper;

public class ClickedMovieEvent {

    private final TransitionWrapper wrapper;

    public ClickedMovieEvent(@NonNull TransitionWrapper wrapper){
        this.wrapper=wrapper;
    }

    public TransitionWrapper getTransitionWrapper() {
        return wrapper;
    }
}
