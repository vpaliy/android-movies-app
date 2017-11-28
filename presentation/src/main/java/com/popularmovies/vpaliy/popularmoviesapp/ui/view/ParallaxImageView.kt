package com.popularmovies.vpaliy.popularmoviesapp.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.View

import com.popularmovies.vpaliy.popularmoviesapp.R

class ParallaxImageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0)
  :RoundedImageView(context, attrs, defStyle) {
  var isChecked = false
    set(isChecked) {
      if (this.isChecked != isChecked) {
        field = isChecked
        refreshDrawableState()
      }
    }

  var minOffset: Float = 0f
  var staticOffset: Float = 0f

  var isPinned = false
    set(isPinned) {
      if (this.isPinned != isPinned) {
        field = isPinned
        refreshDrawableState()
        if (isPinned && isImmediatePin) {
          jumpDrawablesToCurrentState()
        }
      }
    }
  var isImmediatePin = false

  fun setOffset(offset: Float) {
    if (offset != translationY) {
      translationY= Math.max(minOffset, offset)
    }
  }

  override fun onCreateDrawableState(extraSpace: Int): IntArray {
    val drawableState = super.onCreateDrawableState(extraSpace + 1)
    if (isPinned) {
      View.mergeDrawableStates(drawableState, STATE_PINNED)
    }
    return drawableState
  }

  companion object {
    private val STATE_PINNED = intArrayOf(R.attr.state_pinned)
  }
}