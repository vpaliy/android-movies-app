package com.popularmovies.vpaliy.popularmoviesapp.ui.eventBus.events;

import android.support.annotation.NonNull;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.wrapper.TransitionWrapper;

public class ExposeDetailsEvent {

    private TransitionWrapper wrapper;

    public ExposeDetailsEvent(@NonNull TransitionWrapper wrapper){
        this.wrapper=wrapper;
    }

    public TransitionWrapper getWrapper() {
        return wrapper;
    }
}
