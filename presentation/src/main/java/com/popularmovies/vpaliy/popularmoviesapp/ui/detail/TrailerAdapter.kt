package com.popularmovies.vpaliy.popularmoviesapp.ui.detail

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.popularmovies.vpaliy.domain.entity.Trailer
import com.popularmovies.vpaliy.popularmoviesapp.R
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BaseAdapter

class TrailerAdapter(context: Context):BaseAdapter<Trailer>(context){
    inner class TrailerViewHolder(itemView: View):BaseViewHolder(itemView){
        override fun bind() {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int)
            :TrailerViewHolder
            =TrailerViewHolder(inflater.inflate(R.layout.adapter_trailer,parent,false))
}