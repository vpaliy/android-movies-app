package com.popularmovies.vpaliy.data.source.remote;

import com.popularmovies.vpaliy.data.entity.Movie;
import com.popularmovies.vpaliy.data.entity.MovieDetailEntity;
import com.popularmovies.vpaliy.data.source.DataSource;
import com.popularmovies.vpaliy.data.source.remote.wrapper.BackdropsWrapper;
import com.popularmovies.vpaliy.data.source.remote.wrapper.CastWrapper;
import com.popularmovies.vpaliy.data.source.remote.wrapper.MovieWrapper;
import com.popularmovies.vpaliy.domain.ISortConfiguration;
import com.popularmovies.vpaliy.domain.model.MovieCover;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.schedulers.Schedulers;

import javax.inject.Inject;
import javax.inject.Singleton;
import android.support.annotation.NonNull;


@Singleton
public class RemoteSource extends DataSource<Movie,MovieDetailEntity> {

    private static final String MOVIE_URL_BASE="http://api.themoviedb.org/3/";
    private static final String API_KEY=exampleKEY # replace with 'exampleKEY' instead;

    private ISortConfiguration sortConfiguration;

    private MovieDatabaseAPI movieDatabaseAPI;


    @Inject
    public RemoteSource(@NonNull ISortConfiguration sortConfiguration){
        this.sortConfiguration=sortConfiguration;
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(MOVIE_URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        movieDatabaseAPI=retrofit.create(MovieDatabaseAPI.class);
    }

    @Override
    public Observable<List<Movie>> getCovers() {
        return movieDatabaseAPI.getPopularMovies(API_KEY)
                .map(MovieWrapper::getCoverList);
    }

    @Override
    public Observable<MovieDetailEntity> getDetails(int ID) {
        MovieDetailEntity movieDetailEntity=new MovieDetailEntity();

        movieDatabaseAPI.getMovieDetails(API_KEY,ID)
                .subscribeOn(Schedulers.io())
                .subscribe(movieDetailEntity::setMovie);
        movieDatabaseAPI.getSimilarMovies(API_KEY,ID)
                .subscribeOn(Schedulers.io())
                .map(MovieWrapper::getCoverList)
                .subscribe(movieDetailEntity::setSimilarMovies);
        movieDatabaseAPI.getBackdrops(API_KEY,ID)
                .subscribeOn(Schedulers.io())
                .map(BackdropsWrapper::getBackdropImages)
                .subscribe(movieDetailEntity::setBackdropImages);
        movieDatabaseAPI.getMovieCast(API_KEY,ID)
                .subscribeOn(Schedulers.io())
                .map(CastWrapper::getCast)
                .subscribe(movieDetailEntity::setCast);

        return Observable.just(movieDetailEntity);

    }

    @Override
    public Observable<Movie> getCover(int ID) {
        return null;
    }
}
