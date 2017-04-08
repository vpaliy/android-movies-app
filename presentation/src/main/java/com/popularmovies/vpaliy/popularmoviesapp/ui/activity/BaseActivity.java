package com.popularmovies.vpaliy.popularmoviesapp.ui.activity;


import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.popularmovies.vpaliy.popularmoviesapp.ui.navigator.Navigator;
import com.squareup.otto.Bus;

import javax.inject.Inject;

/**
 * A base class for activities
 */
public abstract class BaseActivity extends AppCompatActivity{

    @Inject
    protected Bus eventBus;

    @Inject
    protected Navigator navigator;

    /**
     * Register/Unregister for events which come from fragments
     */
    abstract void register();
    abstract void unregister();

    /**
     * Initialize the dependencies
     */
    abstract void inject();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        inject();
        super.onCreate(savedInstanceState);
    }


    @CallSuper
    @Override
    protected void onStart() {
        super.onStart();
        register();
    }

    @CallSuper
    @Override
    protected void onStop(){
        super.onStop();
        unregister();
    }


}
