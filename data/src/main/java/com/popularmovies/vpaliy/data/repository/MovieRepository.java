package com.popularmovies.vpaliy.data.repository;

import com.google.common.cache.CacheBuilder;
import com.popularmovies.vpaliy.data.cache.CacheStore;
import com.popularmovies.vpaliy.data.entity.Movie;
import com.popularmovies.vpaliy.data.entity.MovieDetailEntity;
import com.popularmovies.vpaliy.data.mapper.Mapper;
import com.popularmovies.vpaliy.data.source.DataSource;
import com.popularmovies.vpaliy.data.source.qualifier.MovieLocal;
import com.popularmovies.vpaliy.data.source.qualifier.MovieRemote;
import com.popularmovies.vpaliy.domain.IMovieRepository;
import com.popularmovies.vpaliy.domain.ISortConfiguration;
import com.popularmovies.vpaliy.domain.model.MovieCover;
import com.popularmovies.vpaliy.domain.model.MovieDetails;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;
import android.support.annotation.NonNull;
import rx.Observable;

@Singleton
public class MovieRepository implements IMovieRepository<MovieCover,MovieDetails> {

    private static final String TAG=MovieRepository.class.getSimpleName();

    private static final int COVERS_CACHE_SIZE=100;
    private static final int DETAILS_CACHE_SIZE=100;

    private final DataSource<Movie, MovieDetailEntity> remoteDataSource;
    private final DataSource<Movie,MovieDetailEntity> localDataSource;
    private final Mapper<MovieCover, Movie> entityMapper;
    private final Mapper<MovieDetails, MovieDetailEntity> detailsMapper;

    private final CacheStore<Integer,MovieCover> coversCache;
    private final CacheStore<Integer,MovieDetails> detailsCache;

    @Inject
    public MovieRepository(@NonNull @MovieRemote DataSource<Movie, MovieDetailEntity> remoteDataSource,
                           @NonNull @MovieLocal DataSource<Movie,MovieDetailEntity> localDataSource,
                           @NonNull Mapper<MovieCover, Movie> entityMapper,
                           @NonNull Mapper<MovieDetails, MovieDetailEntity> detailsMapper) {
        this.remoteDataSource = remoteDataSource;
        this.localDataSource=localDataSource;
        this.entityMapper = entityMapper;
        this.detailsMapper = detailsMapper;
        this.coversCache=new CacheStore<>(CacheBuilder.newBuilder()
                .maximumSize(COVERS_CACHE_SIZE)
                .expireAfterAccess(20,TimeUnit.MINUTES)
                .build());
        this.detailsCache=new CacheStore<>(CacheBuilder.newBuilder()
                .maximumSize(DETAILS_CACHE_SIZE)
                .expireAfterAccess(20,TimeUnit.MINUTES)
                .build());

    }

    @Override
    public Observable<List<MovieCover>> getCovers() {
        return remoteDataSource.getCovers()
                .map(entityMapper::map)
                .doOnNext(movies->Observable.from(movies)
                        .filter(cover->!coversCache.isInCache(cover.getMovieId()))
                        .subscribe(movieCover -> coversCache.put(movieCover.getMovieId(),movieCover)));

    }

    @Override
    public Observable<MovieDetails> getDetails(int ID) {
        if(!detailsCache.isInCache(ID)) {
            return remoteDataSource.getDetails(ID)
                    .map(detailsMapper::map)
                    .doOnNext(details -> detailsCache.put(ID,details));
        }
        return detailsCache.getStream(ID);
    }

    @Override
    public Observable<MovieCover> getCover(int ID) {
        if(!coversCache.isInCache(ID)) {
            return remoteDataSource.getCover(ID)
                    .map(entityMapper::map)
                    .doOnNext(movie->coversCache.put(ID,movie));
        }
        return coversCache.getStream(ID);
    }

    @Override
    public Observable<List<MovieCover>> requestMoreCovers() {
        return remoteDataSource.requestMoreCovers()
                .map(entityMapper::map)
                .doOnNext(movies->Observable.from(movies)
                        .filter(cover->!coversCache.isInCache(cover.getMovieId()))
                        .subscribe(movieCover -> coversCache.put(movieCover.getMovieId(),movieCover)));

    }


    @Override
    public void update(MovieCover item) {
        localDataSource.update(entityMapper.reverseMap(item));
    }

    @Override
    public Observable<List<MovieCover>> sortBy(@NonNull ISortConfiguration.SortType type) {
        return remoteDataSource.sortBy(type)
                .map(entityMapper::map)
                .doOnNext(movies->Observable.from(movies)
                        .filter(cover->!coversCache.isInCache(cover.getMovieId()))
                        .subscribe(movieCover -> coversCache.put(movieCover.getMovieId(),movieCover)));
    }
}