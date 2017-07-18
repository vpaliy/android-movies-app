package com.popularmovies.vpaliy.data.source.remote;


import com.popularmovies.vpaliy.data.entity.ActorEntity;
import com.popularmovies.vpaliy.data.entity.BackdropImage;
import com.popularmovies.vpaliy.data.entity.SeasonEntity;
import com.popularmovies.vpaliy.data.entity.TrailerEntity;
import com.popularmovies.vpaliy.data.entity.TvShow;
import com.popularmovies.vpaliy.data.entity.TvShowDetailEntity;
import com.popularmovies.vpaliy.data.entity.TvShowInfoEntity;
import com.popularmovies.vpaliy.data.source.DetailsDataSource;
import com.popularmovies.vpaliy.data.source.remote.service.TvService;
import com.popularmovies.vpaliy.data.source.remote.wrapper.BackdropsWrapper;
import com.popularmovies.vpaliy.data.source.remote.wrapper.CastWrapper;
import com.popularmovies.vpaliy.data.source.remote.wrapper.TrailerWrapper;
import com.popularmovies.vpaliy.data.source.remote.wrapper.TvShowsWrapper;
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

    private TvService tvService;
    private BaseSchedulerProvider schedulerProvider;

    @Inject
    public RemoteTvShowDetails(@NonNull TvService tvService,
                               @NonNull BaseSchedulerProvider schedulerProvider){
        this.tvService = tvService;
        this.schedulerProvider=schedulerProvider;
    }

    @Override
    public Observable<TvShowDetailEntity> get(String id) {
        Observable<TvShowInfoEntity> infoObservable= tvService.queryTvShowInfo(id)
                .subscribeOn(schedulerProvider.multi());

        Observable<List<BackdropImage>> backdropsObservable = tvService.queryBackdrops(id)
                .subscribeOn(Schedulers.newThread())
                .map(BackdropsWrapper::getBackdropImages);

        Observable<List<ActorEntity>> actorsObservable = tvService.queryCast(id)
                .subscribeOn(schedulerProvider.multi())
                .map(CastWrapper::getCast);

        Observable<List<TrailerEntity>> trailerObservable= tvService.queryVideos(id)
                .subscribeOn(schedulerProvider.multi())
                .map(TrailerWrapper::getTrailers);

        Observable<TvShowsWrapper> similarObservable= tvService.querySimilar(id)
                .subscribeOn(schedulerProvider.multi());

        return Observable.zip(infoObservable,backdropsObservable,actorsObservable,trailerObservable,similarObservable,
                (infoEntity, backdropImages, actorEntities,trailers,similarWrapper) -> {
                    TvShow tvShow= MapperUtils.createTvShowCover(infoEntity);
                    tvShow.setBackdrops(backdropImages);
                    TvShowDetailEntity detailEntity=new TvShowDetailEntity();
                    detailEntity.setCast(actorEntities);
                    detailEntity.setInfoEntity(infoEntity);
                    detailEntity.setTrailers(trailers);
                    detailEntity.setSimilarTvShows(similarWrapper.getTvShows());
                    detailEntity.setSeasons(infoEntity.getSeasonEntities());
                    detailEntity.setTvShowCover(tvShow);
                    return detailEntity;
                });
    }

    private void appendId(List<SeasonEntity> list, int id){
        if(list!=null){
            list.forEach(seasonEntity -> {
                int temp=seasonEntity.getSeasonNumber();
            });
        }
    }

    @Override
    public void insert(TvShowDetailEntity item) {/*Empty*/}
}
