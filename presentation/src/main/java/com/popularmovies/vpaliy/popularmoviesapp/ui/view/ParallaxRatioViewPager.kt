package com.popularmovies.vpaliy.popularmoviesapp.ui.view

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.os.Build
import android.support.annotation.FloatRange
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.View
import com.popularmovies.vpaliy.popularmoviesapp.R
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.modifyAlpha

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
class ParallaxRatioViewPager @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0)
  : ViewPager(context, attrs) {

  private var imageRatio = .5625f
  private val scrimPaint: Paint
  private var imageOffset: Int = 0
  private var minOffset: Int = 0
  private val clipBound = Rect()
  private var scrimAlpha = 0f
  private var maxScrimAlpha = 1f
  private var scrimColor = Color.TRANSPARENT
  private var parallaxFactor = -0.5f
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

  init {
    if (attrs != null) {
      val array = getContext().obtainStyledAttributes(attrs, R.styleable.ParallaxRatioViewPager)
      imageRatio = array.getFloat(R.styleable.ParallaxRatioViewPager_image_ratio, imageRatio)
      scrimAlpha = array.getFloat(R.styleable.ParallaxRatioViewPager_scrimAlpha, scrimAlpha)
      maxScrimAlpha = array.getFloat(R.styleable.ParallaxRatioViewPager_maxScrimAlpha, maxScrimAlpha)
      scrimColor = array.getColor(R.styleable.ParallaxRatioViewPager_scrimColor, scrimColor)
      parallaxFactor = array.getFloat(R.styleable.ParallaxRatioViewPager_parallaxFactor, parallaxFactor)
      array.recycle()
    }
    scrimPaint = Paint()
    scrimPaint.color = scrimColor and 0x00ffffff or ((255f * scrimAlpha).toInt() shl 24)
  }

  var offset: Int
    get() = translationY.toInt()
    set(offset) {
      var offset = offset
      offset = Math.max(minOffset, offset)
      if (offset.toFloat() != translationY) {
        translationY = offset.toFloat()
        imageOffset = (offset * parallaxFactor).toInt()
        clipBound.set(0, -offset, width, height)
        clipBounds = clipBound
        setScrimAlpha(Math.min(
                (-offset).toFloat() / minimumHeight * maxScrimAlpha, maxScrimAlpha))
        postInvalidateOnAnimation()
      }
      isPinned = offset == minOffset
    }

  override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
    super.onSizeChanged(w, h, oldw, oldh)
    if (h > minimumHeight) {
      minOffset = minimumHeight - h
    }
  }

  fun setScrimAlpha(@FloatRange(from = 0.0, to = 1.0) alpha: Float) {
    if (scrimAlpha != alpha) {
      scrimAlpha = alpha
      scrimPaint.color = modifyAlpha(scrimColor, scrimAlpha)
      postInvalidateOnAnimation()
    }
  }

  public override fun onCreateDrawableState(extraSpace: Int): IntArray {
    val drawableState = super.onCreateDrawableState(extraSpace + 1)
    if (this.isPinned) {
      View.mergeDrawableStates(drawableState, STATE_PINNED)
    }
    return drawableState
  }

  fun setImageRatio(imageRatio: Float) {
    this.imageRatio = imageRatio
    requestLayout()
  }

  companion object {
    private val STATE_PINNED = intArrayOf(R.attr.state_pinned)
  }
}