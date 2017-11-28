package com.popularmovies.vpaliy.popularmoviesapp.ui.transition

import android.animation.Animator
import android.content.Context
import android.graphics.Point
import android.transition.TransitionValues
import android.transition.Visibility
import android.util.AttributeSet
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.support.annotation.IdRes
import com.popularmovies.vpaliy.popularmoviesapp.R

class CircularReveal : Visibility {

  private var center: Point? = null
  private var startRadius: Float = 0.toFloat()
  private var endRadius: Float = 0.toFloat()
  @IdRes
  private var centerOnId = View.NO_ID
  private var centerOn: View? = null

  constructor() : super() {}

  constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    val a = context.obtainStyledAttributes(attrs, R.styleable.CircularReveal)
    startRadius = a.getDimension(R.styleable.CircularReveal_startRadius, 0f)
    endRadius = a.getDimension(R.styleable.CircularReveal_endRadius, 0f)
    centerOnId = a.getResourceId(R.styleable.CircularReveal_centerOn, View.NO_ID)
    a.recycle()
  }

  /**
   * The center point of the reveal then conceal, relative to the target `view`.
   */
  fun setCenter(center: Point) {
    this.center = center
  }

  /**
   * Center the reveal then conceal on this view.
   */
  fun centerOn(source: View) {
    centerOn = source
  }

  /**
   * Sets the radius that **reveals** start from.
   */
  fun setStartRadius(startRadius: Float) {
    this.startRadius = startRadius
  }

  /**
   * Sets the radius that **conceals** end at.
   */
  fun setEndRadius(endRadius: Float) {
    this.endRadius = endRadius
  }

  override fun onAppear(sceneRoot: ViewGroup, view: View?,
                        startValues: TransitionValues?,
                        endValues: TransitionValues?): Animator? {
    if (view == null || view.height == 0 || view.width == 0) return null
    ensureCenterPoint(sceneRoot, view)
    return PauseLessAnimator(ViewAnimationUtils.createCircularReveal(
            view,
            center!!.x,
            center!!.y,
            startRadius,
            getFullyRevealedRadius(view)))
  }

  override fun onDisappear(sceneRoot: ViewGroup, view: View?,
                           startValues: TransitionValues?,
                           endValues: TransitionValues?): Animator? {
    if (view == null || view.height == 0 || view.width == 0) return null
    ensureCenterPoint(sceneRoot, view)
    return PauseLessAnimator(ViewAnimationUtils.createCircularReveal(view,
            center!!.x,
            center!!.y,
            getFullyRevealedRadius(view),
            endRadius))
  }

  private fun ensureCenterPoint(sceneRoot: ViewGroup, view: View) {
    if (center != null) return
    if (centerOn != null || centerOnId != View.NO_ID) {
      val source: View?
      if (centerOn != null) {
        source = centerOn
      } else {
        source = sceneRoot.findViewById(centerOnId)
      }
      if (source != null) {
        // use window location to allow views in diff hierarchies
        val loc = IntArray(2)
        source.getLocationInWindow(loc)
        val srcX = loc[0] + source.width / 2
        val srcY = loc[1] + source.height / 2
        view.getLocationInWindow(loc)
        center = Point(srcX - loc[0], srcY - loc[1])
      }
    }
    // else use the pivot point
    if (center == null) {
      center = Point(Math.round(view.pivotX), Math.round(view.pivotY))
    }
  }

  private fun getFullyRevealedRadius(view: View): Float {
    return Math.hypot(
            Math.max(center!!.x, view.width - center!!.x).toDouble(),
            Math.max(center!!.y, view.height - center!!.y).toDouble()).toFloat()
  }
}