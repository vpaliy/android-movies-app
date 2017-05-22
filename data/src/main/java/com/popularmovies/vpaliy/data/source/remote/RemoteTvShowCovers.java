package com.popularmovies.vpaliy.data.source.remote;

import com.popularmovies.vpaliy.data.entity.TvShow;
import com.popularmovies.vpaliy.data.source.CoverDataSource;
import com.popularmovies.vpaliy.data.source.remote.wrapper.PageWrapper;
import com.popularmovies.vpaliy.data.source.remote.wrapper.TvShowsWrapper;
import com.popularmovies.vpaliy.domain.configuration.ISortConfiguration.SortType;
import rx.Observable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import android.support.annotation.NonNull;


public class RemoteTvShowCovers implements CoverDataSource<TvShow> {


    private MovieDatabaseAPI databaseAPI;
    private Map<SortType,PageWrapper> pageMap;

    @Inject
    public RemoteTvShowCovers(@NonNull MovieDatabaseAPI databaseAPI){
        this.databaseAPI=databaseAPI;
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
        switch (sortType){
            case TOP_RATED:
                return databaseAPI.getTopRatedTv(pageWrapper.currentPage)
                        .map(wrapper->convertToTv(sortType,wrapper));
            case POPULAR:
                return databaseAPI.getPopularTv(pageWrapper.currentPage)
                        .map(wrapper->convertToTv(sortType,wrapper));
            case LATEST:
                return databaseAPI.getLatestTv(pageWrapper.currentPage)
                        .map(wrapper->convertToTv(sortType,wrapper));
            case UPCOMING:
                return databaseAPI.getUpcomingTv(pageWrapper.currentPage)
                        .map(wrapper->convertToTv(sortType,wrapper));
            case NOW_PLAYING:
                return databaseAPI.getNowPlayingTv(pageWrapper.currentPage)
                        .map(wrapper->convertToTv(sortType,wrapper));
        }
        return null;
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
        return databaseAPI.getTvShow(id);
    }

    @Override
    public void update(TvShow item, SortType type) {}

    @Override
    public void insert(TvShow item, SortType sortType) {}

}
