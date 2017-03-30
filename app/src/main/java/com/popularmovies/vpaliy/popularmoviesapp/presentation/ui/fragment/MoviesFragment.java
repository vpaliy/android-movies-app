package com.popularmovies.vpaliy.popularmoviesapp.presentation.ui.fragment;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.domain.model.Movie;
import com.popularmovies.vpaliy.popularmoviesapp.presentation.mvp.contract.MoviesContract;
import com.popularmovies.vpaliy.popularmoviesapp.presentation.mvp.contract.MoviesContract.Presenter;
import com.popularmovies.vpaliy.popularmoviesapp.presentation.ui.adapter.MoviesAdapter;
import com.popularmovies.vpaliy.popularmoviesapp.presentation.ui.utils.events.ClickedMovieEvent;
import com.popularmovies.vpaliy.popularmoviesapp.presentation.ui.utils.events.ExposeDetailsEvent;
import com.squareup.otto.Bus;
import java.util.List;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import javax.inject.Inject;
import butterknife.BindView;
import com.squareup.otto.Subscribe;

public class MoviesFragment extends Fragment
        implements MoviesContract.View{

    private Presenter presenter;
    private MoviesAdapter adapter;

    @Inject
    protected Bus eventBus;

    @BindView(R.id.refresher)
    protected SwipeRefreshLayout swipeRefresher;

    @BindView(R.id.recycleView)
    protected RecyclerView recyclerView;

    private Unbinder unbinder;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        initializeDependencies();

    }

    private void initializeDependencies(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_movies,container,false);
        unbinder=ButterKnife.bind(this,root);
        return root;
    }

    @Override
    public void onViewCreated(View root, @Nullable Bundle savedInstanceState) {
        if(root!=null){
            swipeRefresher.setOnRefreshListener(presenter::requestDataRefresh);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        eventBus.register(this);

    }

    @Override
    public void onStop() {
        super.onStop();
        eventBus.unregister(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.stop();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Inject
    @Override
    public void attachPresenter(@NonNull Presenter presenter) {
        this.presenter=presenter;
        this.presenter.attachView(this);
    }

    @Override
    public void showMovies(@NonNull List<Movie> movies) {
        adapter=new MoviesAdapter(getContext(),movies,eventBus);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), R.integer.spinCount,GridLayoutManager.VERTICAL,false));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), GridLayoutManager.VERTICAL));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void showEmptyMessage() {
        if(getView()!=null){
            Snackbar.make(getView(),R.string.noDataMessage,Snackbar.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    public void showErrorMessage() {
        if(getView()!=null){
            Snackbar.make(getView(),R.string.dataError,Snackbar.LENGTH_LONG)
                    .setAction(R.string.refreshAction,v->presenter.requestDataRefresh())
                    .show();
        }
    }

    @Override
    public void setLoadingIndicator(boolean isLoading) {
        swipeRefresher.setRefreshing(isLoading);
    }

    @Subscribe
    public void catchMovieClick(@NonNull ClickedMovieEvent event){
        eventBus.post(new ExposeDetailsEvent(event.getTransitionWrapper()));
    }
}
