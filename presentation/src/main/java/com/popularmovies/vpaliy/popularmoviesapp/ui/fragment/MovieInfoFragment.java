package com.popularmovies.vpaliy.popularmoviesapp.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.popularmovies.vpaliy.domain.model.MediaCover;
import com.popularmovies.vpaliy.domain.model.MovieInfo;
import com.popularmovies.vpaliy.domain.model.Trailer;
import com.popularmovies.vpaliy.popularmoviesapp.App;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.di.component.DaggerViewComponent;
import com.popularmovies.vpaliy.popularmoviesapp.di.module.PresenterModule;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MovieInfoContract;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MovieInfoContract.Presenter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.adapter.InfoAdapter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.adapter.MovieTrailersAdapter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.adapter.RelatedMoviesAdapter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.eventBus.RxBus;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.Constants;
import java.util.List;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import javax.inject.Inject;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import butterknife.BindView;

public class  MovieInfoFragment extends Fragment
        implements MovieInfoContract.View{

    private Presenter presenter;
    private Unbinder unbinder;
    private int movieId;

    @BindView(R.id.infoContainer)
    protected ViewGroup infoContainer;

    @Inject
    protected RxBus eventBus;

    @BindView(R.id.infoList)
    protected RecyclerView infoList;

    private InfoAdapter adapter;

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
            adapter=new InfoAdapter(getContext());
            infoList.setAdapter(adapter);
            infoList.setNestedScrollingEnabled(true);
            infoList.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
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
    public void showNoInfoMessage() {
        
    }

    @Override
    public void showGeneralInfo(@NonNull MovieInfo movieInfo) {
        adapter.setMovieInfo(movieInfo);
    }


    @Override
    public void showSimilarMovies(@NonNull List<MediaCover> similarMovies) {
        RelatedMoviesAdapter adapter=new RelatedMoviesAdapter(getContext(),similarMovies,eventBus);
        this.adapter.addWrapper(InfoAdapter.MovieListWrapper.wrap(adapter,getString(R.string.media_similar_content)));
    }

    @Override
    public void showTrailers(@NonNull List<Trailer> trailers) {
        MovieTrailersAdapter adapter=new MovieTrailersAdapter(getContext());
        adapter.setData(trailers);
        this.adapter.addWrapper(InfoAdapter.MovieListWrapper.wrap(adapter,getString(R.string.media_trailers)));

    }

}
