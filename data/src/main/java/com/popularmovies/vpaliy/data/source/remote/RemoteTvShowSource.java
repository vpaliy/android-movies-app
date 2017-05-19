package com.popularmovies.vpaliy.data.source.remote;


import android.support.annotation.NonNull;

import com.popularmovies.vpaliy.domain.IMovieRepository;
import com.popularmovies.vpaliy.domain.configuration.ISortConfiguration.SortType;
import com.popularmovies.vpaliy.domain.model.MediaCover;
import com.popularmovies.vpaliy.domain.model.TVShowDetails;

import java.util.List;

import rx.Observable;

public class RemoteTvShowSource implements IMovieRepository<MediaCover,TVShowDetails>{


    @Override
    public Observable<List<MediaCover>> getCovers(@NonNull SortType type) {
        return null;
    }

    @Override
    public Observable<MediaCover> getCover(int movieId) {
        return null;
    }

    @Override
    public Observable<TVShowDetails> getDetails(int movieId) {
        return null;
    }

    @Override
    public void update(MediaCover item, @NonNull SortType sortType) {

    }

    @Override
    public boolean isType(int movieId, SortType sortType) {
        return false;
    }

    @Override
    public Observable<List<MediaCover>> requestMoreCovers(@NonNull SortType type) {
        return null;
    }

}
