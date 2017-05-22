package com.popularmovies.vpaliy.data.repository;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.common.cache.CacheBuilder;
import com.popularmovies.vpaliy.data.cache.CacheStore;

import java.util.concurrent.TimeUnit;

import rx.Observable;

abstract class AbstractRepository<T> {

    private static final int DEFAULT_CACHE_SIZE=150; //max 150 items
    private static final int DEFAULT_CACHE_DURATION=20; //20 minutes

    private Context context;
    private final CacheStore<Integer,T> cacheStore;

    AbstractRepository(Context context){
        this(context,DEFAULT_CACHE_SIZE,DEFAULT_CACHE_DURATION);
    }

    AbstractRepository(Context context,int cacheSize){
        this(context,cacheSize,DEFAULT_CACHE_DURATION);
    }

    @SuppressWarnings("WeakerAccess")
    AbstractRepository(Context context,int cacheSize, int expiresAfter){
        this.context=context;
        this.cacheStore=new CacheStore<>(CacheBuilder.newBuilder()
                .maximumSize(cacheSize)
                .expireAfterAccess(expiresAfter,TimeUnit.MINUTES)
                .build());
    }

    public boolean isCached(int key){
        return cacheStore.isInCache(key);
    }

    public void cache(int key, T data){
        cacheStore.put(key,data);
    }

    public Observable<T> fromCache(int id){
        return cacheStore.getStream(id);
    }

    boolean isNetworkConnection(){
        ConnectivityManager manager=ConnectivityManager.class
                .cast(context.getSystemService(Context.CONNECTIVITY_SERVICE));
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        return activeNetwork!=null && activeNetwork.isConnectedOrConnecting();
    }
}
