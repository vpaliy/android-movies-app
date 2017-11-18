package com.popularmovies.vpaliy.popularmoviesapp.ui.search

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.popularmovies.vpaliy.domain.entity.Actor
import com.popularmovies.vpaliy.popularmoviesapp.R
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BaseAdapter
import kotlinx.android.synthetic.main.adapter_search_actor.view.*

class ActorAdapter(context:Context):BaseAdapter<Actor>(context){
    inner class ActorViewHolder(itemView:View):BaseViewHolder(itemView){
        override fun bind()=with(itemView){
            val item=data[adapterPosition]
            Glide.with(itemView.context)
                    .load(item.avatarPath)
                    .asBitmap()
                    .priority(Priority.IMMEDIATE)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .placeholder(R.drawable.placeholder)
                    .animate(R.anim.fade_in)
                    .into(avatar)
            name.text=item.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int)
            :ActorViewHolder
            =ActorViewHolder(inflater.inflate(R.layout.adapter_search_actor,parent,false))
}