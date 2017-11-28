package com.popularmovies.vpaliy.popularmoviesapp.ui.more

import android.content.Context
import android.graphics.Bitmap
import android.support.v7.graphics.Palette
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.ImageViewTarget
import com.popularmovies.vpaliy.popularmoviesapp.R
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BaseAdapter
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.MediaModel
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.fetchDominantSwatch
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.setDrawableColor
import kotlinx.android.synthetic.main.adapter_more_item.view.*

class MediaAdapter(context: Context, val click:(MediaModel)->Unit):BaseAdapter<MediaModel>(context){
  inner class MediaViewHolder(itemView:View):BaseViewHolder(itemView){
    override fun bind()= with(itemView){
      val item=this@MediaAdapter[adapterPosition]
      Glide.with(itemView.context)
              .load(item.backdrop)
              .asBitmap()
              .priority(Priority.IMMEDIATE)
              .diskCacheStrategy(DiskCacheStrategy.RESULT)
              .placeholder(R.drawable.placeholder)
              .animate(R.anim.fade_in)
              .into(object:ImageViewTarget<Bitmap>(poster){
                override fun setResource(resource: Bitmap?) {
                  poster.setImageBitmap(resource)
                  Palette.Builder(resource).generate({applyColors(it)})
                }
              })
      title.text=item.title
      release.text=item.release
      ratings.text=item.ratings
      chips.setTags(item.tags)
    }

    private fun applyColors(palette:Palette)= with(itemView){
      val swatch= fetchDominantSwatch(palette)
      swatch?.let {
        details.setBackgroundColor(swatch.rgb)
        chips.setChipsColors(swatch.bodyTextColor,swatch.titleTextColor)
        title.setTextColor(swatch.bodyTextColor)
        release.setTextColor(swatch.bodyTextColor)
        ratings.setTextColor(swatch.bodyTextColor)
        setDrawableColor(release,swatch.bodyTextColor)
        setDrawableColor(ratings,swatch.bodyTextColor)
      }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int)
          :MediaViewHolder
          =MediaViewHolder(inflater.inflate(R.layout.adapter_more_item,parent,false))
}