package com.popularmovies.vpaliy.data.source.remote;


import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutCompat;

import com.popularmovies.vpaliy.data.source.MediaDataSource;
import com.popularmovies.vpaliy.data.utils.scheduler.BaseSchedulerProvider;
import com.popularmovies.vpaliy.domain.IMediaRepository;
import com.popularmovies.vpaliy.domain.configuration.ISortConfiguration;
import com.popularmovies.vpaliy.domain.configuration.ISortConfiguration.SortType;
import com.popularmovies.vpaliy.domain.model.MediaCover;
import com.popularmovies.vpaliy.domain.model.TVShowDetails;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Observable;

public class RemoteTvShowSource extends MediaDataSource<MediaCover,TVShowDetails> {

    private final MovieDatabaseAPI movieDatabaseAPI;
    private final BaseSchedulerProvider schedulerProvider;
    private final Map<SortType,MediaStream> pageMap;

    @Inject
    public RemoteTvShowSource(@NonNull MovieDatabaseAPI movieDatabaseAPI,
                              @NonNull BaseSchedulerProvider schedulerProvider){
        this.movieDatabaseAPI=movieDatabaseAPI;
        this.schedulerProvider=schedulerProvider;
        this.pageMap=new HashMap<>();
    }

    @Override
    public Observable<List<MediaCover>> getCovers(@NonNull SortType type) {
        switch (type){

        }
        return null;
    }

    @Override
    public Observable<MediaCover> getCover(int id) {
        return null;
    }

    @Override
    public Observable<List<MediaCover>> requestMoreCovers(@NonNull SortType type) {
        return null;
    }

    @Override
    public boolean isType(int id, SortType sortType) {
        return false;
    }

    @Override
    public void insert(MediaCover item, SortType sortType) {

    }

    @Override
    public void insertDetails(TVShowDetails details) {

    }

    @Override
    public Observable<TVShowDetails> getDetails(int id) {
        return null;
    }

    @Override
    public void update(MediaCover item, @NonNull SortType sortType) {

    }

    private class MediaStream {
        private int currentPage;
        private int totalPages;
    }
}
