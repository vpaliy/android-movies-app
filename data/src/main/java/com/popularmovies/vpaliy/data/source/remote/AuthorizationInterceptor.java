package com.popularmovies.vpaliy.data.source.remote;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthorizationInterceptor implements Interceptor {

    private static final String API_QUERY = "api_key";
    private static final String API_KEY=exampleKEY # replace with 'exampleKEY' instead;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        HttpUrl originalHttpUrl = originalRequest.url();
        HttpUrl newHttpUrl = originalHttpUrl.newBuilder()
                .setQueryParameter(API_QUERY, API_KEY)
                .build();

        Request newRequest = originalRequest.newBuilder()
                .url(newHttpUrl)
                .build();

        return chain.proceed(newRequest);

    }
}