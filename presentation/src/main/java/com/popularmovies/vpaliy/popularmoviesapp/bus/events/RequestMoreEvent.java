package com.popularmovies.vpaliy.popularmoviesapp.bus.events;


import com.popularmovies.vpaliy.domain.configuration.SortType;

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
