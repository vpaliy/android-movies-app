package com.popularmovies.vpaliy.data.source.remote;

import com.popularmovies.vpaliy.data.entity.ActorDetailEntity;
import com.popularmovies.vpaliy.data.source.DetailsDataSource;
import com.popularmovies.vpaliy.data.source.remote.service.CastService;
import com.popularmovies.vpaliy.data.source.remote.wrapper.CastImagesWrapper;
import com.popularmovies.vpaliy.data.source.remote.wrapper.MovieWrapper;
import com.popularmovies.vpaliy.data.source.remote.wrapper.TvShowsWrapper;
import com.popularmovies.vpaliy.data.utils.scheduler.BaseSchedulerProvider;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class RemoteActorDetails implements DetailsDataSource<ActorDetailEntity>{

    private CastService castService;
    private BaseSchedulerProvider schedulerProvider;

    @Inject
    public RemoteActorDetails(CastService castService,
                              BaseSchedulerProvider schedulerProvider){
        this.castService=castService;
        this.schedulerProvider=schedulerProvider;
    }

    @Override
    public void insert(ActorDetailEntity item) { /* Empty */}

    @Override
    public Observable<ActorDetailEntity> get(int id) {
        Observable<ActorDetailEntity> actorObservable=castService.getActor(id)
                .subscribeOn(schedulerProvider.multi());
        Observable<CastImagesWrapper> imagesObservable=castService.queryImages(id)
                .subscribeOn(schedulerProvider.multi());
        Observable<MovieWrapper> movieObservable=castService.queryMovieCredits(id)
                .subscribeOn(schedulerProvider.multi());
        Observable<TvShowsWrapper> tvShowsObservable=castService.queryTvShowCredits(id)
                .subscribeOn(schedulerProvider.multi());

        return Observable.zip(actorObservable,imagesObservable,movieObservable,tvShowsObservable,
                (actorDetailEntity, castImagesWrapper, movieWrapper, tvShowsWrapper) ->{
                    actorDetailEntity.setMovies(movieWrapper.movies);
                    actorDetailEntity.setTvShows(tvShowsWrapper.getTvShows());
                    actorDetailEntity.setImagePaths(castImagesWrapper.images);
                    return actorDetailEntity;
                });
    }


}
