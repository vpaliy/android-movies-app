package com.popularmovies.vpaliy.popularmoviesapp.ui.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.transition.Transition;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.popularmovies.vpaliy.domain.configuration.ISortConfiguration.SortType;
import com.popularmovies.vpaliy.domain.model.MediaCover;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.App;
import com.popularmovies.vpaliy.popularmoviesapp.di.component.DaggerViewComponent;
import com.popularmovies.vpaliy.popularmoviesapp.di.module.PresenterModule;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MoviesContract;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MoviesContract.Presenter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.adapter.MoviesAdapter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.configuration.PresentationConfiguration;
import com.popularmovies.vpaliy.popularmoviesapp.ui.eventBus.RxBus;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.Constants;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.Permission;
import com.popularmovies.vpaliy.popularmoviesapp.ui.view.AutofitRecyclerView;
import com.popularmovies.vpaliy.popularmoviesapp.ui.view.MarginDecoration;
import java.util.List;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import android.support.annotation.StringRes;
import javax.inject.Inject;
import butterknife.BindView;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.popularmovies.vpaliy.popularmoviesapp.ui.configuration.PresentationConfiguration.Presentation.CARD;
import static com.popularmovies.vpaliy.popularmoviesapp.ui.configuration.PresentationConfiguration.Presentation.GRID;

public class MoviesFragment extends Fragment
        implements MoviesContract.View{


    private static final String TAG=MoviesFragment.class.getSimpleName();

    private Presenter presenter;
    private MoviesAdapter adapter;

    @Inject
    protected RxBus eventBus;

    @BindView(R.id.refresher)
    protected SwipeRefreshLayout swipeRefresher;

    @BindView(R.id.recycleView)
    protected AutofitRecyclerView recyclerView;

    @BindView(R.id.emptyBox)
    protected ImageView emptyBox;

    @Inject
    protected PresentationConfiguration presentationConfiguration;

    private Unbinder unbinder;
    private SortType sortType;

    public static MoviesFragment newInstance(SortType sortType){
        MoviesFragment fragment=new MoviesFragment();
        Bundle args=new Bundle();
        args.putString(Constants.EXTRA_DATA,sortType.name());
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
        initializeDependencies();
        if(savedInstanceState==null){
            savedInstanceState=getArguments();
        }
        sortType=SortType.valueOf(savedInstanceState
                .getString(Constants.EXTRA_DATA,SortType.POPULAR.name()));

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
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d(TAG,"onCreateView()");
        View root=inflater.inflate(R.layout.fragment_movies,container,false);
        unbinder=ButterKnife.bind(this,root);
        presenter.attachView(this);
        presenter.start(sortType);
        return root;
    }

    @Override
    public void onViewCreated(View root, @Nullable Bundle savedInstanceState) {
        if(root!=null){
            Log.d(TAG,"onViewCreated");
            swipeRefresher.setOnRefreshListener(()->presenter.requestDataRefresh(sortType));
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
                        presenter.requestMoreData(sortType);
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
        if (Permission.checkForVersion(Build.VERSION_CODES.LOLLIPOP)) {
            Transition transition = new Explode();
            transition.setInterpolator(new AccelerateDecelerateInterpolator());
            TransitionManager.beginDelayedTransition(recyclerView, transition);
        }
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy()");
        presenter.stop();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG,"onResume()");
        if(adapter!=null){
            adapter.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG,"onPause()");
        if(swipeRefresher!=null){
            if(swipeRefresher.isRefreshing()) swipeRefresher.setRefreshing(false);
        }
    }

    @Inject
    @Override
    public void attachPresenter(@NonNull Presenter presenter) {
        this.presenter=presenter;
    }

    @Override
    public void showMovies(@NonNull SortType sortType, @NonNull List<MediaCover> movies) {
        Log.d(TAG,"showMovies");
        emptyBox.setVisibility(View.GONE);
        adapter.setData(movies);
    }

    @Override
    public void appendMovies(@NonNull SortType sortType, @NonNull List<MediaCover> movies) {
        Log.d(TAG,"appendMovies");
        adapter.appendData(movies);
    }


    @Override
    public void showEmptyMessage() {
        adapter.clear();
        emptyBox.setVisibility(View.VISIBLE);
        showMessage(R.string.noDataMessage);
    }

    @Override
    public void showErrorMessage() {
        showMessage(R.string.dataError);
    }

    private void showMessage(@StringRes int resourceId){
        if(getView()!=null){
            Snackbar.make(getView(),resourceId,Snackbar.LENGTH_LONG)
                    .setAction(R.string.refreshAction,v->presenter.requestDataRefresh(SortType.POPULAR))
                    .show();
        }
    }

    @Override
    public void setLoadingIndicator(boolean isLoading) {
        Log.d(TAG,"setLoadingIndicator");
        swipeRefresher.setRefreshing(isLoading);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Constants.EXTRA_DATA,sortType.name());
    }
}