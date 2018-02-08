package com.popularmovies.vpaliy.popularmoviesapp.ui.view

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.View

class TranslatableLayout(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {
  var isChecked = false
    set(isChecked) {
      if (this.isChecked != isChecked) {
        field = isChecked
        refreshDrawableState()
      }
    }

  var staticOffset: Float = 0.toFloat()

  var offset: Float
    get() = translationY
    set(offset) {
      if (offset != translationY) {
        translationY = offset
      }
    }

  public override fun onCreateDrawableState(extraSpace: Int): IntArray {
    val drawableState = super.onCreateDrawableState(extraSpace + 1)
    if (isChecked) {
      View.mergeDrawableStates(drawableState, CHECKED_STATE_SET)
    }
    return drawableState
  }

  companion object {

    private val CHECKED_STATE_SET = intArrayOf(android.R.attr.state_checked)
  }
}