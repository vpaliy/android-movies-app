package com.popularmovies.vpaliy.data.source.remote;

import com.popularmovies.vpaliy.data.entity.ActorEntity;
import com.popularmovies.vpaliy.data.entity.BackdropImage;
import com.popularmovies.vpaliy.data.entity.Movie;
import com.popularmovies.vpaliy.data.entity.MovieDetailEntity;
import com.popularmovies.vpaliy.data.entity.ReviewEntity;
import com.popularmovies.vpaliy.data.entity.TrailerEntity;
import com.popularmovies.vpaliy.data.source.DataSource;
import com.popularmovies.vpaliy.data.source.remote.wrapper.BackdropsWrapper;
import com.popularmovies.vpaliy.data.source.remote.wrapper.CastWrapper;
import com.popularmovies.vpaliy.data.source.remote.wrapper.MovieWrapper;
import com.popularmovies.vpaliy.data.source.remote.wrapper.ReviewWrapper;
import com.popularmovies.vpaliy.data.source.remote.wrapper.TrailerWrapper;
import com.popularmovies.vpaliy.data.utils.SchedulerProvider;
import com.popularmovies.vpaliy.domain.configuration.ISortConfiguration;

import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.schedulers.Schedulers;
import javax.inject.Inject;
import javax.inject.Singleton;
import android.support.annotation.NonNull;

@Singleton
public class RemoteSource extends DataSource<Movie,MovieDetailEntity> {

    private final ISortConfiguration sortConfiguration;
    private final MovieDatabaseAPI movieDatabaseAPI;
    private final SchedulerProvider schedulerProvider;

    private int totalPages;
    private int currentPage;


    @Inject
    public RemoteSource(@NonNull ISortConfiguration sortConfiguration,
                        @NonNull MovieDatabaseAPI movieDatabaseAPI,
                        @NonNull SchedulerProvider schedulerProvider){
        this.sortConfiguration=sortConfiguration;
        this.movieDatabaseAPI=movieDatabaseAPI;
        this.schedulerProvider=schedulerProvider;

    }

    @Override
    public Observable<List<Movie>> getCovers() {
        switch (sortConfiguration.getConfiguration()){
            case TOP_RATED:
                return movieDatabaseAPI.getTopRatedMovies(1)
                        .map(this::convertToMovie);
            case POPULAR:
                return movieDatabaseAPI.getPopularMovies(1)
                        .map(this::convertToMovie);
            default:
                return null;
        }
    }


    private List<Movie> convertToMovie(MovieWrapper wrapper){
        this.currentPage=wrapper.getPage();
        this.totalPages=wrapper.getTotalPages();
        return wrapper.getCoverList();
    }

    @Override
    public Observable<MovieDetailEntity> getDetails(int ID) {

        Observable<List<Movie>> similarObservable = movieDatabaseAPI.getSimilarMovies(Integer.toString(ID))
                .subscribeOn(schedulerProvider.multi())
                .map(MovieWrapper::getCoverList);

        Observable<Movie> movieObservable = movieDatabaseAPI
                .getMovieDetails(Integer.toString(ID))
                .subscribeOn(schedulerProvider.multi());


        Observable<List<BackdropImage>> backdropsObservable = movieDatabaseAPI.getBackdrops(Integer.toString(ID))
                .subscribeOn(Schedulers.newThread())
                .map(BackdropsWrapper::getBackdropImages);

        Observable<List<ActorEntity>> actorsObservable = movieDatabaseAPI.getMovieCast(Integer.toString(ID))
                .subscribeOn(schedulerProvider.multi())
                .map(CastWrapper::getCast);

        Observable<List<TrailerEntity>> trailersObservable=movieDatabaseAPI.getVideos(Integer.toString(ID))
                .subscribeOn(schedulerProvider.multi())
                .map(TrailerWrapper::getTrailers);

        Observable<List<ReviewEntity>> reviewsObservable=movieDatabaseAPI.getReviews(Integer.toString(ID))
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
    public Observable<Movie> getCover(int ID) {
        return movieDatabaseAPI.getMovieDetails(Integer.toString(ID));

    }


    @Override
    public boolean isFavorite(int movieId) {
        return false;
    }

    @Override
    public void insert(Movie item) {/*Nothing */}

    @Override
    public void insertDetails(MovieDetailEntity details) {/*Nothing */}

    @Override
    public void update(Movie item) {/* Nothing */}

    @Override
    public Observable<List<Movie>> requestMoreCovers() {
        if(totalPages!=currentPage) {
            currentPage++;
            switch (sortConfiguration.getConfiguration()){
                case POPULAR:
                    return movieDatabaseAPI.getPopularMovies(currentPage)
                            .map(this::convertToMovie);
                case TOP_RATED:
                    return movieDatabaseAPI.getTopRatedMovies(currentPage)
                            .map(this::convertToMovie);

            }
        }
        return Observable.just(new ArrayList<>());
    }

    @Override
    public Observable<List<Movie>> sortBy(@NonNull ISortConfiguration.SortType type) {
        this.sortConfiguration.saveConfiguration(type);
        return getCovers();
    }
}
