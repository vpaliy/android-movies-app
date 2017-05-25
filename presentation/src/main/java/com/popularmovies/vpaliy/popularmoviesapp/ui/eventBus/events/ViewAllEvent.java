package com.popularmovies.vpaliy.popularmoviesapp.ui.eventBus.events;


import android.os.Bundle;
import android.support.annotation.NonNull;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.Constants;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.wrapper.ViewAllWrapper;

public class ViewAllEvent {

    private  final ViewAllWrapper wrapper;

    public ViewAllEvent(@NonNull ViewAllWrapper wrapper){
        this.wrapper=wrapper;
    }


    public Bundle getExtras(){
        Bundle extras=new Bundle();
        extras.putString(Constants.EXTRA_SORT_TYPE,wrapper.sortType.name());
        extras.putString(Constants.EXTRA_MEDIA_TYPE,wrapper.mediaType.name());
        return extras;
    }
}
