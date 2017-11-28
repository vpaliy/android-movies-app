package com.popularmovies.vpaliy.popularmoviesapp.ui.detail

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.popularmovies.vpaliy.domain.entity.Trailer
import com.popularmovies.vpaliy.popularmoviesapp.R
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BaseAdapter
import kotlinx.android.synthetic.main.adapter_trailer.view.*

class TrailerAdapter(context: Context):BaseAdapter<Trailer>(context){
  inner class TrailerViewHolder(itemView: View):BaseViewHolder(itemView){
    override fun bind()= with(itemView){
      val item=data[adapterPosition]
      Glide.with(itemView.context)
              .load(item.video)
              .asBitmap()
              .priority(Priority.IMMEDIATE)
              .diskCacheStrategy(DiskCacheStrategy.RESULT)
              .placeholder(R.drawable.placeholder)
              .animate(R.anim.fade_in)
              .into(trailerImage)
      title.text=item.title
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int)
          :TrailerViewHolder
          =TrailerViewHolder(inflater.inflate(R.layout.adapter_trailer,parent,false))
}