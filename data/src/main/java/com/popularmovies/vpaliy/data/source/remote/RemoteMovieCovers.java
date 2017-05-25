package com.popularmovies.vpaliy.data.source.remote;

import com.popularmovies.vpaliy.data.entity.Movie;
import com.popularmovies.vpaliy.data.source.CoverDataSource;
import com.popularmovies.vpaliy.data.source.remote.wrapper.MovieWrapper;
import com.popularmovies.vpaliy.data.source.remote.wrapper.PageWrapper;
import com.popularmovies.vpaliy.domain.configuration.SortType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import rx.Observable;

import javax.inject.Inject;
import javax.inject.Singleton;

import android.support.annotation.NonNull;


@Singleton
public class RemoteMovieCovers implements CoverDataSource<Movie> {

    private MovieDatabaseAPI databaseAPI;
    private Map<SortType,PageWrapper> pageMap;

    @Inject
    public RemoteMovieCovers(@NonNull MovieDatabaseAPI databaseAPI){
        this.databaseAPI=databaseAPI;
        this.pageMap=new HashMap<>();
    }

    private List<Movie> convertToMovie(SortType sortType,MovieWrapper wrapper){
        int current=wrapper.getPage();
        int total=wrapper.getTotalPages();
        if(!pageMap.containsKey(sortType)) pageMap.put(sortType,new PageWrapper());
        PageWrapper pageWrapper=pageMap.get(sortType);
        pageWrapper.currentPage=current;
        pageWrapper.totalPages=total;
        return wrapper.getCoverList();
    }

    private Observable<List<Movie>> query(SortType sortType,PageWrapper pageWrapper) {
        switch (sortType) {
            case TOP_RATED:
                return databaseAPI.getTopRatedMovies(pageWrapper.currentPage)
                        .map(wrapper -> convertToMovie(sortType, wrapper));
            case POPULAR:
                return databaseAPI.getPopularMovies(pageWrapper.currentPage)
                        .map(wrapper -> convertToMovie(sortType, wrapper));
            case LATEST:
                return databaseAPI.getLatestMovies()
                        .map(wrapper -> convertToMovie(sortType, wrapper));
            case UPCOMING:
                return databaseAPI.getUpcomingMovies(pageWrapper.currentPage)
                        .map(wrapper -> convertToMovie(sortType, wrapper));
            case NOW_PLAYING:
                return databaseAPI.getNowPlayingMovies(pageWrapper.currentPage)
                        .map(wrapper -> convertToMovie(sortType, wrapper));
            default:
                return null;
        }
    }

    @Override
    public Observable<List<Movie>> get(SortType type) {
        if(!pageMap.containsKey(type)) pageMap.put(type,new PageWrapper());
        PageWrapper wrapper=pageMap.get(type);
        wrapper.currentPage=1;
        return query(type,wrapper);
    }

    @Override
    public Observable<Movie> get(int id) {
        return databaseAPI.getMovieDetails(Integer.toString(id));
    }

    @Override
    public Observable<List<Movie>> requestMore(SortType type) {
        PageWrapper wrapper=pageMap.get(type);
        if(wrapper!=null){
            if(wrapper.currentPage!=wrapper.totalPages) {
                wrapper.currentPage++;
                return query(type, wrapper);
            }
        }
        return null;
    }

    @Override
    public void update(Movie item, SortType type) {}

    @Override
    public void insert(Movie item, SortType sortType) {}
}
