package com.popularmovies.vpaliy.popularmoviesapp.ui.search

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.popularmovies.vpaliy.domain.entity.Actor
import com.popularmovies.vpaliy.popularmoviesapp.R
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BaseAdapter

class ActorResultAdapter(context:Context):BaseAdapter<Actor>(context){

    inner class ActorViewHolder(itemView:View):BaseViewHolder(itemView){
        override fun bind()=with(itemView){

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder {
        return ActorViewHolder(inflater.inflate(R.layout.adapter_search_actor,parent,false))
    }
}