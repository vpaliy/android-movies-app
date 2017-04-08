package com.popularmovies.vpaliy.popularmoviesapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG=SplashActivity.class.getSimpleName();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler().postDelayed(()->{
            Intent intent=new Intent(SplashActivity.this,MoviesActivity.class);
            startActivity(intent);}, 50);
    }
}
