package com.popularmovies.vpaliy.popularmoviesapp.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.popularmovies.vpaliy.domain.model.MovieCover;
import com.popularmovies.vpaliy.domain.model.MovieInfo;
import com.popularmovies.vpaliy.popularmoviesapp.App;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.di.component.DaggerViewComponent;
import com.popularmovies.vpaliy.popularmoviesapp.di.module.PresenterModule;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MovieInfoContract;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MovieInfoContract.Presenter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.adapter.RelatedMoviesAdapter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.Constants;
import com.squareup.otto.Bus;
import at.blogc.android.views.ExpandableTextView;
import java.util.List;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import javax.inject.Inject;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import butterknife.BindView;
import butterknife.OnClick;

public class MovieInfoFragment extends Fragment
        implements MovieInfoContract.View{


    private Presenter presenter;
    private Unbinder unbinder;
    private int movieId;

    @BindView(R.id.infoContainer)
    protected ViewGroup infoContainer;

    @Inject
    protected Bus eventBus;

    @BindView(R.id.budget)
    protected TextView movieBudget;

    @BindView(R.id.directedBy)
    protected TextView movieDirectedBy;

    @BindView(R.id.revenue)
    protected TextView movieRevenue;

    @BindView(R.id.releaseDate)
    protected TextView movieReleaseDate;

    @BindView(R.id.movieDescription)
    protected ExpandableTextView movieDescription;

    private View similarMoviesView;


    public static MovieInfoFragment newInstance(int movieId){
        MovieInfoFragment fragment=new MovieInfoFragment();
        Bundle args=new Bundle();
        args.putInt(Constants.EXTRA_ID,movieId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if(savedInstanceState==null){
            savedInstanceState=getArguments();
        }
        this.movieId=savedInstanceState.getInt(Constants.EXTRA_ID);
        initializeDependencies();
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.start(movieId);
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.stop();
    }

    private void initializeDependencies(){
        DaggerViewComponent.builder()
                .applicationComponent(App.appInstance().appComponent())
                .presenterModule(new PresenterModule())
                .build().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_movie_info,container,false);
        unbinder= ButterKnife.bind(this,root);
        return root;
    }

    @Override
    public void onViewCreated(View root, @Nullable Bundle savedInstanceState) {
        if(root!=null){
            movieDescription.setOnClickListener(v->movieDescription.toggle());
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
    public void showGeneralInfo(@NonNull MovieInfo movieInfo) {
        final Context context=getContext();
        final String NA=context.getString(R.string.NA);
        final String budgetText=movieInfo.getBudget()!=null?movieInfo.getBudget():NA;
        final String directedByText=movieInfo.getDirector()!=null?movieInfo.getDirector():NA;
        final String revenueText=movieInfo.getRevenue()!=null?movieInfo.getRevenue():NA;
        final String releaseDateText=movieInfo.getReleaseDate()!=null?movieInfo.getReleaseDate().toString():NA;
        final String descriptionText=movieInfo.getDescription()!=null?movieInfo.getDescription():NA;

        movieDescription.setText(descriptionText);
        movieBudget.setText(budgetText);
        movieDirectedBy.setText(directedByText);
        movieRevenue.setText(revenueText);
        movieReleaseDate.setText(releaseDateText);



    }

    @Override
    public void showSimilarMovies(@NonNull List<MovieCover> similarMovies) {
        if(similarMoviesView==null){
            final Context context=getContext();
            final LayoutInflater inflater=LayoutInflater.from(context);
            similarMoviesView=inflater.inflate(R.layout.movie_similar_card,infoContainer,false);
             TextView cardTitle=ButterKnife.findById(similarMoviesView,R.id.cardTitle);
             RecyclerView movieList=ButterKnife.findById(similarMoviesView,R.id.additionalList);
            movieList.setAdapter(new RelatedMoviesAdapter(context,similarMovies,eventBus));
            cardTitle.setText(context.getString(R.string.movieSimilarMovies));
            movieList.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
            movieList.setNestedScrollingEnabled(false);
            movieList.setHasFixedSize(true);
            infoContainer.addView(similarMoviesView);

            similarMoviesView=inflater.inflate(R.layout.movie_similar_card,infoContainer,false);
            cardTitle=ButterKnife.findById(similarMoviesView,R.id.cardTitle);
            movieList=ButterKnife.findById(similarMoviesView,R.id.additionalList);
            movieList.setAdapter(new RelatedMoviesAdapter(context,similarMovies,eventBus));
            cardTitle.setText(context.getString(R.string.movieSimilarMovies));
            movieList.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
            movieList.setNestedScrollingEnabled(false);
            movieList.setHasFixedSize(true);
            infoContainer.addView(similarMoviesView);
        }

    }

    @OnClick(R.id.movieDescription)
    public void expandText(){
        movieDescription.toggle();
    }
}
