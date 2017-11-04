package com.popularmovies.vpaliy.popularmoviesapp.ui.home

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.popularmovies.vpaliy.popularmoviesapp.R
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BaseAdapter
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.ViewWrapper
import kotlinx.android.synthetic.main.adapter_media_type_item.view.*

class HomeAdapter(context:Context): BaseAdapter<ViewWrapper>(context){

    inner class WrapperViewHolder(root:View):BaseViewHolder(root){
        override fun bind()= with(itemView){
            val item=this@HomeAdapter[adapterPosition]
            title.text=item.title
            media.adapter=item.adapter
            media.isNestedScrollingEnabled=false
            more.setTextColor(item.color)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int)
            :WrapperViewHolder
            =WrapperViewHolder(inflater.inflate(R.layout.adapter_media_type_item,parent,false))
}
