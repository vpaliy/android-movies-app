package com.popularmovies.vpaliy.popularmoviesapp.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.popularmovies.vpaliy.domain.model.Review;
import com.popularmovies.vpaliy.popularmoviesapp.App;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.di.component.DaggerViewComponent;
import com.popularmovies.vpaliy.popularmoviesapp.di.module.PresenterModule;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MovieReviewContract;
import com.popularmovies.vpaliy.popularmoviesapp.ui.adapter.MovieReviewAdapter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.Constants;

import java.util.List;

import javax.inject.Inject;
import butterknife.BindView;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MovieReviewContract.Presenter;


public class MovieReviewFragment extends Fragment
            implements MovieReviewContract.View{


    private int movieId;
    private Presenter presenter;

    private MovieReviewAdapter adapter;
    private Unbinder unbinder;

    @BindView(R.id.reviewList)
    protected RecyclerView reviewList;

    @BindView(R.id.emptyMessage)
    protected TextView emptyMessage;


    public static MovieReviewFragment newInstance(int movieId) {
        MovieReviewFragment fragment = new MovieReviewFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.EXTRA_ID, movieId);
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

        movieId=savedInstanceState.getInt(Constants.EXTRA_ID,0);
        initializeDependencies();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_movie_review,container,false);
        unbinder= ButterKnife.bind(this,root);
        return root;
    }


    @Override
    public void onViewCreated(View root, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(root, savedInstanceState);
        if(root!=null){
            presenter.start(movieId);
            adapter=new MovieReviewAdapter(getContext());

            reviewList.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
            reviewList.setAdapter(adapter);
            reviewList.addItemDecoration(new DividerItemDecoration(getContext(),
                    LinearLayoutManager.VERTICAL));
            reviewList.setHasFixedSize(true);
        }
    }

    private void initializeDependencies(){
        DaggerViewComponent.builder()
                .applicationComponent(App.appInstance().appComponent())
                .presenterModule(new PresenterModule())
                .build().inject(this);
    }

    @Override
    public void showReviews(@NonNull List<Review> reviews) {
        adapter.setData(reviews);
    }


    @Override
    public void showNoReviewMessage() {
        emptyMessage.setVisibility(View.VISIBLE);
        reviewList.setVisibility(View.GONE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(unbinder!=null){
            unbinder.unbind();
        }
    }

    @Inject
    @Override
    public void attachPresenter(@NonNull Presenter presenter) {
        this.presenter=presenter;
        this.presenter.attachView(this);
    }
}
