package com.popularmovies.vpaliy.popularmoviesapp.ui.detail

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.popularmovies.vpaliy.popularmoviesapp.R
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BaseAdapter
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.MediaModel
import kotlinx.android.synthetic.main.adapter_media_item.view.*

class SuggestedAdapter(context:Context):BaseAdapter<MediaModel>(context){
  inner class MediaViewHolder(itemView: View):BaseViewHolder(itemView){
    override fun bind()= with(itemView){
      val item=data[adapterPosition]
      Glide.with(itemView.context)
              .load(item.poster)
              .priority(Priority.IMMEDIATE)
              .diskCacheStrategy(DiskCacheStrategy.RESULT)
              .placeholder(R.drawable.placeholder)
              .animate(R.anim.fade_in)
              .into(poster)
      title.text=item.title
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int)
          :MediaViewHolder
          =MediaViewHolder(inflater.inflate(R.layout.adapter_media_item,parent,false))
}