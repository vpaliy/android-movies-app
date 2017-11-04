package com.popularmovies.vpaliy.popularmoviesapp.ui.utils

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager

abstract class OnReachBottomListener
constructor(private val layoutManager:RecyclerView.LayoutManager,
            private val dataLoading: SwipeRefreshLayout?=null):RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        // bail out if scrolling upward then already loading data
        val firstVisibleItem = fetchFirstVisibleItemPosition()
        if (dy < 0 || (dataLoading != null && dataLoading.isRefreshing) || firstVisibleItem == -1) return

        val visibleItemCount = recyclerView!!.childCount
        val totalItemCount = layoutManager.itemCount

        if ((totalItemCount - visibleItemCount) <= (firstVisibleItem + VISIBLE_THRESHOLD)) {
            onLoadMore()
        }
    }

    private fun fetchFirstVisibleItemPosition(): Int {
        when(layoutManager){
            is LinearLayoutManager-> return layoutManager.findFirstVisibleItemPosition()
            is StaggeredGridLayoutManager->{
                val result = layoutManager.findFirstVisibleItemPositions(null)
                if (result.isNotEmpty()) {
                    return result[0]
                }
            }
        }
        return -1
    }

    abstract fun onLoadMore()

    companion object {
        private val VISIBLE_THRESHOLD = 5
    }
}