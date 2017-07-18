package com.popularmovies.vpaliy.popularmoviesapp.ui.season;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.popularmovies.vpaliy.domain.model.ActorCover;
import com.popularmovies.vpaliy.domain.model.Episode;
import com.popularmovies.vpaliy.domain.model.Trailer;
import com.popularmovies.vpaliy.popularmoviesapp.App;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.di.component.DaggerViewComponent;
import com.popularmovies.vpaliy.popularmoviesapp.di.module.PresenterModule;
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BaseFragment;
import java.util.List;
import com.popularmovies.vpaliy.popularmoviesapp.ui.season.SeasonContract.Presenter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.Constants;
import javax.inject.Inject;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import static com.google.common.base.Preconditions.checkNotNull;

public class SeasonFragment extends BaseFragment
    implements SeasonContract.View{

    private String seasonId;
    private Presenter presenter;

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
        }
    }

    public static SeasonFragment newInstance(Bundle args){
        SeasonFragment seasonFragment=new SeasonFragment();
        seasonFragment.setArguments(args);
        return seasonFragment;
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

    }

    @Override
    public void showTrailers(@NonNull List<Trailer> trailers) {

    }

    @Override
    public void showPoster(@NonNull String poster) {

    }

    @Override
    public void showEpisodes(@NonNull List<Episode> episodes) {

    }

    @Override
    public void showImages(@NonNull List<String> images) {

    }

    @Override
    public void showCast(@NonNull List<ActorCover> cast) {

    }

    @Inject @Override
    public void attachPresenter(@NonNull SeasonContract.Presenter presenter) {
        this.presenter=presenter;
        this.presenter.attachView(this);
    }
}
