package com.popularmovies.vpaliy.popularmoviesapp.presentation.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.domain.model.Movie;
import com.popularmovies.vpaliy.popularmoviesapp.presentation.mvp.contract.DetailsMovieContract;
import com.popularmovies.vpaliy.popularmoviesapp.presentation.mvp.contract.DetailsMovieContract.Presenter;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class MovieDetailsFragment extends Fragment
    implements DetailsMovieContract.View{

    private Presenter mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeDependencies();
    }

    private void initializeDependencies(){

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_movie_details,container,false);
        ButterKnife.bind(this,root);
        return root;
    }

    @Inject
    @Override
    public void attachPresenter(@NonNull Presenter presenter) {
        this.mPresenter=presenter;
        this.mPresenter.attachView(this);
    }

    @Override
    public void showDetails(@NonNull Movie movie) {

    }
}
