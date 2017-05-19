package com.popularmovies.vpaliy.data.source.local;


import android.support.annotation.NonNull;

import com.popularmovies.vpaliy.data.source.MediaDataSource;
import com.popularmovies.vpaliy.domain.configuration.ISortConfiguration;
import com.popularmovies.vpaliy.domain.model.MediaCover;
import com.popularmovies.vpaliy.domain.model.TVShowDetails;

import java.util.List;

import rx.Observable;

public class TVShowLocalSource extends MediaDataSource<MediaCover,TVShowDetails> {

    @Override
    public Observable<List<MediaCover>> getCovers(@NonNull ISortConfiguration.SortType type) {
        return null;
    }

    @Override
    public Observable<MediaCover> getCover(int id) {
        return null;
    }

    @Override
    public Observable<List<MediaCover>> requestMoreCovers(@NonNull ISortConfiguration.SortType type) {
        return null;
    }

    @Override
    public boolean isType(int id, ISortConfiguration.SortType sortType) {
        return false;
    }

    @Override
    public void insert(MediaCover item, ISortConfiguration.SortType sortType) {

    }

    @Override
    public void insertDetails(TVShowDetails details) {

    }

    @Override
    public Observable<TVShowDetails> getDetails(int id) {
        return null;
    }

    @Override
    public void update(MediaCover item, @NonNull ISortConfiguration.SortType sortType) {

    }
}
