package com.popularmovies.vpaliy.data.source.remote;

import com.popularmovies.vpaliy.data.entity.ActorEntity;
import com.popularmovies.vpaliy.data.entity.BackdropImage;
import com.popularmovies.vpaliy.data.entity.Movie;
import com.popularmovies.vpaliy.data.entity.MovieDetailEntity;
import com.popularmovies.vpaliy.data.source.DataSource;
import com.popularmovies.vpaliy.data.source.remote.wrapper.BackdropsWrapper;
import com.popularmovies.vpaliy.data.source.remote.wrapper.CastWrapper;
import com.popularmovies.vpaliy.data.source.remote.wrapper.MovieWrapper;
import com.popularmovies.vpaliy.domain.ISortConfiguration;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.schedulers.Schedulers;
import okhttp3.OkHttpClient.Builder;
import javax.inject.Inject;
import javax.inject.Singleton;
import android.support.annotation.NonNull;
import android.content.Context;

@Singleton
public class RemoteSource extends DataSource<Movie,MovieDetailEntity> {

    private static final String TAG=RemoteSource.class.getSimpleName();
    private static final String MOVIE_URL_BASE="http://api.themoviedb.org/3/";

    private static final long CACHE_SIZE = 10 * 1024 * 1024;
    private static final int CONNECT_TIMEOUT = 15;
    private static final int WRITE_TIMEOUT = 60;
    private static final int TIMEOUT = 60;

    private final ISortConfiguration sortConfiguration;
    private final Context context;

    private MovieDatabaseAPI movieDatabaseAPI;


    @Inject
    public RemoteSource(@NonNull ISortConfiguration sortConfiguration, @NonNull Context context){
        this.sortConfiguration=sortConfiguration;
        this.context=context;
        init();

    }

    private void init(){
        OkHttpClient client=new Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .cache(new Cache(context.getCacheDir(),CACHE_SIZE))
                .addInterceptor(new AuthorizationInterceptor())
                .build();

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(MOVIE_URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();
        movieDatabaseAPI=retrofit.create(MovieDatabaseAPI.class);
    }

    @Override
    public Observable<List<Movie>> getCovers() {
        return movieDatabaseAPI.getPopularMovies(2)
                .map(MovieWrapper::getCoverList);
    }

    @Override
    public Observable<MovieDetailEntity> getDetails(int ID) {

        Observable<List<Movie>> similarObservable=movieDatabaseAPI.getSimilarMovies(Integer.toString(ID))
                .subscribeOn(Schedulers.newThread())
                .map(MovieWrapper::getCoverList);

        Observable<Movie> movieObservable=movieDatabaseAPI
                .getMovieDetails(Integer.toString(ID))
                .subscribeOn(Schedulers.newThread());

        Observable<List<BackdropImage>> backdropsObservable=movieDatabaseAPI.getBackdrops(Integer.toString(ID))
                .subscribeOn(Schedulers.newThread())
                .map(BackdropsWrapper::getBackdropImages);

        Observable<List<ActorEntity>> actorsObservable=  movieDatabaseAPI.getMovieCast(Integer.toString(ID))
                .subscribeOn(Schedulers.newThread())
                .map(CastWrapper::getCast);

       return Observable.zip(movieObservable, similarObservable, backdropsObservable, actorsObservable,
               (Movie movie, List<Movie> movies, List<BackdropImage> backdropImages, List<ActorEntity> actorEntities)-> {
                        MovieDetailEntity movieDetails=new MovieDetailEntity();
                        movieDetails.setCast(actorEntities);
                        movieDetails.setBackdropImages(backdropImages);
                        movie.setBackdropImages(backdropImages);
                        movieDetails.setMovie(movie);
                        movieDetails.setSimilarMovies(movies);
                        return movieDetails;
                    });

    }

    @Override
    public Observable<Movie> getCover(int ID) {
        return movieDatabaseAPI.getMovieDetails(Integer.toString(ID))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<Movie> requestMoreCovers() {
        return null;
    }

    @Override
    public Observable<Movie> sortBy(@NonNull ISortConfiguration.SortType type) {
        return null;
    }
}
