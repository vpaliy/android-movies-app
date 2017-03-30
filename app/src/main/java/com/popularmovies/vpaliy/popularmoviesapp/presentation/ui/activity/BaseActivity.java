package com.popularmovies.vpaliy.popularmoviesapp.presentation.ui.activity;


import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import com.squareup.otto.Bus;

import javax.inject.Inject;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;

/**
 * A base class for activities
 */
public abstract class BaseActivity extends AppCompatActivity{

    @Inject
    protected Bus eventBus;

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
        super.onCreate(savedInstanceState);
        inject();
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
