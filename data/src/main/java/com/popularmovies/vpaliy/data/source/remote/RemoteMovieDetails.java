package com.popularmovies.vpaliy.data.source.remote;

import com.popularmovies.vpaliy.data.entity.ActorEntity;
import com.popularmovies.vpaliy.data.entity.BackdropImage;
import com.popularmovies.vpaliy.data.entity.CollectionEntity;
import com.popularmovies.vpaliy.data.entity.Movie;
import com.popularmovies.vpaliy.data.entity.MovieDetailEntity;
import com.popularmovies.vpaliy.data.entity.ReviewEntity;
import com.popularmovies.vpaliy.data.entity.TrailerEntity;
import com.popularmovies.vpaliy.data.source.DetailsDataSource;
import com.popularmovies.vpaliy.data.source.remote.service.MoviesService;
import com.popularmovies.vpaliy.data.source.remote.wrapper.BackdropsWrapper;
import com.popularmovies.vpaliy.data.source.remote.wrapper.CastWrapper;
import com.popularmovies.vpaliy.data.source.remote.wrapper.MovieWrapper;
import com.popularmovies.vpaliy.data.source.remote.wrapper.ReviewWrapper;
import com.popularmovies.vpaliy.data.source.remote.wrapper.TrailerWrapper;
import com.popularmovies.vpaliy.data.utils.scheduler.BaseSchedulerProvider;
import java.util.List;
import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import android.support.annotation.NonNull;
import android.util.Log;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RemoteMovieDetails implements DetailsDataSource<MovieDetailEntity> {

    private MoviesService moviesService;
    private BaseSchedulerProvider schedulerProvider;

    @Inject
    public RemoteMovieDetails(@NonNull MoviesService moviesService,
                              @NonNull BaseSchedulerProvider provider){
        this.moviesService=moviesService;
        this.schedulerProvider=provider;
    }

    @Override
    public Observable<MovieDetailEntity> get(int id) {
        Observable<List<Movie>> similarObservable = moviesService.querySimilarMovies(Integer.toString(id))
                .subscribeOn(schedulerProvider.multi())
                .map(MovieWrapper::getCoverList);

        Observable<Movie> movieObservable = moviesService.queryDetails(Integer.toString(id))
                .map(movie->{
                    CollectionEntity collection=movie.getCollection();
                    if(collection!=null) {
                        moviesService.queryCollection(Integer.toString(collection.getId()))
                                .map(collectionEntity ->{
                                    movie.setCollection(collectionEntity);
                                    return movie;
                                })
                                .subscribe();
                    }
                    return movie;
                })
                .subscribeOn(schedulerProvider.multi());

        Observable<List<BackdropImage>> backdropsObservable = moviesService.queryBackdrops(Integer.toString(id))
                .subscribeOn(Schedulers.newThread())
                .map(BackdropsWrapper::getBackdropImages);

        Observable<List<ActorEntity>> actorsObservable = moviesService.queryCast(Integer.toString(id))
                .subscribeOn(schedulerProvider.multi())
                .map(CastWrapper::getCast);

        Observable<List<TrailerEntity>> trailersObservable=moviesService.queryVideos(Integer.toString(id))
                .subscribeOn(schedulerProvider.multi())
                .map(TrailerWrapper::getTrailers);

        Observable<List<ReviewEntity>> reviewsObservable=moviesService.queryReviews(Integer.toString(id))
                .subscribeOn(schedulerProvider.multi())
                .map(ReviewWrapper::getReviewList);

        return Observable.zip(movieObservable, similarObservable, backdropsObservable, actorsObservable,trailersObservable,reviewsObservable,
                (Movie movie, List<Movie> movies, List<BackdropImage> backdropImages,
                 List<ActorEntity> actorEntities, List<TrailerEntity> trailers, List<ReviewEntity> reviews) -> {
                    MovieDetailEntity movieDetails = new MovieDetailEntity();
                    movieDetails.setCast(actorEntities);
                    movieDetails.setCollectionEntity(movie.getCollection());
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
