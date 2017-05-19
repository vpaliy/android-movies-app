package com.popularmovies.vpaliy.data.repository;


import android.support.annotation.NonNull;

import com.popularmovies.vpaliy.data.entity.TvShow;
import com.popularmovies.vpaliy.data.entity.TvShowDetailEntity;
import com.popularmovies.vpaliy.data.source.MediaDataSource;
import com.popularmovies.vpaliy.domain.IMovieRepository;
import com.popularmovies.vpaliy.domain.configuration.ISortConfiguration;
import com.popularmovies.vpaliy.domain.model.MediaCover;
import com.popularmovies.vpaliy.domain.model.TVShowDetails;
import java.util.List;
import rx.Observable;

public class TvShowRepository implements IMovieRepository<MediaCover,TVShowDetails> {

    private MediaDataSource<TvShow,TvShowDetailEntity> localDataSource;
    private MediaDataSource<TvShow,TvShowDetailEntity> remoteDataSource;

    @Override
    public Observable<List<MediaCover>> getCovers(@NonNull ISortConfiguration.SortType type) {
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
    public void update(MediaCover item, @NonNull ISortConfiguration.SortType sortType) {

    }

    @Override
    public boolean isType(int movieId, ISortConfiguration.SortType sortType) {
        return false;
    }

    @Override
    public Observable<List<MediaCover>> requestMoreCovers(@NonNull ISortConfiguration.SortType type) {
        return null;
    }

}