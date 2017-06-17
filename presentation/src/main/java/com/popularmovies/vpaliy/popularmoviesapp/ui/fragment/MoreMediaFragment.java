package com.popularmovies.vpaliy.popularmoviesapp.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.popularmovies.vpaliy.domain.configuration.SortType;
import com.popularmovies.vpaliy.domain.model.MediaCover;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MediaContract;
import com.popularmovies.vpaliy.popularmoviesapp.mvp.contract.MediaContract.Presenter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.adapter.AbstractMediaAdapter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.adapter.MoreMediaAdapter;
import com.popularmovies.vpaliy.popularmoviesapp.bus.RxBus;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.Constants;
import java.util.List;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import butterknife.BindView;
import javax.inject.Inject;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public abstract class MoreMediaFragment  extends Fragment
        implements MediaContract.View {

    private static final String TAG=MoreMediaFragment.class.getSimpleName();

    protected Presenter presenter;

    @Inject
    protected RxBus rxBus;

    @BindView(R.id.media_list)
    protected RecyclerView mediaList;

    @BindView(R.id.refresher)
    protected SwipeRefreshLayout refreshLayout;

    private Unbinder unbinder;
    private SortType sortType;

    private AbstractMediaAdapter<MediaCover> adapter;

    public static MoreMediaFragment create(SortType sortType, boolean isTvShow){
        Bundle args=new Bundle();
        MoreMediaFragment fragment=isTvShow?new MoreTvFragment():new MoreMoviesFragment();
        args.putString(Constants.EXTRA_DATA,sortType.name());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        initializeDependencies();
        if(savedInstanceState==null){
            savedInstanceState=getArguments();
        }
        this.sortType=SortType.valueOf(savedInstanceState.getString(Constants.EXTRA_DATA));

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(view!=null){
            adapter=new MoreMediaAdapter(getContext(),rxBus);
            refreshLayout.setOnRefreshListener(()->presenter.requestRefresh(sortType));
            mediaList.setAdapter(adapter);
            mediaList.addItemDecoration(new DividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL));
            mediaList.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
            mediaList.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    if (refreshLayout.isRefreshing())
                        return;
                    LinearLayoutManager layoutManager=LinearLayoutManager.class.cast(recyclerView.getLayoutManager());
                    int totalItemCount = layoutManager.getItemCount();
                    int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();

                    boolean endHasBeenReached = lastVisibleItemPosition + 5 >= totalItemCount;
                    if (totalItemCount > 0 && endHasBeenReached) {
                        presenter.requestMore(sortType);
                    }
                }
            });
            presenter.start(sortType);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_more_media,container,false);
        unbinder= ButterKnife.bind(this,root);
        return root;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(unbinder!=null) unbinder.unbind();
    }

    @Override
    public void appendMedia(@NonNull SortType sortType,
                            @NonNull List<MediaCover> covers) {
        adapter.appendData(covers);
    }

    @Override
    public void showMedia(@NonNull SortType sortType,
                          @NonNull List<MediaCover> covers) {
        adapter.setData(covers);
    }

    @Override
    public void setLoadingIndicator(boolean isLoading) {
        refreshLayout.setRefreshing(isLoading);
    }

    @Override
    public void showErrorMessage() {
    }

    @Override
    public void showEmptyMessage() {
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Constants.EXTRA_DATA,sortType.name());
    }

    abstract void initializeDependencies();
    abstract String getTitle(@NonNull SortType sortType);

}
