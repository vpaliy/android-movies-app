package com.popularmovies.vpaliy.popularmoviesapp.ui.additional

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.popularmovies.vpaliy.popularmoviesapp.R
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BaseAdapter
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.MediaModel

class MediaAdapter(context: Context, val click:(MediaModel)->Unit):BaseAdapter<MediaModel>(context){

    inner class MediaViewHolder(itemView:View):BaseViewHolder(itemView){
        override fun bind() {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int)
            :MediaViewHolder
            =MediaViewHolder(inflater.inflate(R.layout.adapter_media_item,parent,false))
}