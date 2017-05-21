package com.popularmovies.vpaliy.data.source.remote;


import com.popularmovies.vpaliy.data.entity.ActorEntity;
import com.popularmovies.vpaliy.data.entity.BackdropImage;
import com.popularmovies.vpaliy.data.entity.TvShow;
import com.popularmovies.vpaliy.data.entity.TvShowDetailEntity;
import com.popularmovies.vpaliy.data.entity.TvShowInfoEntity;
import com.popularmovies.vpaliy.data.source.MediaDataSource;
import com.popularmovies.vpaliy.data.source.remote.wrapper.BackdropsWrapper;
import com.popularmovies.vpaliy.data.source.remote.wrapper.CastWrapper;
import com.popularmovies.vpaliy.data.source.remote.wrapper.TvShowsWrapper;
import com.popularmovies.vpaliy.data.utils.scheduler.BaseSchedulerProvider;
import com.popularmovies.vpaliy.domain.configuration.ISortConfiguration.SortType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import rx.Observable;
import rx.schedulers.Schedulers;

import javax.inject.Inject;
import javax.inject.Singleton;
import android.support.annotation.NonNull;

@Singleton
public class RemoteTvShowSource extends MediaDataSource<TvShow,TvShowDetailEntity> {

    private final MovieDatabaseAPI databaseAPI;
    private final Map<SortType,MediaStream> pageMap;
    private final BaseSchedulerProvider schedulerProvider;

    @Inject
    public RemoteTvShowSource(@NonNull MovieDatabaseAPI databaseAPI,
                              @NonNull BaseSchedulerProvider schedulerProvider){
        this.databaseAPI=databaseAPI;
        this.pageMap=new HashMap<>();
        this.schedulerProvider=schedulerProvider;
    }

    @Override
    public Observable<List<TvShow>> getCovers(@NonNull SortType type) {
        if(!pageMap.containsKey(type)) pageMap.put(type,new MediaStream());
        MediaStream stream=pageMap.get(type);
        stream.currentPage=1;
        return query(type,stream);
    }

    private List<TvShow> convertToTv(SortType sortType,TvShowsWrapper wrapper){
        int current=wrapper.getPage();
        int total=wrapper.getTotalPages();
        if(!pageMap.containsKey(sortType)) pageMap.put(sortType,new MediaStream());
        MediaStream stream=pageMap.get(sortType);
        stream.currentPage=current;
        stream.totalPages=total;
        return wrapper.getTvShows();
    }


    @Override
    public Observable<TvShow> getCover(int id) {
        return databaseAPI.getTvShow(id);
    }

    @Override
    public Observable<List<TvShow>> requestMoreCovers(@NonNull SortType sortType) {
        MediaStream stream=pageMap.get(sortType);
        if(stream!=null){
            if(stream.currentPage!=stream.totalPages){
                stream.currentPage++;
                return query(sortType,stream);
            }
        }
        return null;
    }

    private Observable<List<TvShow>> query(@NonNull SortType sortType, MediaStream stream){
        switch (sortType){
            case TOP_RATED:
                return databaseAPI.getTopRatedTv(stream.currentPage)
                        .map(wrapper->convertToTv(sortType,wrapper));
            case POPULAR:
                return databaseAPI.getPopularTv(stream.currentPage)
                        .map(wrapper->convertToTv(sortType,wrapper));
            case LATEST:
                return databaseAPI.getLatestTv(stream.currentPage)
                        .map(wrapper->convertToTv(sortType,wrapper));
            case UPCOMING:
                return databaseAPI.getUpcomingTv(stream.currentPage)
                        .map(wrapper->convertToTv(sortType,wrapper));
            case NOW_PLAYING:
                return databaseAPI.getNowPlayingTv(stream.currentPage)
                        .map(wrapper->convertToTv(sortType,wrapper));
        }
        return null;
    }

    @Override
    public boolean isType(int id, SortType sortType) {
        return false;
    }

    @Override
    public void insert(TvShow item, SortType sortType) {/* Empty */}

    @Override
    public void insertDetails(TvShowDetailEntity details) {/* Empty */}

    @Override
    public Observable<TvShowDetailEntity> getDetails(int id) {
        Observable<TvShowInfoEntity> infoObservable=databaseAPI.getTvShowDetails(id)
                .subscribeOn(schedulerProvider.multi());

        Observable<List<BackdropImage>> backdropsObservable = databaseAPI.getBackdropsForTvShow(id)
                .subscribeOn(Schedulers.newThread())
                .map(BackdropsWrapper::getBackdropImages);

        Observable<List<ActorEntity>> actorsObservable = databaseAPI.getTvShowCast(id)
                .subscribeOn(schedulerProvider.multi())
                .map(CastWrapper::getCast);

        return Observable.zip(infoObservable,backdropsObservable,actorsObservable,
                (infoEntity, backdropImages, actorEntities) -> {
                    TvShow tvShow=TvShowInfoEntity.createTvShowCover(infoEntity);
                    tvShow.setBackdrops(backdropImages);
                    TvShowDetailEntity detailEntity=new TvShowDetailEntity();
                    detailEntity.setCast(actorEntities);
                    detailEntity.setInfoEntity(infoEntity);
                    detailEntity.setSeasons(infoEntity.getSeasonEntities());
                    detailEntity.setTvShowCover(tvShow);
                    return detailEntity;
                });
    }

    @Override
    public void update(TvShow item, @NonNull SortType sortType) {/* Empty */}

    private class MediaStream {
        private int currentPage;
        private int totalPages;
    }
}
