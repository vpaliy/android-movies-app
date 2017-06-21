package com.popularmovies.vpaliy.data.source.remote;


import com.popularmovies.vpaliy.data.entity.ActorEntity;
import com.popularmovies.vpaliy.data.entity.BackdropImage;
import com.popularmovies.vpaliy.data.entity.TvShow;
import com.popularmovies.vpaliy.data.entity.TvShowDetailEntity;
import com.popularmovies.vpaliy.data.entity.TvShowInfoEntity;
import com.popularmovies.vpaliy.data.source.DetailsDataSource;
import com.popularmovies.vpaliy.data.source.remote.service.TvShowService;
import com.popularmovies.vpaliy.data.source.remote.wrapper.BackdropsWrapper;
import com.popularmovies.vpaliy.data.source.remote.wrapper.CastWrapper;
import com.popularmovies.vpaliy.data.utils.MapperUtils;
import com.popularmovies.vpaliy.data.utils.scheduler.BaseSchedulerProvider;
import java.util.List;
import rx.Observable;
import rx.schedulers.Schedulers;

import javax.inject.Inject;
import javax.inject.Singleton;

import android.support.annotation.NonNull;

@Singleton
public class RemoteTvShowDetails implements DetailsDataSource<TvShowDetailEntity>{

    private TvShowService tvShowService;
    private BaseSchedulerProvider schedulerProvider;

    @Inject
    public RemoteTvShowDetails(@NonNull TvShowService tvShowService,
                               @NonNull BaseSchedulerProvider schedulerProvider){
        this.tvShowService=tvShowService;
        this.schedulerProvider=schedulerProvider;
    }

    @Override
    public Observable<TvShowDetailEntity> get(int id) {
        Observable<TvShowInfoEntity> infoObservable=tvShowService.queryTvShowInfo(id)
                .subscribeOn(schedulerProvider.multi());

        Observable<List<BackdropImage>> backdropsObservable = tvShowService.queryBackdrops(id)
                .subscribeOn(Schedulers.newThread())
                .map(BackdropsWrapper::getBackdropImages);

        Observable<List<ActorEntity>> actorsObservable = tvShowService.queryCast(id)
                .subscribeOn(schedulerProvider.multi())
                .map(CastWrapper::getCast);

        return Observable.zip(infoObservable,backdropsObservable,actorsObservable,
                (infoEntity, backdropImages, actorEntities) -> {
                    TvShow tvShow= MapperUtils.createTvShowCover(infoEntity);
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
    public void insert(TvShowDetailEntity item) {/*Empty*/}
}
