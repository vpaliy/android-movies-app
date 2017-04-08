package com.popularmovies.vpaliy.data.repository;

import android.support.annotation.NonNull;

import com.popularmovies.vpaliy.data.entity.Movie;
import com.popularmovies.vpaliy.data.entity.MovieDetailEntity;
import com.popularmovies.vpaliy.data.mapper.Mapper;
import com.popularmovies.vpaliy.data.source.DataSource;
import com.popularmovies.vpaliy.domain.IMovieRepository;
import com.popularmovies.vpaliy.domain.ISortConfiguration;
import com.popularmovies.vpaliy.domain.model.MovieCover;
import com.popularmovies.vpaliy.domain.model.MovieDetails;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class MovieRepository implements IMovieRepository<MovieCover,MovieDetails> {

    private static final String TAG=MovieRepository.class.getSimpleName();

    private final DataSource<Movie, MovieDetailEntity> dataSource;
    private final Mapper<MovieCover, Movie> entityMapper;
    private final Mapper<MovieDetails, MovieDetailEntity> detailsMapper;
    private final ISortConfiguration sortConfiguration;


    @Inject
    public MovieRepository(@NonNull DataSource<Movie, MovieDetailEntity> dataSource,
                           @NonNull Mapper<MovieCover, Movie> entityMapper,
                           @NonNull Mapper<MovieDetails, MovieDetailEntity> detailsMapper,
                           @NonNull ISortConfiguration sortConfiguration) {
        this.dataSource = dataSource;
        this.entityMapper = entityMapper;
        this.detailsMapper = detailsMapper;
        this.sortConfiguration = sortConfiguration;
    }

    @Override
    public Observable<List<MovieCover>> getCovers() {
        return dataSource.getCovers()
                .map(entityMapper::map);
    }

    @Override
    public Observable<MovieDetails> getDetails(int ID) {
        return dataSource.getDetails(ID)
                .map(detailsMapper::map);
    }

    @Override
    public Observable<MovieCover> getCover(int ID) {
        return dataSource.getCover(ID)
                .map(entityMapper::map);
    }

    @Override
    public Observable<List<MovieCover>> requestMoreCovers() {
        return dataSource.requestMoreCovers()
                .map(entityMapper::map);
    }


    @Override
    public Observable<MovieCover> sortBy(@NonNull ISortConfiguration.SortType type) {
        return null;
    }
}