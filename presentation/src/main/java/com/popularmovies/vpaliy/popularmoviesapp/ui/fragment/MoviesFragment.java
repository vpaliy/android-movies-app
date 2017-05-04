package com.popularmovies.vpaliy.popularmoviesapp.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.popularmovies.vpaliy.domain.configuration.ISortConfiguration;
import com.popularmovies.vpaliy.domain.configuration.ISortConfiguration.SortType;
import com.popularmovies.vpaliy.domain.model.MovieCover;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.App;
import com.popularmovies.vpaliy.popularmoviesapp.di.component.DaggerViewComponent;
import com.popularmovies.vpaliy.popularmoviesapp.di.module.PresenterModule;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MoviesContract;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MoviesContract.Presenter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.activity.MoviesActivity;
import com.popularmovies.vpaliy.popularmoviesapp.ui.adapter.MoviesAdapter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.configuration.PresentationConfiguration;
import com.popularmovies.vpaliy.popularmoviesapp.ui.eventBus.RxBus;
import com.popularmovies.vpaliy.popularmoviesapp.ui.view.AutofitRecyclerView;
import com.popularmovies.vpaliy.popularmoviesapp.ui.view.MarginDecoration;
import java.util.List;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import android.widget.ImageView;

import android.support.annotation.StringRes;
import javax.inject.Inject;
import butterknife.BindView;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.popularmovies.vpaliy.popularmoviesapp.ui.configuration.PresentationConfiguration.Presentation.CARD;
import static com.popularmovies.vpaliy.popularmoviesapp.ui.configuration.PresentationConfiguration.Presentation.GRID;


public class MoviesFragment extends Fragment
        implements MoviesContract.View, MoviesActivity.IMoviesFragment{

    private static final String TAG=MoviesFragment.class.getSimpleName();

    private Presenter presenter;
    private MoviesAdapter adapter;

    @Inject
    protected RxBus eventBus;

    @Inject
    protected ISortConfiguration iSortConfiguration;

    @BindView(R.id.refresher)
    protected SwipeRefreshLayout swipeRefresher;

    @BindView(R.id.recycleView)
    protected AutofitRecyclerView recyclerView;

    @BindView(R.id.emptyBox)
    protected ImageView emptyBox;

    @Inject
    protected PresentationConfiguration presentationConfiguration;

    private Unbinder unbinder;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_movies,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getGroupId()==R.id.changeViewChoice) {
            switch (item.getItemId()) {
                case R.id.asCard:
                    if(presentationConfiguration.getPresentation()!= CARD){
                        presentationConfiguration.savePresentation(CARD);
                        adjustMoviesAdapter();
                        adjustColumnWidth();
                    }
                    break;
                case R.id.asGrid:
                    if(presentationConfiguration.getPresentation()!= GRID){
                        presentationConfiguration.savePresentation(GRID);
                        adjustMoviesAdapter();
                        adjustColumnWidth();
                    }
                    break;
            }
            return true;
        }else {
            switch (item.getItemId()) {
                case R.id.changeView:
                    //expand the choices
                    return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_movies,container,false);
        unbinder=ButterKnife.bind(this,root);
        presenter.attachView(this);
        presenter.start();
        return root;
    }


    @Override
    public void onViewCreated(View root, @Nullable Bundle savedInstanceState) {
        if(root!=null){
            swipeRefresher.setOnRefreshListener(presenter::requestDataRefresh);
            adapter=new MoviesAdapter(getContext(),eventBus,presentationConfiguration);
            adjustColumnWidth();
            recyclerView.setAdapter(adapter);
            recyclerView.addItemDecoration(new MarginDecoration(getContext()));
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    if (swipeRefresher.isRefreshing())
                        return;

                    LinearLayoutManager layoutManager=LinearLayoutManager.class.cast(recyclerView.getLayoutManager());
                    int totalItemCount = layoutManager.getItemCount();
                    int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();

                    boolean endHasBeenReached = lastVisibleItemPosition + 5 >= totalItemCount;
                    if (totalItemCount > 0 && endHasBeenReached) {
                        presenter.requestMoreData();
                    }
                }
            });

        }
    }


    private void adjustColumnWidth(){
        switch (presentationConfiguration.getPresentation()){
            case CARD:
                recyclerView.setColumnWidth((int)getResources().getDimension(R.dimen.item_card_width));
                break;
            case GRID:
                recyclerView.setColumnWidth((int)getResources().getDimension(R.dimen.item_width));
                break;
        }
    }

    private void adjustMoviesAdapter(){
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void sort(SortType sortType){
        presenter.sort(sortType);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.stop();
        unbinder.unbind();
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
    }

    @Override
    public void showMovies(@NonNull List<MovieCover> movies) {
        emptyBox.setVisibility(View.GONE);
        adapter.setData(movies);
    }

    @Override
    public void appendMovies(@NonNull List<MovieCover> movies) {
        adapter.appendData(movies);
    }

    @Override
    public void onConfigChanged() {
        presenter.requestDataRefresh();
    }

    @Override
    public void showEmptyMessage() {
        adapter.clear();
        emptyBox.setVisibility(View.VISIBLE);
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

    private void showMessage(@StringRes int resourceId){
        if(getView()!=null){
            Snackbar.make(getView(),resourceId,Snackbar.LENGTH_LONG)
                    .show();
        }
    }

    @Override
    public void setLoadingIndicator(boolean isLoading) {
        swipeRefresher.setRefreshing(isLoading);
    }

}
