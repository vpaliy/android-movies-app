package com.popularmovies.vpaliy.popularmoviesapp.ui.base

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import com.popularmovies.vpaliy.popularmoviesapp.ui.inflater
import com.popularmovies.vpaliy.popularmoviesapp.ui.then

abstract class BaseAdapter<T>(context:Context)
    :RecyclerView.Adapter<BaseAdapter<T>.BaseViewHolder>(){

    var data= mutableListOf<T>()
        set(value) {
            field=value
            notifyDataSetChanged()
        }

    protected val inflater=context.inflater()

    abstract inner class BaseViewHolder(itemView:View) :RecyclerView.ViewHolder(itemView){
        abstract fun bind()
    }

    override fun getItemCount()=data.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int)
            :Unit
            =holder.bind()

    fun add(item:T)=apply{addItem(item)}

    private fun addItem(item:T){
        data.add(item)
        notifyDataSetChanged()   //TODO fix that
    }

    fun clear(){
        data.clear()
        notifyDataSetChanged()
    }

    fun append(appended:List<T>){
        data.addAll(appended)
        notifyDataSetChanged()
    }

    fun addFirst(item:T):Unit =data.add(0,item)

    fun addLast(item:T):Unit =data.add(data.isNotEmpty() then (data.size-1)?:0,item)

    operator fun BaseAdapter<T>.get(index:Int)=data[index]

    operator fun BaseAdapter<T>.set(index:Int, item: T){
        data[index]=item
    }

    inner class Empty(itemView: View):BaseViewHolder(itemView){
        override fun bind() {}
    }
}
