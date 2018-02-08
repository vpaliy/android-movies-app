package com.popularmovies.vpaliy.popularmoviesapp.ui.detail

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.popularmovies.vpaliy.domain.entity.Role
import com.popularmovies.vpaliy.popularmoviesapp.R
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BaseAdapter
import kotlinx.android.synthetic.main.adapter_role.view.*

class CastAdapter(context: Context) : BaseAdapter<Role>(context) {
  inner class RoleViewHolder(itemView: View) : BaseViewHolder(itemView) {
    override fun bind() = with(itemView) {
      val item = data[adapterPosition]
      Glide.with(itemView.context)
          .load(item.picture)
          .asBitmap()
          .priority(Priority.IMMEDIATE)
          .diskCacheStrategy(DiskCacheStrategy.RESULT)
          .placeholder(R.drawable.placeholder)
          .animate(R.anim.fade_in)
          .into(photo)
      name.text = item.actor
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int)
      : RoleViewHolder
      = RoleViewHolder(inflater.inflate(R.layout.adapter_role, parent, false))
}