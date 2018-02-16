package com.popularmovies.vpaliy.popularmoviesapp.ui.base

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import com.vpaliy.kotlin_extensions.inflater
import com.vpaliy.kotlin_extensions.then

abstract class BaseAdapter<T>(context: Context)
  : RecyclerView.Adapter<BaseAdapter<T>.BaseViewHolder>() {

  var data = mutableListOf<T>()
    set(value) {
      field = value
      notifyDataSetChanged()
    }

  val isEmpty
    get() = data.isEmpty()

  protected val inflater: LayoutInflater = context.inflater()

  abstract inner class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind()
  }

  override fun getItemCount() = data.size

  override fun onBindViewHolder(holder: BaseViewHolder, position: Int)
      = holder.bind()

  open fun add(item: T) = apply { addItem(item) }

  private fun addItem(item: T) {
    data.add(item)
    notifyDataSetChanged()
  }

  fun clear() {
    data.clear()
    notifyDataSetChanged()
  }

  open fun append(appended: List<T>) {
    data.addAll(appended)
    notifyDataSetChanged()
  }

  open fun addFirst(item: T) {
    data.add(0, item)
    notifyItemInserted(0)
  }

  open fun addLast(item: T) {
    data.add(data.isNotEmpty() then (data.size - 1) ?: 0, item)
    notifyItemInserted(data.size - 1)
  }

  operator fun BaseAdapter<T>.get(index: Int) = data[index]

  operator fun BaseAdapter<T>.set(index: Int, item: T) {
    data[index] = item
  }

  inner class Empty(itemView: View) : BaseViewHolder(itemView) {
    override fun bind() {}
  }
}
