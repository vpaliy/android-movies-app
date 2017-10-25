package com.popularmovies.vpaliy.popularmoviesapp.ui.home

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BaseAdapter
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.ViewWrapper

class HomeAdapter(context:Context): BaseAdapter<ViewWrapper>(context){

    inner class WrapperViewHolder(root:View):BaseViewHolder(root){
        override fun bind()= with(itemView){
            val item=this@HomeAdapter[adapterPosition]
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int)
            :WrapperViewHolder
            =WrapperViewHolder(inflater.inflate(1,parent,false))
}
