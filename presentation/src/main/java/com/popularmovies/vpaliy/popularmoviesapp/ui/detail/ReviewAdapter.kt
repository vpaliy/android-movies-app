package com.popularmovies.vpaliy.popularmoviesapp.ui.detail

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.popularmovies.vpaliy.domain.entity.Review
import com.popularmovies.vpaliy.popularmoviesapp.R
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BaseAdapter

class ReviewAdapter(context: Context) : BaseAdapter<Review>(context) {
  inner class ReviewViewHolder(itemView: View) : BaseViewHolder(itemView) {
    override fun bind() = with(itemView) {
      TODO("Implement")
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int)
      : BaseViewHolder
      = ReviewViewHolder(inflater.inflate(R.layout.adapter_review, parent, false))
}