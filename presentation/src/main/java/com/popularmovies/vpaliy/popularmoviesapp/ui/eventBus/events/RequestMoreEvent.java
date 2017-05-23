package com.popularmovies.vpaliy.popularmoviesapp.ui.eventBus.events;


import com.popularmovies.vpaliy.domain.configuration.ISortConfiguration.SortType;

public class RequestMoreEvent {

    private SortType sortType;

    public RequestMoreEvent(SortType sortType){
        this.sortType=sortType;
    }

    public SortType sortType() {
        return sortType;
    }

    public static RequestMoreEvent createRequest(SortType sortType){
        return new RequestMoreEvent(sortType);
    }
}
