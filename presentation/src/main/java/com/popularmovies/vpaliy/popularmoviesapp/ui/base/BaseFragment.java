package com.popularmovies.vpaliy.popularmoviesapp.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.popularmovies.vpaliy.popularmoviesapp.ui.base.bus.RxBus;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {

    private Unbinder unbinder;

    @Inject
    protected RxBus rxBus;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        initializeDependencies();
    }

    public void bind(View root){
        unbinder= ButterKnife.bind(this,root);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(unbinder!=null){
            unbinder.unbind();
        }
    }

    public abstract void initializeDependencies();
}
