package com.popularmovies.vpaliy.popularmoviesapp.ui.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class OnReachBottomListener extends RecyclerView.OnScrollListener {

    private static final int VISIBLE_THRESHOLD = 5;

    private final LinearLayoutManager layoutManager;
    private final SwipeRefreshLayout dataLoading;

    public OnReachBottomListener(@NonNull RecyclerView recyclerView,
                                  @Nullable SwipeRefreshLayout dataLoading) {
        this.layoutManager = LinearLayoutManager.class.cast(recyclerView.getLayoutManager());
        this.dataLoading = dataLoading;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        // bail out if scrolling upward or already loading data
        if (dy < 0 || (dataLoading!=null && dataLoading.isRefreshing())) return;

        final int visibleItemCount = recyclerView.getChildCount();
        final int totalItemCount = layoutManager.getItemCount();
        final int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();

        if ((totalItemCount - visibleItemCount) <= (firstVisibleItem + VISIBLE_THRESHOLD)) {
            onLoadMore();
        }
    }

    public abstract void onLoadMore();
}
