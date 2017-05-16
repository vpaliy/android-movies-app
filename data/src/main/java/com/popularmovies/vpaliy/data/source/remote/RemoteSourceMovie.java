package com.popularmovies.vpaliy.data.source.remote;

import com.popularmovies.vpaliy.data.entity.ActorEntity;
import com.popularmovies.vpaliy.data.entity.BackdropImage;
import com.popularmovies.vpaliy.data.entity.Movie;
import com.popularmovies.vpaliy.data.entity.MovieDetailEntity;
import com.popularmovies.vpaliy.data.entity.ReviewEntity;
import com.popularmovies.vpaliy.data.entity.TrailerEntity;
import com.popularmovies.vpaliy.data.source.MovieDataSource;
import com.popularmovies.vpaliy.data.source.remote.wrapper.BackdropsWrapper;
import com.popularmovies.vpaliy.data.source.remote.wrapper.CastWrapper;
import com.popularmovies.vpaliy.data.source.remote.wrapper.MovieWrapper;
import com.popularmovies.vpaliy.data.source.remote.wrapper.ReviewWrapper;
import com.popularmovies.vpaliy.data.source.remote.wrapper.TrailerWrapper;
import com.popularmovies.vpaliy.data.utils.scheduler.BaseSchedulerProvider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.popularmovies.vpaliy.domain.configuration.ISortConfiguration.SortType;

import rx.Observable;
import rx.schedulers.Schedulers;

import javax.inject.Inject;
import javax.inject.Singleton;
import android.support.annotation.NonNull;

@Singleton
public class RemoteSourceMovie extends MovieDataSource<Movie,MovieDetailEntity> {

    private final MovieDatabaseAPI movieDatabaseAPI;
    private final BaseSchedulerProvider schedulerProvider;
    private final Map<SortType,MediaStream> pageMap;


    @Inject
    public RemoteSourceMovie(@NonNull MovieDatabaseAPI movieDatabaseAPI,
                             @NonNull BaseSchedulerProvider schedulerProvider){
        this.movieDatabaseAPI=movieDatabaseAPI;
        this.schedulerProvider=schedulerProvider;
        this.pageMap=new HashMap<>();

    }

    @Override
    public Observable<List<Movie>> getCovers(@NonNull SortType sortType) {
        switch (sortType){
            case TOP_RATED:
                return movieDatabaseAPI.getTopRatedMovies(1)
                        .map(wrapper->convertToMovie(sortType,wrapper));
            case POPULAR:
                return movieDatabaseAPI.getPopularMovies(1)
                        .map(wrapper->convertToMovie(sortType,wrapper));
            default:
                return null;
        }
    }


    private List<Movie> convertToMovie(SortType sortType,MovieWrapper wrapper){
        int current=wrapper.getPage();
        int total=wrapper.getTotalPages();
        if(pageMap.containsKey(sortType)) pageMap.put(sortType,new MediaStream());
        MediaStream stream=pageMap.get(sortType);
        stream.currentPage=current;
        stream.totalPages=total;
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
    public boolean isType(int movieId,SortType sortType) {
        return false;
    }

    @Override
    public void insert(Movie item) {/*Nothing */}

    @Override
    public void insertDetails(MovieDetailEntity details) {/*Nothing */}

    @Override
    public void update(Movie item, @NonNull SortType sortType) {/* Nothing */}

    @Override
    public Observable<List<Movie>> requestMoreCovers(@NonNull SortType sortType) {
        MediaStream stream=pageMap.get(sortType);
        if(stream!=null){
            if(stream.currentPage!=stream.totalPages){
                switch (sortType){
                    case POPULAR:
                        return movieDatabaseAPI.getPopularMovies(stream.currentPage)
                                .map(movieWrapper -> convertToMovie(sortType,movieWrapper));
                    case TOP_RATED:
                        return movieDatabaseAPI.getTopRatedMovies(stream.currentPage)
                                .map(movieWrapper -> convertToMovie(sortType,movieWrapper));
                }
            }
        }
        return Observable.just(new ArrayList<>());
    }

    private class MediaStream {
        private int totalPages;
        private int currentPage;

    }
}
