package com.popularmovies.vpaliy.popularmoviesapp.ui.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.transition.Transition;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.popularmovies.vpaliy.domain.configuration.ISortConfiguration.SortType;
import com.popularmovies.vpaliy.domain.model.MovieCover;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.App;
import com.popularmovies.vpaliy.popularmoviesapp.di.component.DaggerViewComponent;
import com.popularmovies.vpaliy.popularmoviesapp.di.module.PresenterModule;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MoviesContract;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MoviesContract.Presenter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.activity.MoviesActivity;
import com.popularmovies.vpaliy.popularmoviesapp.ui.adapter.MovieAdapter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.adapter.MoviesAdapter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.configuration.PresentationConfiguration;
import com.popularmovies.vpaliy.popularmoviesapp.ui.eventBus.RxBus;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.Permission;
import com.popularmovies.vpaliy.popularmoviesapp.ui.view.MarginDecoration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import android.widget.ImageView;
import android.support.annotation.StringRes;
import javax.inject.Inject;
import butterknife.BindView;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;


public class MoviesFragment extends Fragment
        implements MoviesContract.View, MoviesActivity.IMoviesFragment{


    private Presenter presenter;

    private Map<SortType,MovieAdapter> adapterMap;
    private Map<SortType,CardView> cardViewMap;

    @Inject
    protected RxBus eventBus;

    @BindView(R.id.emptyBox)
    protected ImageView emptyBox;

    @Inject
    protected PresentationConfiguration presentationConfiguration;

    private Unbinder unbinder;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        presenter.attachView(this);
        presenter.start();
        adapterMap=new HashMap<>();
        return root;
    }


    @Override
    public void onViewCreated(View root, @Nullable Bundle savedInstanceState) {
        if(root!=null){
            initMaps(root);
            for(SortType sortType:SortType.values()){
                CardView card=cardViewMap.get(sortType);
                RecyclerView movieList=ButterKnife.findById(card,R.id.movies);
                TextView moviesTitle=ButterKnife.findById(card,R.id.movies_type);
                moviesTitle.setText(getMovieString(sortType));
                MovieAdapter adapter=adapterMap.get(sortType);
                movieList.setAdapter(adapter);
                movieList.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
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
    }

    private int getMovieString(SortType sortType){
        return R.string.sortByTopRated;
    }

    private void initMaps(@NonNull View root){
        if(cardViewMap==null) cardViewMap=new HashMap<>();
        if(adapterMap==null) adapterMap=new HashMap<>();
        CardView card=ButterKnife.findById(root,R.id.popular_movies);
        cardViewMap.put(SortType.POPULAR,card);
        card=ButterKnife.findById(root,R.id.latest_movies);
        cardViewMap.put(SortType.LATEST,card);
        card=ButterKnife.findById(root,R.id.now_playing_movies);
        cardViewMap.put(SortType.NOW_PLAYING,card);
        card=ButterKnife.findById(root,R.id.upcoming_movies);
        cardViewMap.put(SortType.UPCOMING,card);
        card=ButterKnife.findById(root,R.id.top_rated_movies);
        cardViewMap.put(SortType.TOP_RATED,card);

        //initialize adapters
        for(SortType sortType:SortType.values()){
            MovieAdapter adapter=new MovieAdapter(getContext(),eventBus,sortType);
            adapterMap.put(sortType,adapter);
        }

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
     /*   if(adapter!=null){
            adapter.onResume();
        }   */
    }

    @Inject
    @Override
    public void attachPresenter(@NonNull Presenter presenter) {
        this.presenter=presenter;
    }

    @Override
    public void showMovies(@NonNull SortType sortType, @NonNull List<MovieCover> movies) {
        if(!adapterMap.containsKey(sortType)){
            //
        }
        adapterMap.get(sortType).setData(movies);
        emptyBox.setVisibility(View.GONE);
    }

    @Override
    public void appendMovies(@NonNull SortType sortType, @NonNull List<MovieCover> movies) {
        if(!adapterMap.containsKey(sortType)){
            //
        }
        adapterMap.get(sortType).appendData(movies);
    }

    @Override
    public void onConfigChanged() {
        presenter.requestDataRefresh(SortType.POPULAR);
    }

    @Override
    public void showEmptyMessage() {
      //  adapter.clear();
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
       // swipeRefresher.setRefreshing(isLoading);
    }

}
