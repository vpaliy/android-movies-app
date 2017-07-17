package com.popularmovies.vpaliy.popularmoviesapp.ui.actor;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.popularmovies.vpaliy.data.entity.ActorDetailEntity;
import com.popularmovies.vpaliy.popularmoviesapp.App;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.di.component.DaggerViewComponent;
import com.popularmovies.vpaliy.popularmoviesapp.di.module.PresenterModule;
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BaseFragment;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.Constants;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

import static com.popularmovies.vpaliy.popularmoviesapp.ui.actor.ActorContract.Presenter;

public class ActorFragment extends BaseFragment
        implements ActorContract.View{

    private Presenter presenter;
    private int actorId;

    @BindView(R.id.actor_backdrop)
    protected ImageView actorBackdrop;

    public static ActorFragment newInstance(Bundle args){
        ActorFragment fragment=new ActorFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_actor, container, false);
        bind(root);
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState==null){
            savedInstanceState=getArguments();
        }
        actorId=savedInstanceState.getInt(Constants.EXTRA_ID);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(view!=null){
            presenter.start(actorId);
        }
    }

    @Override
    public void initializeDependencies() {
        DaggerViewComponent.builder()
                .applicationComponent(App.appInstance().appComponent())
                .presenterModule(new PresenterModule())
                .build().inject(this);
    }

    @Override
    public void showBackground(@NonNull String backdropPath) {
        Glide.with(getContext())
                .load(backdropPath)
                .asBitmap()
                .priority(Priority.IMMEDIATE)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(actorBackdrop);
    }

    @Inject
    @Override
    public void attachPresenter(@NonNull Presenter presenter) {
        this.presenter=presenter;
        this.presenter.attachView(this);
    }

    @Override
    public void showBioDetails(@NonNull ActorDetailEntity entity) {

    }

    @Override
    public void showImages(@NonNull List<String> images) {

    }

    @Override
    public void showProfilePhoto(@NonNull String profilePhoto) {

    }

}
