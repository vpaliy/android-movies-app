package com.popularmovies.vpaliy.popularmoviesapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.popularmovies.vpaliy.domain.configuration.ISortConfiguration.SortType;
import com.popularmovies.vpaliy.domain.model.MediaCover;
import com.popularmovies.vpaliy.popularmoviesapp.App;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.di.component.DaggerViewComponent;
import com.popularmovies.vpaliy.popularmoviesapp.di.module.PresenterModule;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MediaContract;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MediaContract.Presenter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.adapter.MediaAdapter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.adapter.MediaTypeAdapter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.eventBus.RxBus;
import com.popularmovies.vpaliy.popularmoviesapp.ui.adapter.MediaTypeAdapter.MediaTypeWrapper;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class MediaFragment extends Fragment
        implements MediaContract.View{

    protected Presenter presenter;
    private Map<SortType,MediaAdapter> mediaAdapters;
    private MediaTypeAdapter mediaTypeAdapter;

    @Inject
    protected RxBus rxBus;

    @BindView(R.id.media_recycler_view)
    protected RecyclerView mediaList;

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
        View root=inflater.inflate(R.layout.fragment_media,container,false);
        unbinder=ButterKnife.bind(this,root);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(view!=null){
            mediaList.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
            mediaList.setAdapter(mediaTypeAdapter);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(unbinder!=null) unbinder.unbind();
    }

    @Override
    public void showEmptyMessage() {

    }

    @Override
    public void showErrorMessage() {

    }

    @Override
    public void showMedia(@NonNull SortType sortType, @NonNull List<MediaCover> media) {
        if(!mediaAdapters.containsKey(sortType)) mediaAdapters.put(sortType,new MediaAdapter(getContext(),rxBus));
        MediaAdapter adapter=mediaAdapters.get(sortType);
        adapter.setData(media);
        mediaTypeAdapter.addWrapper(MediaTypeWrapper.wrap(getTitle(sortType),adapter));
    }


    @Override
    public void appendMedia(@NonNull SortType sortType, @NonNull List<MediaCover> movies) {

    }


    @Override
    public void setLoadingIndicator(boolean isLoading) {

    }

    abstract String getTitle(SortType sortType);
    abstract List<SortType> getSortTypes();
}
