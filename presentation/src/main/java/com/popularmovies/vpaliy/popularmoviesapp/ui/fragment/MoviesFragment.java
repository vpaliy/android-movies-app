package com.popularmovies.vpaliy.popularmoviesapp.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.popularmovies.vpaliy.domain.model.MovieCover;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.App;
import com.popularmovies.vpaliy.popularmoviesapp.di.component.DaggerViewComponent;
import com.popularmovies.vpaliy.popularmoviesapp.di.module.PresenterModule;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MoviesContract;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MoviesContract.Presenter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.adapter.MoviesAdapter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.events.ClickedMovieEvent;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.events.ExposeDetailsEvent;
import com.squareup.otto.Bus;
import java.util.List;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import javax.inject.Inject;
import butterknife.BindView;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

public class MoviesFragment extends Fragment
        implements MoviesContract.View{

    private static final String TAG= MoviesFragment.class.getSimpleName();

    private Presenter presenter;
    private MoviesAdapter adapter;

    @Inject
    protected Bus eventBus;

    @BindView(R.id.refresher)
    protected SwipeRefreshLayout swipeRefresher;

    @BindView(R.id.recycleView)
    protected RecyclerView recyclerView;

    @BindView(R.id.actionBar)
    protected Toolbar actionBar;

    private Unbinder unbinder;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate()");
        setHasOptionsMenu(true);
        setRetainInstance(true);
        initializeDependencies();

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
        View root=inflater.inflate(R.layout.fragment_movies,container,false);
        unbinder=ButterKnife.bind(this,root);
        presenter.start();
        return root;
    }

    @Override
    public void onViewCreated(View root, @Nullable Bundle savedInstanceState) {
        if(root!=null){
            swipeRefresher.setOnRefreshListener(presenter::requestDataRefresh);
            actionBar.inflateMenu(R.menu.menu_movies);

            LinearLayoutManager layoutManager=new GridLayoutManager(getContext(),getResources()
                    .getInteger(R.integer.moviesSpanCount),GridLayoutManager.VERTICAL,false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    if (swipeRefresher.isRefreshing())
                        return;

                    if(layoutManager.findLastCompletelyVisibleItemPosition()==adapter.getItemCount()-1){
                        presenter.requestMoreData();
                    }
                }
            });

            //initialize sort options
          /*  actionBar.setOnMenuItemClickListener(item -> {
                if(item.getGroupId()==R.id.sortingChoice) {
                    switch (item.getItemId()) {
                        case R.id.byPopularity:
                            presenter.sort(POPULAR);
                            break;
                        case R.id.byLatest:
                            presenter.sort(LATEST);
                            break;
                    }
                    return true;
                }else {
                    switch (item.getItemId()) {
                        case R.id.sortAction:
                            //expand the choices
                            return true;
                    }
                }
                return false;
            }); */

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
        if(adapter!=null){
            adapter.onResume();
        }
    }

    @Inject
    @Override
    public void attachPresenter(@NonNull Presenter presenter) {
        this.presenter=presenter;
        this.presenter.attachView(this);
    }

    @Override
    public void showMovies(@NonNull List<MovieCover> movies) {
        adapter=new MoviesAdapter(getContext(),movies,eventBus);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void appendMovies(@NonNull List<MovieCover> movies) {
        adapter.appendData(movies);
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
        //actionBar.setVisibility(View.INVISIBLE);
        eventBus.post(new ExposeDetailsEvent(event.getTransitionWrapper()));
    }
}
