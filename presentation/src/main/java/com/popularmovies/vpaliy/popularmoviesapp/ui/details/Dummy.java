package com.popularmovies.vpaliy.popularmoviesapp.ui.details;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.popularmovies.vpaliy.domain.model.MediaCover;
import com.popularmovies.vpaliy.domain.model.MovieDetails;
import com.popularmovies.vpaliy.popularmoviesapp.App;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.di.component.DaggerViewComponent;
import com.popularmovies.vpaliy.popularmoviesapp.di.module.PresenterModule;
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BaseActivity;
import com.popularmovies.vpaliy.popularmoviesapp.ui.details.adapter.MovieBackdropsAdapter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.details.mvp.contract.MovieDetailsContract;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.Constants;
import com.popularmovies.vpaliy.popularmoviesapp.ui.view.ParallaxRatioViewPager;
import com.rd.PageIndicatorView;
import butterknife.BindView;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import javax.inject.Inject;

import java.util.Collections;
import java.util.List;
import com.popularmovies.vpaliy.popularmoviesapp.ui.details.mvp.contract.MovieDetailsContract.Presenter;
import butterknife.ButterKnife;

public class Dummy extends BaseActivity
        implements MovieDetailsContract.View{

    @BindView(R.id.backdrop_pager)
    protected ViewPager pager;

    @BindView(R.id.page_indicator)
    protected PageIndicatorView indicatorView;

    private MovieBackdropsAdapter adapter;

    private Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_media_details);
        ButterKnife.bind(this);
        supportPostponeEnterTransition();
        if(savedInstanceState==null) savedInstanceState=getIntent().getExtras();
        int mediaId=savedInstanceState.getInt(Constants.EXTRA_ID);
        String posterPath=savedInstanceState.getString(Constants.EXTRA_DATA);

        adapter=new MovieBackdropsAdapter(getApplicationContext());
        adapter.setData(Collections.singletonList(posterPath));
        adapter.setCallback(image->{
            image.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    image.getViewTreeObserver().removeOnPreDrawListener(this);
                    supportStartPostponedEnterTransition();
                    return true;
                }
            });
        });
        pager.setAdapter(adapter);
        //presenter.start(mediaId);

    }

    @Override
    public void inject() {
        DaggerViewComponent.builder()
                .applicationComponent(App.appInstance().appComponent())
                .presenterModule(new PresenterModule())
                .build().inject(this);
    }

    @Override
    public void showDetails(@NonNull MovieDetails movieDetails) {

    }

    @Override
    public void showBackdrops(@NonNull List<String> backdrops) {
    }

    @Override
    public void showCover(@NonNull MediaCover movieCover) {

    }


    @Override
    public void shareWithMovie(MovieDetails details) {

    }

    @Inject
    @Override
    public void attachPresenter(@NonNull Presenter presenter) {
        this.presenter=presenter;
        this.presenter.attachView(this);
    }

    @Override
    public void handleEvent(@NonNull Object event) {

    }
}
