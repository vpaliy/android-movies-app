package com.popularmovies.vpaliy.popularmoviesapp.ui.fragment;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.popularmovies.vpaliy.domain.model.MovieCover;
import com.popularmovies.vpaliy.domain.model.MovieDetails;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.App;
import com.popularmovies.vpaliy.popularmoviesapp.di.component.DaggerViewComponent;
import com.popularmovies.vpaliy.popularmoviesapp.di.module.PresenterModule;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.DetailsMovieContract;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.DetailsMovieContract.Presenter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.adapter.MovieBackdropsAdapter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.adapter.MovieDetailsAdapter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.Constants;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.Permission;
import java.util.List;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.relex.circleindicator.CircleIndicator;
import android.support.v7.graphics.Palette.Swatch;

import android.annotation.TargetApi;
import javax.inject.Inject;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;

import butterknife.BindView;


public class MovieDetailsFragment extends Fragment
        implements DetailsMovieContract.View{

    private static final String TAG=MovieDetailsFragment.class.getSimpleName();

    private Presenter presenter;
    private Unbinder unbinder;

    private MovieDetailsAdapter adapter;

    @BindView(R.id.movieImage)
    protected ImageView movieImage;

    @BindView(R.id.movieDetailsPager)
    protected ViewPager detailsPager;

    @BindView(R.id.tabLayout)
    protected TabLayout tabLayout;

    @BindView(R.id.backdropPager)
    protected ViewPager backdropPager;

    @BindView(R.id.indicator)
    protected CircleIndicator indicator;

    private int ID;
    private String imageTransitionName;

    public static MovieDetailsFragment newInstance(@NonNull Bundle extraData){
        MovieDetailsFragment fragment=new MovieDetailsFragment();
        fragment.setArguments(extraData);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if(savedInstanceState==null){
            savedInstanceState=getArguments();
        }
        this.ID=savedInstanceState.getInt(Constants.EXTRA_ID);
        this.imageTransitionName=savedInstanceState.getString(Constants.EXTRA_TRANSITION_NAME);
        initializeDependencies();
    }


    private void initializeDependencies(){
        DaggerViewComponent.builder()
                .applicationComponent(App.appInstance().appComponent())
                .presenterModule(new PresenterModule())
                .build().inject(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.start(ID);
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.stop();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_movie_details,container,false);
        unbinder=ButterKnife.bind(this,root);
        return root;
    }

    @Override
    public void onViewCreated(View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);
        if(root!=null){
            adapter=new MovieDetailsAdapter(getContext(),getFragmentManager(),ID);
            detailsPager.setAdapter(adapter);
            tabLayout.setupWithViewPager(detailsPager);

        }
    }

    @Inject
    @Override
    public void attachPresenter(@NonNull Presenter presenter) {
        this.presenter=presenter;
        this.presenter.attachView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void showDetails(@NonNull MovieDetails movie) {

    }

    @Override
    public void showBackdrops(@NonNull List<String> backdrops) {
        MovieBackdropsAdapter adapter=new MovieBackdropsAdapter(getContext(),backdrops);
        backdropPager.setAdapter(adapter);
        indicator.setViewPager(backdropPager);
    }

    @Override
    public void showCover(@NonNull MovieCover movieCover) {
        showCoverDetails(movieCover);
        Glide.with(this)
                .fromResource()
                .asBitmap()
                .load(R.drawable.poster)
                .centerCrop()
                .into(new ImageViewTarget<Bitmap>(movieImage) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        movieImage.setImageBitmap(resource);
                        new Palette.Builder(resource)
                                .generate(MovieDetailsFragment.this::applyPalette);
                        //Log.d(TAG,imageTransitionName);
                        if(Permission.checkForVersion(Build.VERSION_CODES.LOLLIPOP)){
                            movieImage.setTransitionName(imageTransitionName);
                            startTransition();
                        }
                    }
                });
    }

    private void showCoverDetails(@NonNull MovieCover movieCover){
        if(getView()!=null) {
            TextView year = ButterKnife.findById(getView(),R.id.year);
            TextView duration=ButterKnife.findById(getView(),R.id.duration);
            TextView title=ButterKnife.findById(getView(),R.id.title);
            TextView genres=ButterKnife.findById(getView(),R.id.genres);

            String bullet="\u25CF";
            String titleText=movieCover.getMovieTitle();
            String yearText=bullet+" "+Integer.toString(movieCover.getReleaseYear());
            String durationText=bullet+" "+movieCover.getDuration();

            title.setText(titleText);
            year.setText(yearText);
            duration.setText(durationText);

            List<String> genreList=movieCover.getGenres();
            if(genreList!=null){
                String result="";
                for(int index=0;index<genreList.size();index++){
                    result+=genreList.get(index);
                    if(index!=genreList.size()-1){
                        result+=", ";
                    }
                }
                genres.setText(result);
            }
        }
    }

    private void applyPalette(Palette palette){
        if (palette != null) {
            Swatch darkVibrantSwatch    = palette.getDarkVibrantSwatch();
            Swatch darkMutedSwatch      = palette.getDarkMutedSwatch();
            Swatch lightVibrantSwatch   = palette.getLightVibrantSwatch();
            Swatch lightMutedSwatch     = palette.getLightMutedSwatch();
            Swatch vibrantSwatch        = palette.getVibrantSwatch();

            setBackgroundSwatch(darkMutedSwatch);
            setTabBackground(lightMutedSwatch);

        }
    }

    private void setTabBackground(Swatch swatch){
        tabLayout.setBackgroundColor(swatch.getRgb());

    }

    private void setBackgroundSwatch(Swatch swatch){
        if(getView()!=null) {
            View view = ButterKnife.findById(getView(),R.id.detailsContainer);
            view.setBackgroundColor(swatch.getRgb());

            TextView year = ButterKnife.findById(getView(),R.id.year);
            TextView duration=ButterKnife.findById(getView(),R.id.duration);
            TextView title=ButterKnife.findById(getView(),R.id.title);
            TextView genres=ButterKnife.findById(getView(),R.id.genres);

            year.setTextColor(swatch.getTitleTextColor());
            duration.setTextColor(swatch.getTitleTextColor());
            title.setTextColor(swatch.getTitleTextColor());
            genres.setTextColor(swatch.getTitleTextColor());

        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void startTransition(){
        movieImage.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                movieImage.getViewTreeObserver().removeOnPreDrawListener(this);
                getActivity().startPostponedEnterTransition();
                return true;
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(Constants.EXTRA_ID,ID);
        super.onSaveInstanceState(outState);
    }

}
