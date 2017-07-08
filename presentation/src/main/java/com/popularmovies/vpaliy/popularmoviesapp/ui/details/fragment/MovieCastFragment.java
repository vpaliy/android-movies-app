package com.popularmovies.vpaliy.popularmoviesapp.ui.details.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.popularmovies.vpaliy.domain.model.ActorCover;
import com.popularmovies.vpaliy.popularmoviesapp.App;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.di.component.DaggerViewComponent;
import com.popularmovies.vpaliy.popularmoviesapp.di.module.PresenterModule;
import com.popularmovies.vpaliy.popularmoviesapp.ui.details.mvp.contract.MovieCastContract;
import com.popularmovies.vpaliy.popularmoviesapp.ui.details.adapter.MovieCastAdapter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.Constants;
import com.popularmovies.vpaliy.popularmoviesapp.ui.details.mvp.contract.MovieCastContract.Presenter;
import java.util.List;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import javax.inject.Inject;
import butterknife.BindView;

public class MovieCastFragment extends Fragment
        implements MovieCastContract.View{


    private Unbinder unbinder;
    private MovieCastAdapter adapter;
    private Presenter presenter;

    private int movieId;

    @BindView(R.id.castList)
    protected RecyclerView castList;

    @BindView(R.id.emptyMessage)
    protected TextView emptyMessage;

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

    public static MovieCastFragment newInstance(int movieID){
        Bundle args=new Bundle();
        args.putInt(Constants.EXTRA_ID,movieID);
        MovieCastFragment fragment=new MovieCastFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_movie_cast,container,false);
        unbinder=ButterKnife.bind(this,root);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if(view!=null){
            adapter=new MovieCastAdapter(getContext());
            castList.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
            castList.setHasFixedSize(true);
            castList.addItemDecoration(new DividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL));
            castList.setAdapter(adapter);
        }
    }

    @Override
    public void showNoCastMessage() {
        emptyMessage.setVisibility(View.VISIBLE);
        castList.setVisibility(View.GONE);
    }

    @Override
    @Inject
    public void attachPresenter(@NonNull Presenter presenter) {
        this.presenter=presenter;
        this.presenter.attachView(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.start(movieId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.stop();
        unbinder.unbind();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Constants.EXTRA_ID,movieId);
    }

    @Override
    public void showCast(@NonNull List<ActorCover> cast) {
        adapter.setData(cast);
    }

}
