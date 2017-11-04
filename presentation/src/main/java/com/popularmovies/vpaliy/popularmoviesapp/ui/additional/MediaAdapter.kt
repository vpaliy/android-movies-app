package com.popularmovies.vpaliy.popularmoviesapp.ui.additional

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.popularmovies.vpaliy.popularmoviesapp.R
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BaseAdapter
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.MediaModel
import kotlinx.android.synthetic.main.adapter_more_item.view.*

class MediaAdapter(context: Context, val click:(MediaModel)->Unit):BaseAdapter<MediaModel>(context){

    inner class MediaViewHolder(itemView:View):BaseViewHolder(itemView){
        override fun bind()= with(itemView){
            val item=this@MediaAdapter[adapterPosition]
            title.text=item.title
            release.text=item.release
            ratings.text=item.ratings
            chips.setTags(item.tags)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int)
            :MediaViewHolder
            =MediaViewHolder(inflater.inflate(R.layout.adapter_more_item,parent,false))
}