package com.popularmovies.vpaliy.data.repository;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.google.common.cache.CacheBuilder;
import com.popularmovies.vpaliy.data.cache.CacheStore;
import com.popularmovies.vpaliy.data.mapper.Mapper;
import com.popularmovies.vpaliy.data.source.MediaDataSource;
import com.popularmovies.vpaliy.domain.IMediaRepository;
import com.popularmovies.vpaliy.domain.configuration.ISortConfiguration.SortType;
import com.popularmovies.vpaliy.domain.model.MediaCover;
import java.util.List;
import java.util.concurrent.TimeUnit;
import android.support.annotation.NonNull;
import javax.inject.Inject;
import com.popularmovies.vpaliy.data.source.qualifier.MovieLocal;
import com.popularmovies.vpaliy.data.source.qualifier.MovieRemote;
import rx.Observable;

public class MediaRepository<T,D1,D2> implements IMediaRepository<MediaCover,D1> {

    private static final int COVERS_CACHE_SIZE=100;
    private static final int DETAILS_CACHE_SIZE=100;

    private Mapper<D1,D2> detailsMapper;
    private Mapper<MediaCover,T> coverMapper;

    private MediaDataSource<T,D2> localDataSource;
    private MediaDataSource<T,D2> remoteDataSource;

    private final CacheStore<Integer,MediaCover> coversCache;
    private final CacheStore<Integer,D1> detailsCache;

    private Context context;

    @Inject
    public MediaRepository(@NonNull @MovieRemote MediaDataSource<T, D2> remoteDataSource,
                           @NonNull @MovieLocal MediaDataSource<T,D2> localDataSource,
                           @NonNull Mapper<MediaCover, T> coverMapper,
                           @NonNull Mapper<D1, D2> detailsMapper,
                           @NonNull Context context) {
        this.context=context;
        this.remoteDataSource = remoteDataSource;
        this.localDataSource=localDataSource;
        this.coverMapper=coverMapper;
        this.detailsMapper = detailsMapper;
        this.coversCache=new CacheStore<>(CacheBuilder.newBuilder()
                .maximumSize(COVERS_CACHE_SIZE)
                .expireAfterAccess(20, TimeUnit.MINUTES)
                .build());
        this.detailsCache=new CacheStore<>(CacheBuilder.newBuilder()
                .maximumSize(DETAILS_CACHE_SIZE)
                .expireAfterAccess(20,TimeUnit.MINUTES)
                .build());

    }


    @Override
    public Observable<MediaCover> getCover(int id) {
        if(!coversCache.isInCache(id)) {
            if(isNetworkConnection()) {
                return remoteDataSource.getCover(id)
                        .map(coverMapper::map)
                        .map(this::isFavorite)
                        .doOnNext(movie -> coversCache.put(id, movie));
            }
            return localDataSource.getCover(id)
                    .map(coverMapper::map)
                    .map(this::isFavorite)
                    .doOnNext(movie -> coversCache.put(id, movie));
        }
        return coversCache.getStream(id);
    }

    @Override
    public Observable<List<MediaCover>> getCovers(@NonNull SortType sortType) {
        if(isNetworkConnection()) {
            Observable<List<T>> observable=remoteDataSource.getCovers(sortType);
            if(observable!=null) {
                return observable.doOnNext(list->saveMoviesToDisk(list,sortType))
                        .map(coverMapper::map)
                        .doOnNext(covers -> Observable.from(covers)
                                .map(this::isFavorite)
                                .filter(cover -> !coversCache.isInCache(cover.getMediaId()))
                                .subscribe(mediaCover -> coversCache.put(mediaCover.getMediaId(), mediaCover)));
            }
        }
        return localDataSource.getCovers(sortType)
                .map(coverMapper::map)
                .doOnNext(covers -> Observable.from(covers)
                        .map(this::isFavorite)
                        .filter(cover -> !coversCache.isInCache(cover.getMediaId()))
                        .subscribe(mediaCover -> coversCache.put(mediaCover.getMediaId(), mediaCover)));
    }

    private void saveMoviesToDisk(List<T> list, SortType sortType){

    }

    private MediaCover isFavorite(MediaCover mediaCover){
        return mediaCover;
    }


    @Override
    public Observable<List<MediaCover>> requestMoreCovers(@NonNull SortType sortType) {
        if(isNetworkConnection()) {
            return remoteDataSource.requestMoreCovers(sortType)
                    .doOnNext(movies->saveMoviesToDisk(movies,sortType))
                    .map(coverMapper::map)
                    .doOnNext(movies -> Observable.from(movies)
                            .map(this::isFavorite)
                            .filter(cover -> !coversCache.isInCache(cover.getMediaId()))
                            .subscribe(movieCover -> coversCache.put(movieCover.getMediaId(), movieCover)));
        }
        return Observable.just(null);
    }

    @Override
    public boolean isType(int id, SortType sortType) {
        return false;
    }

    @Override
    public Observable<D1> getDetails(int id) {
        if(!detailsCache.isInCache(id)) {
            if(isNetworkConnection()) {
                return remoteDataSource.getDetails(id)
                        // .doOnNext(localDataSource::insertDetails)
                        .map(detailsMapper::map)
                        .doOnNext(details -> detailsCache.put(id, details));
            }
            return localDataSource.getDetails(id)
                    .map(detailsMapper::map);
        }
        return detailsCache.getStream(id);
    }

    @Override
    public void update(MediaCover item, @NonNull SortType sortType) {

    }

    private boolean isNetworkConnection(){
        ConnectivityManager manager=ConnectivityManager.class
                .cast(context.getSystemService(Context.CONNECTIVITY_SERVICE));
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        return activeNetwork!=null && activeNetwork.isConnectedOrConnecting();
    }
}
