package com.popularmovies.vpaliy.popularmoviesapp.ui.view

import android.content.Context
import android.support.design.widget.FloatingActionButton
import android.util.AttributeSet
import android.view.View

class FABToggle(context: Context, attrs: AttributeSet) : FloatingActionButton(context, attrs) {

  private var isCheckedBy = false
    set(value) {
      if (field != value) {
        field = value
        refreshDrawableState()
      }
    }

  private var minOffset: Float = 0f
  var staticOffset: Int = 0

  fun setOffset(offset: Float) {
    if (offset != translationY) {
      translationY = Math.max(minOffset, offset)
    }
  }

  fun setMinOffset(minOffset: Float) {
    this.minOffset = minOffset
  }

  override fun onCreateDrawableState(extraSpace: Int): IntArray {
    val drawableState = super.onCreateDrawableState(extraSpace + 1)
    if (isCheckedBy) {
      View.mergeDrawableStates(drawableState, CHECKED_STATE_SET)
    }
    return drawableState
  }

  companion object {
    private val CHECKED_STATE_SET = intArrayOf(android.R.attr.state_checked)
  }
}