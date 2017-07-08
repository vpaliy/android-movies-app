package com.popularmovies.vpaliy.popularmoviesapp.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.ui.media.MediaActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler().postDelayed(()->{
            Intent intent=new Intent(SplashActivity.this,MediaActivity.class);
            startActivity(intent);}, getResources().getInteger(R.integer.splashDuration));
    }
}
