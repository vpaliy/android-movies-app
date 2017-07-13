package com.popularmovies.vpaliy.popularmoviesapp.ui.details;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.popularmovies.vpaliy.domain.model.MediaCover;
import com.popularmovies.vpaliy.domain.model.MovieDetails;
import com.popularmovies.vpaliy.popularmoviesapp.App;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.di.component.DaggerViewComponent;
import com.popularmovies.vpaliy.popularmoviesapp.di.module.PresenterModule;
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BaseActivity;
import com.popularmovies.vpaliy.popularmoviesapp.ui.details.adapter.InfoAdapter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.details.adapter.MovieBackdropsAdapter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.details.adapter.MovieTrailersAdapter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.details.mvp.contract.MovieDetailsContract;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.Constants;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.PresentationUtils;
import com.popularmovies.vpaliy.popularmoviesapp.ui.view.ParallaxRatioViewPager;
import com.popularmovies.vpaliy.popularmoviesapp.ui.view.TranslatableLayout;
import com.rd.PageIndicatorView;
import butterknife.BindView;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import javax.inject.Inject;

import java.util.Collections;
import java.util.List;
import com.popularmovies.vpaliy.popularmoviesapp.ui.details.mvp.contract.MovieDetailsContract.Presenter;
import com.rd.animation.type.AnimationType;
import com.vpaliy.chips_lover.ChipBuilder;
import com.vpaliy.chips_lover.ChipsLayout;

import butterknife.ButterKnife;

public class Dummy extends BaseActivity
        implements MovieDetailsContract.View{

    @BindView(R.id.backdrop_pager)
    protected ParallaxRatioViewPager pager;

    @BindView(R.id.page_indicator)
    protected PageIndicatorView indicatorView;

    @BindView(R.id.back_wrapper)
    protected View actionBar;

    @BindView(R.id.details_background)
    protected TranslatableLayout detailsParent;

    @BindView(R.id.media_title)
    protected TextView mediaTitle;

    @BindView(R.id.media_ratings)
    protected TextView mediaRatings;

    @BindView(R.id.chipsContainer)
    protected ChipsLayout tags;

    @BindView(R.id.media_release_year)
    protected TextView releaseYear;

    @BindView(R.id.media_description)
    protected TextView mediaDescription;

    @BindView(R.id.details)
    protected RecyclerView info;


    private InfoAdapter infoAdapter;
    private MovieBackdropsAdapter adapter;
    private Presenter presenter;


    private static final String TAG=Dummy.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_media_details);
        ButterKnife.bind(this);
        supportPostponeEnterTransition();
        if(savedInstanceState==null) savedInstanceState=getIntent().getExtras();
        int mediaId=savedInstanceState.getInt(Constants.EXTRA_ID);
        String posterPath=savedInstanceState.getString(Constants.EXTRA_DATA);

        infoAdapter=new InfoAdapter(this);
        adapter=new MovieBackdropsAdapter(getApplicationContext());
        adapter.setData(Collections.singletonList(posterPath));
        adapter.setCallback((image,bitmap)->{
            indicatorView.setAnimationType(AnimationType.WORM);
            indicatorView.setTranslationY(image.getHeight()-indicatorView.getHeight()*2.5f);
            detailsParent.setStaticOffset(image.getHeight());
            detailsParent.setOffset(image.getHeight());
            View blank=infoAdapter.getBlank();
            ViewGroup.LayoutParams params=blank.getLayoutParams();
            params.height=image.getHeight()+detailsParent.getHeight();
            blank.setLayoutParams(params);
            new Palette.Builder(bitmap).generate(Dummy.this::applyPalette);
            image.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    image.getViewTreeObserver().removeOnPreDrawListener(this);
                    supportStartPostponedEnterTransition();
                    return true;
                }
            });
        });
        info.setAdapter(infoAdapter);
        pager.setAdapter(adapter);
        presenter.start(mediaId);

        int height=PresentationUtils.getStatusBarHeight(getResources());
        actionBar.setPadding(0,height,0,0);

        info.addOnScrollListener(listener);
        info.setOnFlingListener(flingListener);
    }

    private void applyPalette(Palette palette){
        Palette.Swatch swatch=palette.getDarkVibrantSwatch();
        if(swatch==null) swatch=palette.getDominantSwatch();
        //apply if not null
        if(swatch!=null){
            detailsParent.setBackgroundColor(swatch.getRgb());
            ChipBuilder builder=tags.getChipBuilder()
                    .setBackgroundColor(swatch.getTitleTextColor())
                    .setTextColor(swatch.getBodyTextColor());
            tags.updateChipColors(builder);
            mediaTitle.setTextColor(swatch.getBodyTextColor());
            releaseYear.setTextColor(swatch.getBodyTextColor());
            mediaRatings.setTextColor(swatch.getBodyTextColor());
            mediaDescription.setTextColor(swatch.getBodyTextColor());
            setDrawableColor(releaseYear,swatch.getBodyTextColor());
            setDrawableColor(mediaRatings,swatch.getBodyTextColor());
        }
    }

    private void setDrawableColor(TextView view, int color){
        Drawable[] drawables=view.getCompoundDrawables();
        for(Drawable drawable:drawables){
            if(drawable!=null){
                drawable.mutate();
                DrawableCompat.setTint(drawable,color);
            }
        }
    }

    private RecyclerView.OnFlingListener flingListener = new RecyclerView.OnFlingListener() {
        @Override
        public boolean onFling(int velocityX, int velocityY) {
            pager.setImmediatePin(true);
            return false;
        }
    };

    private RecyclerView.OnScrollListener listener=new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            pager.setImmediatePin(newState == RecyclerView.SCROLL_STATE_SETTLING);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            final int scrollY= infoAdapter.getBlank().getTop();
            pager.setOffset(scrollY);
            detailsParent.setOffset(detailsParent.getStaticOffset()+scrollY);
        }
    };

    @Override
    public void inject() {
        DaggerViewComponent.builder()
                .applicationComponent(App.appInstance().appComponent())
                .presenterModule(new PresenterModule())
                .build().inject(this);
    }

    @Override
    public void showDetails(@NonNull MovieDetails movieDetails) {
        infoAdapter.setMovieInfo(movieDetails.getMovieInfo());
        mediaDescription.setText(movieDetails.getMovieInfo().getDescription());
        MovieTrailersAdapter adapter=new MovieTrailersAdapter(this);
        adapter.setData(movieDetails.getTrailers());
        infoAdapter.addWrapper(InfoAdapter.MovieListWrapper.wrap(adapter,getString(R.string.media_trailers)));
    }

    @Override
    public void showBackdrops(@NonNull List<String> backdrops) {
        adapter.appendData(backdrops);
    }

    @Override
    public void showCover(@NonNull MediaCover movieCover) {
        mediaTitle.setText(movieCover.getMovieTitle());
        releaseYear.setText(movieCover.getFormattedDate());
        mediaRatings.setText(movieCover.getAverageRate());
        tags.setTags(movieCover.getGenres());
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
