package com.popularmovies.vpaliy.popularmoviesapp.presentation.ui.utils.events;

import android.support.annotation.NonNull;

import com.popularmovies.vpaliy.popularmoviesapp.presentation.ui.utils.wrapper.TransitionWrapper;

public class ExposeDetailsEvent {

    private TransitionWrapper wrapper;

    public ExposeDetailsEvent(@NonNull TransitionWrapper wrapper){
        this.wrapper=wrapper;
    }

    public TransitionWrapper getWrapper() {
        return wrapper;
    }
}
