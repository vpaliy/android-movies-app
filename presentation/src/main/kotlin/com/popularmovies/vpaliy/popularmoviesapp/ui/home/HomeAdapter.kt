package com.popularmovies.vpaliy.popularmoviesapp.ui.home

import android.content.Context
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import com.popularmovies.vpaliy.domain.entity.MediaType
import com.popularmovies.vpaliy.popularmoviesapp.R
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BaseAdapter
import com.popularmovies.vpaliy.popularmoviesapp.ui.isNullOrEmpty
import com.popularmovies.vpaliy.popularmoviesapp.ui.model.ViewWrapper
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.OnReachBottomListener
import com.vpaliy.kotlin_extensions.click
import com.vpaliy.kotlin_extensions.then
import kotlinx.android.synthetic.main.adapter_media_type_item.view.*

class HomeAdapter(context: Context, val click: (MediaType) -> Unit)
  : BaseAdapter<ViewWrapper>(context) {

  lateinit var request: (MediaType) -> Unit

  private val mediaTypeMap by lazy(LazyThreadSafetyMode.NONE) {
    mutableMapOf<MediaType, Int>()
  }

  private var loading = BooleanArray(itemCount)

  inner class WrapperViewHolder(root: View) : BaseViewHolder(root) {
    init {
      val list = itemView.media
      list.isNestedScrollingEnabled = false
      itemView.more.click {
        click(data[adapterPosition].type)
      }
      list.addOnScrollListener(object : OnReachBottomListener(list.layoutManager) {
        override fun onLoadMore() {
          request(this@HomeAdapter[adapterPosition].type)
        }
      })
    }

    override fun bind() = with(itemView) {
      val item = this@HomeAdapter[adapterPosition]
      progress.visibility = loading[adapterPosition] then View.VISIBLE ?: View.GONE
      title.text = item.title
      if (!loading[adapterPosition])
        media.adapter = item.adapter
      more.setTextColor(item.color)
    }
  }

  override fun add(item: ViewWrapper) = apply {
    data.add(item)
    data.sortBy { it.title }
    mediaTypeMap[item.type] = data.indexOf(item)
    loading = loading.copyOf(itemCount)
    notifyDataSetChanged()
  }

  fun changeLoadingAt(type: MediaType, isLoading: Boolean) {
    mediaTypeMap[type]?.let {
      if (it < loading.size) {
        loading[it] = isLoading
        notifyItemRangeChanged(it, itemCount, ProgressVisibility(it, isLoading))
      }
    }
  }

  override fun onBindViewHolder(holder: BaseViewHolder, position: Int, payloads: MutableList<Any>?) {
    payloads?.forEach {
      if (it is ProgressVisibility) {
        if (it.position == position) {
          val progressBar = holder.itemView.progress
          progressBar.visibility = it.isLoading then View.VISIBLE ?: View.GONE
          return
        }
      }
    }
    super.onBindViewHolder(holder, position, payloads)
  }

  private class ProgressVisibility(val position: Int, val isLoading: Boolean)

  override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int)
      : WrapperViewHolder
      = WrapperViewHolder(inflater.inflate(R.layout.adapter_media_type_item, parent, false))
}
