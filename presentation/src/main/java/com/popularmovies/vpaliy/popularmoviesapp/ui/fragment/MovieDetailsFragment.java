package com.popularmovies.vpaliy.popularmoviesapp.ui.fragment;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.popularmovies.vpaliy.domain.model.MediaCover;
import com.popularmovies.vpaliy.domain.model.MovieDetails;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.App;
import com.popularmovies.vpaliy.popularmoviesapp.di.component.DaggerViewComponent;
import com.popularmovies.vpaliy.popularmoviesapp.di.module.PresenterModule;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MovieDetailsContract;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MovieDetailsContract.Presenter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.adapter.MovieBackdropsAdapter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.adapter.MovieDetailsAdapter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.configuration.PresentationConfiguration;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.Constants;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.Permission;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.relex.circleindicator.CircleIndicator;
import android.support.v7.graphics.Palette.Swatch;
import android.widget.TextView;
import butterknife.OnClick;
import android.annotation.TargetApi;
import javax.inject.Inject;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

import butterknife.BindView;

import static com.popularmovies.vpaliy.popularmoviesapp.ui.configuration.PresentationConfiguration.Presentation.CARD;


public class MovieDetailsFragment extends Fragment
        implements MovieDetailsContract.View{

    private Presenter presenter;
    private Unbinder unbinder;

    private MovieDetailsAdapter adapter;
    private MovieBackdropsAdapter backdropsAdapter;

    @BindView(R.id.movieImage)
    protected ImageView movieImage;

    @BindView(R.id.actionBar)
    protected Toolbar actionBar;

    @BindView(R.id.movieDetailsPager)
    protected ViewPager detailsPager;

    @BindView(R.id.tabLayout)
    protected TabLayout tabLayout;

    @BindView(R.id.backdropPager)
    protected ViewPager backdropPager;

    @BindView(R.id.indicator)
    protected CircleIndicator indicator;

    @BindView(R.id.ratingStar)
    protected ImageView ratingStar;

    @BindView(R.id.main_collapsing)
    protected CollapsingToolbarLayout collapsingToolbarLayout;

    protected Drawable starDrawable;

    @Inject
    protected PresentationConfiguration presentationConfigs;

    @BindView(R.id.favoriteButton)
    protected FloatingActionButton favoriteButton;

    private int favoriteColor;
    private int ID;
    private boolean isFavorite;
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
        this.presenter.attachView(this);
        presenter.start(ID);
    }

    @Override
    public void onPause() {
        super.onPause();
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
            initActionBar();

            adapter=new MovieDetailsAdapter(getContext(),getFragmentManager(),ID);
            backdropsAdapter=new MovieBackdropsAdapter(getContext());
            detailsPager.setAdapter(adapter);
            tabLayout.setupWithViewPager(detailsPager);
            backdropPager.setAdapter(backdropsAdapter);
            indicator.setViewPager(backdropPager);

        }
    }

    @Override
    public void shareWithMovie(MovieDetails details) {
        MediaCover cover=details.getMovieCover();
        String message=getContext().getString(R.string.checkOutMovie);
        message+=cover.getMovieTitle()+'\n';
        message+=Constants.MOVIE_URL_BASE+Integer.toString(details.getMovieId())+'\n';
        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT,message);
        intent.setType("text/plain");
        startActivity(Intent.createChooser(intent,getString(R.string.chooseToWhomToSend)));
    }

    private void initActionBar(){
        Drawable icon=MaterialDrawableBuilder.with(getContext())
                .setIcon(MaterialDrawableBuilder.IconValue.ARROW_LEFT)
                .setColor(Color.WHITE).build();
        actionBar.setNavigationIcon(icon);
        actionBar.inflateMenu(R.menu.details_menu);
        actionBar.setTitleTextColor(Color.WHITE);
        actionBar.setNavigationOnClickListener(view->{
            if(Permission.checkForVersion(Build.VERSION_CODES.LOLLIPOP)){
                getActivity().finishAfterTransition();
            }else{
                getActivity().finish();
            }
        });
        actionBar.setOnMenuItemClickListener(menuItem->{
            switch (menuItem.getItemId()){
                case R.id.shareAction:
                    presenter.shareWithMovie();
                    return true;
            }
            return false;
        });

        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        ViewGroup.MarginLayoutParams params= ViewGroup.MarginLayoutParams.class.cast(actionBar.getLayoutParams());
        params.topMargin=getStatusBarHeight();

    }

    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Inject
    @Override
    public void attachPresenter(@NonNull Presenter presenter) {
        this.presenter=presenter;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void showDetails(@NonNull MovieDetails movie) {
        showCoverDetails(movie.getMovieCover());
    }

    @Override
    public void showBackdrops(@NonNull List<String> backdrops) {
        backdropsAdapter.setData(backdrops);
        indicator.setViewPager(backdropPager);
    }

    @Override
    public void showCover(@NonNull MediaCover movieCover) {
        collapsingToolbarLayout.setTitle(movieCover.getMovieTitle());
        favoriteButton.setScaleX(0);
        favoriteButton.setScaleY(0);
        Glide.with(this)
                .load(movieCover.getPosterPath())
                .asBitmap()
                .centerCrop()
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .placeholder(R.drawable.placeholder)
                .into(new ImageViewTarget<Bitmap>(movieImage) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        isFavorite=movieCover.isFavorite();
                        movieImage.setImageBitmap(resource);
                        new Palette.Builder(resource)
                                .generate(MovieDetailsFragment.this::applyPalette);
                        //if(presentationConfigs.getPresentation()!=CARD) {
                            if (Permission.checkForVersion(Build.VERSION_CODES.LOLLIPOP)) {
                                movieImage.setTransitionName(imageTransitionName);
                                startTransition();
                            }
                       // }
                    }
                });
    }



    private void showCoverDetails(@NonNull MediaCover movieCover){
        if(getView()!=null) {
            TextView year = ButterKnife.findById(getView(),R.id.year);
            TextView duration=ButterKnife.findById(getView(),R.id.duration);
            TextView title=ButterKnife.findById(getView(),R.id.title);
            TextView genres=ButterKnife.findById(getView(),R.id.genres);
            TextView ratings=ButterKnife.findById(getView(),R.id.ratings);


            String bullet="\u25CF";
            String titleText=movieCover.getMovieTitle();
            String yearText=bullet+" "+movieCover.getReleaseDate();
            String durationText=bullet+" "+movieCover.getDuration();
            String ratingsText=Double.toString(movieCover.getAverageRate());

            ratings.setText(ratingsText);
            title.setText(titleText!=null?titleText:"");
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
            Swatch tabBackground=lightMutedSwatch!=null?lightMutedSwatch
                    :(lightVibrantSwatch!=null?lightVibrantSwatch:palette.getDominantSwatch());
            Swatch backgroundSwatch=darkMutedSwatch!=null?darkMutedSwatch:
                    (darkVibrantSwatch!=null?darkVibrantSwatch:palette.getDominantSwatch());
            setBackgroundSwatch(backgroundSwatch);
            setTabBackground(tabBackground);

        }
    }

    private void setTabBackground(Swatch swatch){
        if(swatch!=null) {
            tabLayout.setBackgroundColor(swatch.getRgb());
            collapsingToolbarLayout.setContentScrimColor(swatch.getRgb());
        }

    }

    private void setBackgroundSwatch(Swatch swatch){
        if(getView()!=null) {
            View view = ButterKnife.findById(getView(),R.id.detailsContainer);
            view.setBackgroundColor(swatch.getRgb());

            TextView year = ButterKnife.findById(getView(),R.id.year);
            TextView duration=ButterKnife.findById(getView(),R.id.duration);
            TextView title=ButterKnife.findById(getView(),R.id.title);
            TextView genres=ButterKnife.findById(getView(),R.id.genres);
            TextView ratings=ButterKnife.findById(getView(),R.id.ratings);

            favoriteButton.setBackgroundTintList(ColorStateList.valueOf(swatch.getRgb()));
            this.favoriteColor=swatch.getBodyTextColor();
            changeFavoriteColor(swatch.getBodyTextColor());

            favoriteButton.setVisibility(View.VISIBLE);
            favoriteButton.animate()
                    .scaleX(1).scaleY(1)
                    .setDuration(300).start();
            ratings.setTextColor(swatch.getTitleTextColor());
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

    @OnClick(R.id.favoriteButton)
    public void makeMovieFavorite(){
       // presenter.makeFavorite();
        isFavorite=!isFavorite;
        changeFavoriteColor(favoriteColor);

    }

    private void changeFavoriteColor(int color){
        starDrawable=MaterialDrawableBuilder.with(getContext())
                .setIcon(MaterialDrawableBuilder.IconValue.STAR)
                .setColor(isFavorite?getResources().getColor(R.color.colorFavorite):color)
                .build();
        ratingStar.setImageDrawable(starDrawable);
        favoriteButton.setImageDrawable(starDrawable);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(Constants.EXTRA_ID,ID);
        super.onSaveInstanceState(outState);
    }

}
