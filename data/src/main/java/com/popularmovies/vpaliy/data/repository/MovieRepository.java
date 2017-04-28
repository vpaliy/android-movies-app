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

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

    private final Context context;

    @Inject
    public MovieRepository(@NonNull @MovieRemote DataSource<Movie, MovieDetailEntity> remoteDataSource,
                           @NonNull @MovieLocal DataSource<Movie,MovieDetailEntity> localDataSource,
                           @NonNull Mapper<MovieCover, Movie> entityMapper,
                           @NonNull Mapper<MovieDetails, MovieDetailEntity> detailsMapper,
                           @NonNull Context context) {
        this.context=context;
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
        if(isNetworkConnection()) {
            return remoteDataSource.getCovers()
                    .doOnNext(this::saveMovies)
                    .map(entityMapper::map)
                    .doOnNext(movies -> Observable.from(movies)
                            .filter(cover -> !coversCache.isInCache(cover.getMovieId()))
                            .subscribe(movieCover -> coversCache.put(movieCover.getMovieId(), movieCover)));
        }
        return localDataSource.getCovers()
                .map(entityMapper::map)
                .doOnNext(movies -> Observable.from(movies)
                        .filter(cover -> !coversCache.isInCache(cover.getMovieId()))
                        .subscribe(movieCover -> coversCache.put(movieCover.getMovieId(), movieCover)));

    }

    private void saveMovies(List<Movie> movies){
        if(movies!=null){
            if(!movies.isEmpty()){
                for(Movie movie:movies) localDataSource.insert(movie);
            }
        }
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
            if(isNetworkConnection()) {
                return remoteDataSource.getCover(ID)
                        .map(entityMapper::map)
                        .doOnNext(movie -> coversCache.put(ID, movie));
            }
            return localDataSource.getCover(ID)
                    .map(entityMapper::map)
                    .doOnNext(movie -> coversCache.put(ID, movie));
        }
        return coversCache.getStream(ID);
    }

    @Override
    public Observable<List<MovieCover>> requestMoreCovers() {
        if(isNetworkConnection()) {
            return remoteDataSource.requestMoreCovers()
                    .doOnNext(this::saveMovies)
                    .map(entityMapper::map)
                    .doOnNext(movies -> Observable.from(movies)
                            .filter(cover -> !coversCache.isInCache(cover.getMovieId()))
                            .subscribe(movieCover -> coversCache.put(movieCover.getMovieId(), movieCover)));
        }
        return null;

    }


    @Override
    public void update(MovieCover item) {
        localDataSource.update(entityMapper.reverseMap(item));
    }

    @Override
    public Observable<List<MovieCover>> sortBy(@NonNull ISortConfiguration.SortType type) {
        switch (type){
            case FAVORITE:
                return localDataSource.sortBy(type)
                        .map(entityMapper::map)
                        .doOnNext(movies->Observable.from(movies)
                                .filter(cover->!coversCache.isInCache(cover.getMovieId()))
                                .subscribe(movieCover->coversCache.put(movieCover.getMovieId(),movieCover)));
            default:
                return remoteDataSource.sortBy(type)
                        .map(entityMapper::map)
                        .doOnNext(movies -> Observable.from(movies)
                                .filter(cover -> !coversCache.isInCache(cover.getMovieId()))
                                .subscribe(movieCover -> coversCache.put(movieCover.getMovieId(), movieCover)));

        }
    }

    private boolean isNetworkConnection(){
        ConnectivityManager manager=ConnectivityManager.class
                .cast(context.getSystemService(Context.CONNECTIVITY_SERVICE));
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        return activeNetwork.isConnectedOrConnecting();
    }
}