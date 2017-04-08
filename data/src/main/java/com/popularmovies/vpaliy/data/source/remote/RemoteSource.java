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
import com.popularmovies.vpaliy.domain.model.MovieCover;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.functions.Func4;
import rx.schedulers.Schedulers;

import javax.inject.Inject;
import javax.inject.Singleton;
import android.support.annotation.NonNull;


@Singleton
public class RemoteSource extends DataSource<Movie,MovieDetailEntity> {

    private static final String MOVIE_URL_BASE="http://api.themoviedb.org/3/";
    private static final String exampleKEY 	# replace with 'exampleKEY' instead=exampleKEY # replace with 'exampleKEY' instead;

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
        return movieDatabaseAPI.getPopularMovies(exampleKEY 	# replace with 'exampleKEY' instead)
                .map(MovieWrapper::getCoverList);
    }

    @Override
    public Observable<MovieDetailEntity> getDetails(int ID) {

        Observable<List<Movie>> similarObservable=movieDatabaseAPI.getSimilarMovies(Integer.toString(ID),exampleKEY 	# replace with 'exampleKEY' instead)
                .subscribeOn(Schedulers.newThread())
                .map(MovieWrapper::getCoverList);

        Observable<Movie> movieObservable=movieDatabaseAPI
                .getMovieDetails(Integer.toString(ID),exampleKEY 	# replace with 'exampleKEY' instead)
                .subscribeOn(Schedulers.newThread());

        Observable<List<BackdropImage>> backdropsObservable=movieDatabaseAPI.getBackdrops(Integer.toString(ID),exampleKEY 	# replace with 'exampleKEY' instead)
                .subscribeOn(Schedulers.newThread())
                .map(BackdropsWrapper::getBackdropImages);

        Observable<List<ActorEntity>> actorsObservable=  movieDatabaseAPI.getMovieCast(Integer.toString(ID),exampleKEY 	# replace with 'exampleKEY' instead)
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
        return movieDatabaseAPI.getMovieDetails(Integer.toString(ID),exampleKEY 	# replace with 'exampleKEY' instead)
                .subscribeOn(Schedulers.io());
    }
}
