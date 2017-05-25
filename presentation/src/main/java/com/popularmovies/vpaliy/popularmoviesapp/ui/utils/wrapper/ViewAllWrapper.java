package com.popularmovies.vpaliy.popularmoviesapp.ui.utils.wrapper;

import android.support.annotation.NonNull;
import com.popularmovies.vpaliy.domain.configuration.SortType;

public class ViewAllWrapper {

    @NonNull
    public final SortType sortType;

    @NonNull
    public final MediaType mediaType;

    private ViewAllWrapper(@NonNull SortType sortType,
                           @NonNull MediaType mediaType){
        this.sortType=sortType;
        this.mediaType=mediaType;
    }

    public static ViewAllWrapper wrap(@NonNull SortType sortType,
                                      @NonNull MediaType mediaType){
        return new ViewAllWrapper(sortType,mediaType);
    }

}
