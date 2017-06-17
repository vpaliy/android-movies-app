package com.popularmovies.vpaliy.data.source.remote;

import com.popularmovies.vpaliy.data.entity.Genre;
import com.popularmovies.vpaliy.data.entity.TvShow;
import com.popularmovies.vpaliy.data.source.CoverDataSource;
import com.popularmovies.vpaliy.data.source.remote.service.GenresService;
import com.popularmovies.vpaliy.data.source.remote.service.TvShowService;
import com.popularmovies.vpaliy.data.source.remote.wrapper.PageWrapper;
import com.popularmovies.vpaliy.data.source.remote.wrapper.TvShowsWrapper;
import com.popularmovies.vpaliy.data.utils.scheduler.BaseSchedulerProvider;
import com.popularmovies.vpaliy.domain.configuration.SortType;
import rx.Observable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import android.support.annotation.NonNull;
import android.util.SparseArray;

@Singleton
public class RemoteTvShowCovers implements CoverDataSource<TvShow> {

    private TvShowService tvShowService;
    private Map<SortType,PageWrapper> pageMap;
    private SparseArray<Genre> genres;
    private GenresService genresService;
    private BaseSchedulerProvider schedulerProvider;

    @Inject
    public RemoteTvShowCovers(@NonNull TvShowService tvShowService,
                              @NonNull GenresService genresService,
                              @NonNull BaseSchedulerProvider schedulerProvider){
        this.tvShowService=tvShowService;
        this.schedulerProvider=schedulerProvider;
        this.genresService=genresService;
        this.pageMap=new HashMap<>();
    }

    @Override
    public Observable<List<TvShow>> get(SortType type) {
        if(!pageMap.containsKey(type)) pageMap.put(type,new PageWrapper());
        PageWrapper wrapper=pageMap.get(type);
        wrapper.currentPage=1;
        return query(type,wrapper);
    }

    private List<TvShow> convertToTv(SortType sortType,TvShowsWrapper wrapper){
        int current=wrapper.getPage();
        int total=wrapper.getTotalPages();
        if(!pageMap.containsKey(sortType)) pageMap.put(sortType,new PageWrapper());
        PageWrapper pageWrapper=pageMap.get(sortType);
        pageWrapper.currentPage=current;
        pageWrapper.totalPages=total;
        return wrapper.getTvShows();
    }

    private Observable<List<TvShow>> query(SortType sortType,PageWrapper pageWrapper) {
        Observable<List<TvShow>> tvObservable;
        switch (sortType){
            case TOP_RATED:
                tvObservable=tvShowService.queryTopRated(pageWrapper.currentPage)
                        .map(wrapper->convertToTv(sortType,wrapper));
                break;
            case POPULAR:
                tvObservable=tvShowService.queryPopular(pageWrapper.currentPage)
                        .map(wrapper->convertToTv(sortType,wrapper));
                break;
            case LATEST:
                tvObservable=tvShowService.queryLatest(pageWrapper.currentPage)
                        .map(wrapper->convertToTv(sortType,wrapper));
                break;
            case UPCOMING:
                tvObservable=tvShowService.queryAiringToday(pageWrapper.currentPage)
                        .map(wrapper->convertToTv(sortType,wrapper));
                break;
            default:
                tvObservable=tvShowService.queryOnAir(pageWrapper.currentPage)
                        .map(wrapper->convertToTv(sortType,wrapper));
                break;
        }
        return Observable.zip(tvObservable,queryGenres(),(list,genres)->{
            list.forEach(this::mergeGenres);
            return list;
        });
    }

    private void mergeGenres(TvShow tvShow){
        if(tvShow.getGenres()!=null){
            for(int genreId:tvShow.getGenres()){
                Genre genre=genres.get(genreId);
                if(genre!=null){
                    tvShow.addGenre(genre);
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
    public Observable<List<TvShow>> requestMore(SortType type) {
        PageWrapper wrapper=pageMap.get(type);
        if (wrapper != null) {
            if(wrapper.currentPage!=wrapper.totalPages){
                return query(type,wrapper);
            }
        }
        return null;
    }

    @Override
    public Observable<TvShow> get(int id) {
        return tvShowService.queryTvShow(id);
    }

    @Override
    public void update(TvShow item, SortType type) {}

    @Override
    public void insert(TvShow item, SortType sortType) {}

}
