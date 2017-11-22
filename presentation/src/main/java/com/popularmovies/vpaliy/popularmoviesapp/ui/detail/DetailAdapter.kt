package com.popularmovies.vpaliy.popularmoviesapp.ui.detail

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.popularmovies.vpaliy.popularmoviesapp.R
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BaseAdapter
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.ListWrapper
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.OnReachBottomListener
import com.vpaliy.kotlin_extensions.then
import kotlinx.android.synthetic.main.adapter_detail_item.view.*

class DetailAdapter(context: Context):BaseAdapter<ListWrapper>(context){
    lateinit var placeholder:View

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder {
        return when(viewType){
          0-> {
              placeholder=inflater.inflate(R.layout.blank,parent,false)
              Empty(placeholder)
          } else -> MediaViewHolder(inflater.inflate(R.layout.adapter_detail_item,parent,false))
        }
    }

    override fun getItemCount()=super.getItemCount()+1

    override fun getItemViewType(position: Int)= (position!=0) then 1?:0

    inner class MediaViewHolder(itemView:View):BaseViewHolder(itemView){
        init { itemView.list.isNestedScrollingEnabled=false }
        override fun bind()=with(itemView){
            val item=data[adapterPosition-1]
            if(item.bottomReached!=null){
                list.addOnScrollListener(object:OnReachBottomListener(list.layoutManager) {
                    override fun onLoadMore() {
                        item.bottomReached.invoke()
                    }
                })
            }else list.clearOnScrollListeners()

            title.text=item.title
            list.adapter=item.adapter
        }
    }
}