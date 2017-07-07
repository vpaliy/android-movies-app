package com.popularmovies.vpaliy.popularmoviesapp.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.popularmovies.vpaliy.domain.configuration.SortType;
import com.popularmovies.vpaliy.domain.model.MediaCover;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MediaContract;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MediaContract.Presenter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.adapter.AbstractMediaAdapter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.adapter.MediaAdapter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.adapter.MediaTypeAdapter;
import com.popularmovies.vpaliy.popularmoviesapp.bus.RxBus;
import com.popularmovies.vpaliy.popularmoviesapp.ui.adapter.MediaTypeAdapter.MediaTypeWrapper;
import com.popularmovies.vpaliy.popularmoviesapp.bus.events.RequestMoreEvent;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.wrapper.MediaType;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;

import javax.inject.Inject;
import butterknife.BindView;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public abstract class MediaFragment extends Fragment
        implements MediaContract.View{

    protected Presenter presenter;
    private Map<SortType,MediaAdapter> mediaAdapters;
    private AbstractMediaAdapter<MediaTypeWrapper> mediaTypeAdapter;
    private CompositeDisposable disposables;

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
        disposables=new CompositeDisposable();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_media,container,false);
        unbinder=ButterKnife.bind(this,root);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(view!=null){
            mediaAdapters=new LinkedHashMap<>();
            mediaTypeAdapter=new MediaTypeAdapter(getContext(),rxBus);
            mediaList.setAdapter(mediaTypeAdapter);
            getSortTypes().forEach(presenter::start);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        disposables.add(rxBus.asFlowable()
                .subscribe(this::processEvent));
    }

    private void processEvent(Object event){
        if(event!=null) {
            if (event instanceof RequestMoreEvent) {
                RequestMoreEvent requestMoreEvent=RequestMoreEvent.class.cast(event);
                presenter.requestMore(requestMoreEvent.sortType());
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(disposables!=null) disposables.clear();
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
        if(!mediaAdapters.containsKey(sortType)) {
            mediaAdapters.put(sortType,new MediaAdapter(getContext(),rxBus));
        }
        MediaAdapter adapter = mediaAdapters.get(sortType);
        adapter.setData(media);
        MediaTypeWrapper wrapper=MediaTypeWrapper.wrap(getTitle(sortType),sortType,adapter,getMediaType(),getColor(sortType));
        mediaTypeAdapter.addItem(wrapper);
    }


    @Override
    public void appendMedia(@NonNull SortType sortType, @NonNull List<MediaCover> movies) {
        if(!mediaAdapters.containsKey(sortType)){
            throw new IllegalArgumentException("Map does not contain this sort type"+sortType.name());
        }
        mediaAdapters.get(sortType).appendData(movies);
    }


    @Override
    public void setLoadingIndicator(boolean isLoading) {
    }

    abstract void initializeDependencies();
    abstract String getTitle(SortType sortType);
    abstract int getColor(SortType sortType);
    abstract MediaType getMediaType();
    abstract List<SortType> getSortTypes();
}
