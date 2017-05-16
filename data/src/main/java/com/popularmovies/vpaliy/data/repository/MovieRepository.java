package com.popularmovies.vpaliy.data.repository;

import com.google.common.cache.CacheBuilder;
import com.popularmovies.vpaliy.data.cache.CacheStore;
import com.popularmovies.vpaliy.data.entity.Movie;
import com.popularmovies.vpaliy.data.entity.MovieDetailEntity;
import com.popularmovies.vpaliy.data.mapper.Mapper;
import com.popularmovies.vpaliy.data.source.MovieDataSource;
import com.popularmovies.vpaliy.data.source.qualifier.MovieLocal;
import com.popularmovies.vpaliy.data.source.qualifier.MovieRemote;
import com.popularmovies.vpaliy.domain.IMovieRepository;
import com.popularmovies.vpaliy.domain.configuration.ISortConfiguration;
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

import static com.popularmovies.vpaliy.domain.configuration.ISortConfiguration.SortType;

@Singleton
public class MovieRepository implements IMovieRepository<MovieCover,MovieDetails> {

    private static final String TAG=MovieRepository.class.getSimpleName();

    private static final int COVERS_CACHE_SIZE=100;
    private static final int DETAILS_CACHE_SIZE=100;

    private final MovieDataSource<Movie, MovieDetailEntity> remoteDataSource;
    private final MovieDataSource<Movie,MovieDetailEntity> localDataSource;
    private final Mapper<MovieCover, Movie> entityMapper;
    private final Mapper<MovieDetails, MovieDetailEntity> detailsMapper;

    private final CacheStore<Integer,MovieCover> coversCache;
    private final CacheStore<Integer,MovieDetails> detailsCache;

    private final Context context;

    @Inject
    public MovieRepository(@NonNull @MovieRemote MovieDataSource<Movie, MovieDetailEntity> remoteDataSource,
                           @NonNull @MovieLocal MovieDataSource<Movie,MovieDetailEntity> localDataSource,
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
    public Observable<List<MovieCover>> getCovers(@NonNull SortType sortType) {
        if(isNetworkConnection()) {
            Observable<List<Movie>> observable=remoteDataSource.getCovers(sortType);
            if(observable!=null) {
                    return observable.doOnNext(movies->saveMoviesToDisk(movies,sortType))
                        .map(entityMapper::map)
                        .doOnNext(movies -> Observable.from(movies)
                                .map(this::isFavorite)
                                .filter(cover -> !coversCache.isInCache(cover.getMovieId()))
                                .subscribe(movieCover -> coversCache.put(movieCover.getMovieId(), movieCover)));
            }
        }
        return localDataSource.getCovers(sortType)
                .map(entityMapper::map)
                .doOnNext(movies -> Observable.from(movies)
                        .map(this::isFavorite)
                        .filter(cover -> !coversCache.isInCache(cover.getMovieId()))
                        .subscribe(movieCover -> coversCache.put(movieCover.getMovieId(), movieCover)));
    }

    private void saveMoviesToDisk(List<Movie> movies, SortType sortType){
        if(movies!=null){
            if(!movies.isEmpty()){
                for(Movie movie:movies) localDataSource.insert(movie,sortType);
            }
        }
    }

    @Override
    public boolean isType(int movieId, SortType sortType) {
        return false;
    }

    private MovieCover isFavorite(MovieCover movie){
        //movie.setFavorite(localDataSource.isFavorite(movie.getMovieId()));
        return movie;
    }

    private MovieDetailEntity isFavorite(MovieDetailEntity detailEntity){
        //detailEntity.setFavorite(localDataSource.isFavorite(detailEntity.getMovieId()));
        return detailEntity;
    }

    @Override
    public synchronized Observable<MovieDetails> getDetails(int ID) {
        if(!detailsCache.isInCache(ID)) {
            if(isNetworkConnection()) {
                return remoteDataSource.getDetails(ID)
                        .map(this::isFavorite)
                        .doOnNext(localDataSource::insertDetails)
                        .map(detailsMapper::map)
                        .doOnNext(details -> detailsCache.put(ID, details));
            }
            return localDataSource.getDetails(ID)
                    .map(this::isFavorite)
                    .map(detailsMapper::map);
        }
        return detailsCache.getStream(ID);
    }

    @Override
    public Observable<MovieCover> getCover(int ID) {
        if(!coversCache.isInCache(ID)) {
            if(isNetworkConnection()) {
                return remoteDataSource.getCover(ID)
                        .map(entityMapper::map)
                        .map(this::isFavorite)
                        .doOnNext(movie -> coversCache.put(ID, movie));
            }
            return localDataSource.getCover(ID)
                    .map(entityMapper::map)
                    .map(this::isFavorite)
                    .doOnNext(movie -> coversCache.put(ID, movie));
        }
        return coversCache.getStream(ID);
    }


    @Override
    public Observable<List<MovieCover>> requestMoreCovers(@NonNull SortType sortType) {
        if(isNetworkConnection()) {
            return remoteDataSource.requestMoreCovers(sortType)
                    .doOnNext(movies->saveMoviesToDisk(movies,sortType))
                    .map(entityMapper::map)
                    .doOnNext(movies -> Observable.from(movies)
                            .map(this::isFavorite)
                            .filter(cover -> !coversCache.isInCache(cover.getMovieId()))
                            .subscribe(movieCover -> coversCache.put(movieCover.getMovieId(), movieCover)));
        }
        return Observable.just(null);

    }


    @Override
    public void update(MovieCover item, @NonNull SortType sortType) {
        localDataSource.update(entityMapper.reverseMap(item),sortType);
        if(sortType==SortType.FAVORITE) item.setFavorite(!item.isFavorite());
    }

    private boolean isNetworkConnection(){
        ConnectivityManager manager=ConnectivityManager.class
                .cast(context.getSystemService(Context.CONNECTIVITY_SERVICE));
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        return activeNetwork!=null && activeNetwork.isConnectedOrConnecting();
    }
}