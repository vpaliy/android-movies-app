package com.popularmovies.vpaliy.popularmoviesapp.ui.home

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.popularmovies.vpaliy.domain.entity.MediaType
import com.popularmovies.vpaliy.popularmoviesapp.R
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BaseAdapter
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.ViewWrapper
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.OnReachBottomListener
import com.vpaliy.kotlin_extensions.click
import kotlinx.android.synthetic.main.adapter_media_type_item.view.*

class HomeAdapter(context:Context, val click:(MediaType)->Unit): BaseAdapter<ViewWrapper>(context){

  lateinit var request:(MediaType)->Unit

  inner class WrapperViewHolder(root:View):BaseViewHolder(root){
    init {
      val list=itemView.media
      itemView.media.isNestedScrollingEnabled=false
      itemView.more.click {
        click(data[adapterPosition].type)
      }
      list.addOnScrollListener(object : OnReachBottomListener(list.layoutManager) {
        override fun onLoadMore() {
          request(this@HomeAdapter[adapterPosition].type)
        }
      })
    }
    override fun bind()= with(itemView){
      val item=this@HomeAdapter[adapterPosition]
      title.text=item.title
      media.adapter=item.adapter
      more.setTextColor(item.color)
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int)
          :WrapperViewHolder
          =WrapperViewHolder(inflater.inflate(R.layout.adapter_media_type_item,parent,false))
}
