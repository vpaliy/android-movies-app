package com.popularmovies.vpaliy.popularmoviesapp.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.LinkedList;
import java.util.List;

import at.blogc.android.views.ExpandableTextView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import javax.inject.Inject;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;

import butterknife.BindView;

public class  MovieInfoFragment extends Fragment
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


    @BindView(R.id.revenue)
    protected TextView movieRevenue;

    @BindView(R.id.releaseDate)
    protected TextView movieReleaseDate;

    @BindView(R.id.movieDescription)
    protected ExpandableTextView movieDescription;

    @BindView(R.id.ratings)
    protected TextView ratings;

    @BindView(R.id.similarMoviesCard)
    protected CardView similarMoviesCard;


    private List<RelatedMoviesAdapter> adapterList;

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
    }



    @Override
    public void onResume() {
        super.onResume();
        if(adapterList!=null){
            for(RelatedMoviesAdapter adapter:adapterList){
                adapter.onResume();
            }
        }
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
            presenter.start(movieId);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Constants.EXTRA_ID,movieId);
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
        presenter.stop();
        unbinder.unbind();
    }

    @Override
    public void showGeneralInfo(@NonNull MovieInfo movieInfo) {
        final Context context=getContext();
        final String NA=context.getString(R.string.NA);
        final String budgetText=movieInfo.getBudget()!=null?"$"+movieInfo.getBudget():NA;
        final String revenueText=movieInfo.getRevenue()!=null?"$"+movieInfo.getRevenue():NA;
        final String releaseDateText=movieInfo.getReleaseDate()!=null?movieInfo.getReleaseDate().toString():NA;
        final String descriptionText=movieInfo.getDescription()!=null?movieInfo.getDescription():NA;
        final String ratingsText=Double.toString(movieInfo.getAverageRate());

        movieDescription.setText(descriptionText);
        movieBudget.setText(budgetText);
        movieRevenue.setText(revenueText);
        movieReleaseDate.setText(releaseDateText);
        ratings.setText(ratingsText);



    }


    @Override
    public void showSimilarMovies(@NonNull List<MovieCover> similarMovies) {
        similarMoviesCard.setVisibility(View.VISIBLE);
        final Context context=getContext();
        final TextView cardTitle=ButterKnife.findById(similarMoviesCard,R.id.cardTitle);
        final RecyclerView similarMoviesList=ButterKnife.findById(similarMoviesCard,R.id.additionalList);

        RelatedMoviesAdapter adapter=new RelatedMoviesAdapter(context,similarMovies,eventBus);
        cardTitle.setText(context.getString(R.string.movieSimilarMovies));
        similarMoviesList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false));
        similarMoviesList.setAdapter(adapter);
        similarMoviesList.setNestedScrollingEnabled(false);
        similarMoviesList.getLayoutManager().setAutoMeasureEnabled(true);
        similarMoviesList.setHasFixedSize(true);

        if(adapterList==null){
            adapterList=new LinkedList<>();
        }
        adapterList.add(adapter);


    }


    @OnClick(R.id.movieDescription)
    public void expandText(){
        movieDescription.toggle();
    }
}
