package com.popularmovies.vpaliy.popularmoviesapp.di.module;


import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.popularmovies.vpaliy.data.source.remote.MovieDatabaseAPI;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import dagger.Module;
import javax.inject.Singleton;
import dagger.Provides;

@Module
public class NetworkModule {

    private static final String MOVIE_URL_BASE="http://api.themoviedb.org/3/";
    private static final String API_QUERY = "api_key";
    private static final String API_KEY="de3cafdd4958ef2352dfcd262ef1aef3";

    private static final long CACHE_SIZE = 10 * 1024 * 1024;
    private static final int CONNECT_TIMEOUT = 15;
    private static final int WRITE_TIMEOUT = 60;
    private static final int READ_TIMEOUT = 60;


    @Provides
    @Singleton
    Interceptor provideOkHttpInterceptor(){
        return (chain -> {
            Request originalRequest = chain.request();
            HttpUrl originalHttpUrl = originalRequest.url();
            HttpUrl newHttpUrl = originalHttpUrl.newBuilder()
                    .setQueryParameter(API_QUERY, API_KEY)
                    .build();

            Request newRequest = originalRequest.newBuilder()
                    .url(newHttpUrl)
                    .build();

            return chain.proceed(newRequest);});
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(@NonNull Context context, @NonNull Interceptor interceptor) {
        Builder builder = new Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .cache(new Cache(context.getCacheDir(), CACHE_SIZE));

        return builder.build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(MOVIE_URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    MovieDatabaseAPI provideMovieDataBaseAPI(@NonNull Retrofit retrofit){
        return retrofit.create(MovieDatabaseAPI.class);
    }

}
