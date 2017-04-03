package com.popularmovies.vpaliy.popularmoviesapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.popularmovies.vpaliy.domain.model.MovieInfo;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MovieInfoContract;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MovieInfoContract.Presenter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.Constants;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MovieInfoFragment extends Fragment
        implements MovieInfoContract.View{


    private Presenter presenter;
    private Unbinder unbinder;

    private int movieId;

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
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_movie_info,container,false);
        unbinder= ButterKnife.bind(this,root);
        return root;
    }

    @Override
    public void attachPresenter(@NonNull Presenter presenter) {
        this.presenter=presenter;
        this.presenter.attachView(this);
    }

    @Override
    public void showInfo(@NonNull MovieInfo movieInfo) {

    }

    @Override
    public void showTrailer() {

    }

    @Override
    public String toString() {
        return getContext().getString(R.string.intoTitle);
    }
}
