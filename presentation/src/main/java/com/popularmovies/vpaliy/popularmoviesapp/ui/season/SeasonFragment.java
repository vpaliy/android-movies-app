package com.popularmovies.vpaliy.popularmoviesapp.ui.season;

import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.popularmovies.vpaliy.domain.model.ActorCover;
import com.popularmovies.vpaliy.domain.model.Episode;
import com.popularmovies.vpaliy.domain.model.SeasonCover;
import com.popularmovies.vpaliy.domain.model.Trailer;
import com.popularmovies.vpaliy.popularmoviesapp.App;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.di.component.DaggerViewComponent;
import com.popularmovies.vpaliy.popularmoviesapp.di.module.PresenterModule;
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BaseFragment;

import java.util.Arrays;
import java.util.List;

import com.popularmovies.vpaliy.popularmoviesapp.ui.details.adapter.InfoAdapter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.details.adapter.MovieCastAdapter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.details.adapter.MovieTrailersAdapter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.season.SeasonContract.Presenter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.Constants;
import com.popularmovies.vpaliy.popularmoviesapp.ui.view.ElasticDismissLayout;
import com.popularmovies.vpaliy.popularmoviesapp.ui.view.FABToggle;
import com.popularmovies.vpaliy.popularmoviesapp.ui.view.ParallaxImageView;
import com.popularmovies.vpaliy.popularmoviesapp.ui.view.ParallaxRatioViewPager;
import com.popularmovies.vpaliy.popularmoviesapp.ui.view.TranslatableLayout;
import com.rd.PageIndicatorView;
import com.rd.animation.type.AnimationType;
import com.vpaliy.chips_lover.ChipsLayout;

import javax.inject.Inject;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;

import butterknife.BindView;

import static com.google.common.base.Preconditions.checkNotNull;

public class SeasonFragment extends BaseFragment
    implements SeasonContract.View{

    @BindView(R.id.backdrop_pager)
    protected ParallaxRatioViewPager pager;

    @BindView(R.id.page_indicator)
    protected PageIndicatorView indicatorView;

    @BindView(R.id.back_wrapper)
    protected View actionBar;

    @BindView(R.id.season_number)
    protected TextView seasonNumber;

    @BindView(R.id.details_background)
    protected TranslatableLayout detailsParent;

    @BindView(R.id.season_title)
    protected TextView seasonTitle;

    @BindView(R.id.air_date)
    protected TextView airDate;

    @BindView(R.id.season_overview)
    protected TextView seasonOverview;

    @BindView(R.id.details)
    protected RecyclerView info;

    @BindView(R.id.poster)
    protected ParallaxImageView poster;

    @BindView(R.id.share_fab)
    protected FABToggle toggle;

    @BindView(R.id.parent)
    protected ElasticDismissLayout parent;

    private InfoAdapter infoAdapter;
    private String seasonId;
    private Presenter presenter;

    public static SeasonFragment newInstance(Bundle args){
        SeasonFragment seasonFragment=new SeasonFragment();
        seasonFragment.setArguments(args);
        return seasonFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_season,container,false);
        bind(root);
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState==null) savedInstanceState=getArguments();
        seasonId=savedInstanceState.getString(Constants.EXTRA_ID,null);
        seasonId=checkNotNull(seasonId);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(view!=null){
            infoAdapter=new InfoAdapter(getContext());
            presenter.start(seasonId);
            info.setAdapter(infoAdapter);
            pager.post(SeasonFragment.this::adjustElements);
        }
    }

    @Override
    public void initializeDependencies() {
        DaggerViewComponent.builder()
                .presenterModule(new PresenterModule())
                .applicationComponent(App.appInstance().appComponent())
                .build().inject(this);
    }

    @Override
    public void showDescription(@NonNull String description) {
        seasonOverview.setText(description);
    }

    @Override
    public void showCover(@NonNull SeasonCover cover) {
        seasonOverview.setText(cover.getOverview());
        seasonTitle.setText(cover.getSeasonName());
        airDate.setText(cover.getAirDate());
//        seasonNumber.setText(cover.getSeasonNumber());
    }

    @Override
    public void showTrailers(@NonNull List<Trailer> trailers) {
        MovieTrailersAdapter adapter=new MovieTrailersAdapter(getContext());
        adapter.setData(trailers);
        infoAdapter.addWrapper(InfoAdapter.MovieListWrapper.wrap(adapter,getString(R.string.media_trailers)));
    }

    @Override
    public void showPoster(@NonNull String posterPath) {
        Glide.with(this)
                .load(posterPath)
                .priority(Priority.IMMEDIATE)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(poster);

    }

    @Override
    public void showEpisodes(@NonNull List<Episode> episodes) {

    }

    @Override
    public void showImages(@NonNull List<String> images) {
        ImagesAdapter adapter=new ImagesAdapter(getContext());
        adapter.setData(images);
        Log.d(SeasonFragment.class.getSimpleName(),Integer.toString(images.size()));
        pager.setAdapter(adapter);
    }

    @Override
    public void showCast(@NonNull List<ActorCover> cast) {
        MovieCastAdapter adapter=new MovieCastAdapter(getContext(),rxBus);
        adapter.setData(cast);
        infoAdapter.addWrapper(InfoAdapter.MovieListWrapper.wrap(adapter,getString(R.string.cast_title)));
    }

    @Inject @Override
    public void attachPresenter(@NonNull SeasonContract.Presenter presenter) {
        this.presenter=presenter;
        this.presenter.attachView(this);
    }


    private void adjustElements(){
        indicatorView.setAnimationType(AnimationType.WORM);
        indicatorView.setTranslationY(pager.getHeight()-indicatorView.getHeight()*2.5f);
        detailsParent.setStaticOffset(pager.getHeight());
        detailsParent.setOffset(pager.getHeight());
        View blank=infoAdapter.getBlank();
        ViewGroup.LayoutParams params=blank.getLayoutParams();
        params.height=detailsParent.getHeight();
        blank.setLayoutParams(params);

        final float posterOffset=detailsParent.getHeight()/2-poster.getHeight()*.33f;
        poster.setStaticOffset(posterOffset);
        poster.setOffset(posterOffset);
        poster.setPinned(true);
        toggle.setMinOffset(ViewCompat.getMinimumHeight(pager)-toggle.getHeight()/2);
        ParamsFactory.shiftElementsFrom(poster, Arrays.asList(seasonTitle,airDate,seasonNumber,seasonOverview));
    }

    private static class ParamsFactory{

        static void shiftElementsFrom(View target, List<View> shiftElements){
            float posterY=getBottomY(target)+target.getHeight();
            final float posterX=target.getX()+target.getWidth();
            final float spacing=target.getResources().getDimension(R.dimen.spacing_media_details);
            final float offset=target.getWidth()+spacing;
            for(int index=0;index<shiftElements.size();index++){
                View element=shiftElements.get(index);
                if(posterX>=element.getX() && (posterY+spacing)>=getBottomY(element)) {
                    if (index != 0 && shouldShiftVertically(posterY, element,spacing)) {
                        posterY=shiftVertically(element,posterY);
                    } else {
                        shiftHorizontally(element, offset);
                    }
                }
            }
        }

        static boolean shouldShiftVertically(float offsetY, View target, float spacing){
            final float targetY=getBottomY(target);
            final float offsetDiff=targetY-offsetY;
            return offsetDiff>=0 && offsetDiff<=spacing;
        }

        static float shiftVertically(View target, float posterY){
            final float spacing=target.getResources().getDimension(R.dimen.spacing_media_details);
            ViewGroup.MarginLayoutParams params = ViewGroup.MarginLayoutParams.class.cast(target.getLayoutParams());
            final float offsetY = posterY - getBottomY(target) + spacing;
            params.topMargin += offsetY;
            posterY -= offsetY;
            target.setLayoutParams(params);
            return posterY;
        }

        static void shiftHorizontally(View target, float offset){
            ViewGroup.MarginLayoutParams params=ViewGroup.MarginLayoutParams.class.cast(target.getLayoutParams());
            params.leftMargin+=offset;
            target.setLayoutParams(params);
        }

        static float getBottomY(View view){
            int[] screenLocation=new int[2];
            view.getLocationOnScreen(screenLocation);
            return screenLocation[1];
        }
    }
}
