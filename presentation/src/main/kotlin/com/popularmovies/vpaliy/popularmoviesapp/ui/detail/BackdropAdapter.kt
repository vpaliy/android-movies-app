package com.popularmovies.vpaliy.popularmoviesapp.ui.detail

import android.content.Context
import android.graphics.Bitmap
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.popularmovies.vpaliy.popularmoviesapp.R
import kotlinx.android.synthetic.main.adapter_backdrop.view.*
import com.bumptech.glide.request.target.ImageViewTarget
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.vpaliy.kotlin_extensions.then

class BackdropAdapter(context: Context, val callback: (ImageView, Bitmap) -> Unit) : PagerAdapter() {
  private var isLoaded = false
  private val inflater by lazy {
    LayoutInflater.from(context)
  }
  var data = mutableListOf<String>()
    set(value) {
      field = value
      notifyDataSetChanged()
    }

  override fun isViewFromObject(view: View?, `object`: Any?) = view == `object`

  override fun getCount() = (data.size >= 5) then 5 ?: data.size

  override fun instantiateItem(container: ViewGroup?, position: Int): Any {
    val view = inflater.inflate(R.layout.adapter_backdrop, container, false)
    with(view) {
      progressBar.visibility = View.VISIBLE
      Glide.with(inflater.context)
          .load(data[position])
          .asBitmap()
          .priority(Priority.IMMEDIATE)
          .diskCacheStrategy(DiskCacheStrategy.RESULT)
          .into(object : ImageViewTarget<Bitmap>(backdropImage) {
            override fun setResource(resource: Bitmap) {
              backdropImage.setImageBitmap(resource)
              progressBar.visibility = View.GONE
              if (position == 0 && !isLoaded) {
                isLoaded = true
                callback(backdropImage, resource)
              }
            }
          })
      container?.addView(view)
      return view
    }
  }

  override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
    container?.removeView(`object` as View)
  }
}