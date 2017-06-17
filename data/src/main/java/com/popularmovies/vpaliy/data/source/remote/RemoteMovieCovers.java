package com.popularmovies.vpaliy.data.source.remote;

import com.popularmovies.vpaliy.data.entity.Genre;
import com.popularmovies.vpaliy.data.entity.Movie;
import com.popularmovies.vpaliy.data.source.CoverDataSource;
import com.popularmovies.vpaliy.data.source.remote.service.GenresService;
import com.popularmovies.vpaliy.data.source.remote.service.MoviesService;
import com.popularmovies.vpaliy.data.source.remote.wrapper.MovieWrapper;
import com.popularmovies.vpaliy.data.source.remote.wrapper.PageWrapper;
import com.popularmovies.vpaliy.data.utils.scheduler.BaseSchedulerProvider;
import com.popularmovies.vpaliy.domain.configuration.SortType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import rx.Observable;
import android.util.SparseArray;
import javax.inject.Inject;
import javax.inject.Singleton;
import android.support.annotation.NonNull;

@Singleton
public class RemoteMovieCovers implements CoverDataSource<Movie> {

    private Map<SortType,PageWrapper> pageMap;
    private SparseArray<Genre> genres;
    private final BaseSchedulerProvider schedulerProvider;
    private final MoviesService moviesService;
    private final GenresService genresService;

    @Inject
    public RemoteMovieCovers(@NonNull MoviesService moviesService,
                             @NonNull GenresService genresService,
                             BaseSchedulerProvider schedulerProvider){
        this.moviesService=moviesService;
        this.genresService=genresService;
        this.pageMap=new HashMap<>();
        this.schedulerProvider=schedulerProvider;
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
        Observable<List<Movie>> movieObservable;
        switch (sortType) {
            case TOP_RATED:
                movieObservable=moviesService.queryTopRated(pageWrapper.currentPage)
                        .map(wrapper -> convertToMovie(sortType,wrapper));
                break;
            case POPULAR:
                movieObservable=moviesService.queryPopular(pageWrapper.currentPage)
                        .map(wrapper -> convertToMovie(sortType,wrapper));
                break;
            case LATEST:
                movieObservable=moviesService.queryLatest()
                        .map(wrapper -> convertToMovie(sortType,wrapper));
                break;
            case UPCOMING:
                movieObservable=moviesService.queryUpcoming(pageWrapper.currentPage)
                        .map(wrapper -> convertToMovie(sortType,wrapper));
                break;
            default:
                movieObservable=moviesService.queryNowPlaying(pageWrapper.currentPage)
                        .map(wrapper -> convertToMovie(sortType,wrapper));
        }
        return Observable.zip(movieObservable,queryGenres(), (list, genres)->{
            list.forEach(this::mergeGenres);
            return list;
        });
    }

    private void mergeGenres(Movie movie){
        if(movie.getGenresId()!=null){
            for(int genreId:movie.getGenresId()){
                Genre genre=genres.get(genreId);
                if(genre!=null){
                    movie.addGenre(genre);
                }
            }
        }
    }

    private Observable<SparseArray<Genre>> queryGenres(){
        if(genres==null){
            return genresService.queryMovieGenres()
                    .subscribeOn(schedulerProvider.multi())
                    .map(genreWrapper -> {
                        genres=genreWrapper.convert();
                        return genres;
                    });
        }
        return Observable.just(genres);
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
        return moviesService.queryDetails(Integer.toString(id));
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
