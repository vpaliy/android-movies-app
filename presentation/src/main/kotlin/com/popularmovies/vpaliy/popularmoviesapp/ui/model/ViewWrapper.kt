package com.popularmovies.vpaliy.popularmoviesapp.ui.model

import android.support.v7.widget.RecyclerView
import com.popularmovies.vpaliy.domain.entity.MediaType

data class ViewWrapper(val adapter: RecyclerView.Adapter<*>,
                       val title: String,
                       val color: Int,
                       val type: MediaType)