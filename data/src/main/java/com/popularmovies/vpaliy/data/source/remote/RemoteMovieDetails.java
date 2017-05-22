package com.popularmovies.vpaliy.data.source.remote;

import com.popularmovies.vpaliy.data.entity.ActorEntity;
import com.popularmovies.vpaliy.data.entity.BackdropImage;
import com.popularmovies.vpaliy.data.entity.Movie;
import com.popularmovies.vpaliy.data.entity.MovieDetailEntity;
import com.popularmovies.vpaliy.data.entity.ReviewEntity;
import com.popularmovies.vpaliy.data.entity.TrailerEntity;
import com.popularmovies.vpaliy.data.source.DetailsDataSource;
import com.popularmovies.vpaliy.data.source.remote.wrapper.BackdropsWrapper;
import com.popularmovies.vpaliy.data.source.remote.wrapper.CastWrapper;
import com.popularmovies.vpaliy.data.source.remote.wrapper.MovieWrapper;
import com.popularmovies.vpaliy.data.source.remote.wrapper.ReviewWrapper;
import com.popularmovies.vpaliy.data.source.remote.wrapper.TrailerWrapper;
import com.popularmovies.vpaliy.data.utils.scheduler.BaseSchedulerProvider;
import java.util.List;
import rx.Observable;
import rx.schedulers.Schedulers;

import android.support.annotation.NonNull;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RemoteMovieDetails implements DetailsDataSource<MovieDetailEntity> {

    private MovieDatabaseAPI databaseAPI;
    private BaseSchedulerProvider schedulerProvider;

    @Inject
    public RemoteMovieDetails(@NonNull MovieDatabaseAPI databaseAPI,
                              @NonNull BaseSchedulerProvider provider){
        this.databaseAPI=databaseAPI;
        this.schedulerProvider=provider;
    }

    @Override
    public Observable<MovieDetailEntity> get(int id) {
        Observable<List<Movie>> similarObservable = databaseAPI.getSimilarMovies(Integer.toString(id))
                .subscribeOn(schedulerProvider.multi())
                .map(MovieWrapper::getCoverList);

        Observable<Movie> movieObservable = databaseAPI
                .getMovieDetails(Integer.toString(id))
                .subscribeOn(schedulerProvider.multi());


        Observable<List<BackdropImage>> backdropsObservable = databaseAPI.getBackdrops(Integer.toString(id))
                .subscribeOn(Schedulers.newThread())
                .map(BackdropsWrapper::getBackdropImages);

        Observable<List<ActorEntity>> actorsObservable = databaseAPI.getMovieCast(Integer.toString(id))
                .subscribeOn(schedulerProvider.multi())
                .map(CastWrapper::getCast);

        Observable<List<TrailerEntity>> trailersObservable=databaseAPI.getVideos(Integer.toString(id))
                .subscribeOn(schedulerProvider.multi())
                .map(TrailerWrapper::getTrailers);

        Observable<List<ReviewEntity>> reviewsObservable=databaseAPI.getReviews(Integer.toString(id))
                .subscribeOn(schedulerProvider.multi())
                .map(ReviewWrapper::getReviewList);

        return Observable.zip(movieObservable, similarObservable, backdropsObservable, actorsObservable,trailersObservable,reviewsObservable,
                (Movie movie, List<Movie> movies, List<BackdropImage> backdropImages,
                 List<ActorEntity> actorEntities, List<TrailerEntity> trailers, List<ReviewEntity> reviews) -> {
                    MovieDetailEntity movieDetails = new MovieDetailEntity();
                    movieDetails.setCast(actorEntities);
                    movieDetails.setBackdropImages(backdropImages);
                    movie.setBackdropImages(backdropImages);
                    movieDetails.setMovie(movie);
                    movieDetails.setTrailers(trailers);
                    movieDetails.setSimilarMovies(movies);
                    movieDetails.setReviews(reviews);
                    return movieDetails;
                });
    }

    @Override
    public void insert(MovieDetailEntity item) {/* Empty */}
}
