package com.popularmovies.vpaliy.popularmoviesapp.ui.view

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.popularmovies.vpaliy.popularmoviesapp.R
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.PresentationUtils
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.isNavBarOnBottom
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.modifyAlpha
import java.util.ArrayList


class ElasticDismissLayout @TargetApi(Build.VERSION_CODES.LOLLIPOP)
@JvmOverloads constructor(context: Context, attrs: AttributeSet?=null, defStyleAttr: Int=0, defStyleRes: Int=0)
    :FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    private var dragDismissDistance = java.lang.Float.MAX_VALUE
    private var dragDismissFraction = -1f
    private var dragDismissScale = 1f
    private var shouldScale = false
    private var dragElacticity = 0.8f

    private var totalDrag: Float = 0.toFloat()
    private var draggingDown = false
    private var draggingUp = false

    private var callbacks: MutableList<ElasticDragDismissCallback>? = null

    init {
        attrs?.let {
            val a = context.obtainStyledAttributes(it, R.styleable.ElasticDragDismissFrameLayout)

            if (a.hasValue(R.styleable.ElasticDragDismissFrameLayout_dragDismissDistance)) {
                dragDismissDistance = a.getDimensionPixelSize(R.styleable
                        .ElasticDragDismissFrameLayout_dragDismissDistance, 0).toFloat()
            } else if (a.hasValue(R.styleable.ElasticDragDismissFrameLayout_dragDismissFraction)) {
                dragDismissFraction = a.getFloat(R.styleable
                        .ElasticDragDismissFrameLayout_dragDismissFraction, dragDismissFraction)
            }
            if (a.hasValue(R.styleable.ElasticDragDismissFrameLayout_dragDismissScale)) {
                dragDismissScale = a.getFloat(R.styleable
                        .ElasticDragDismissFrameLayout_dragDismissScale, dragDismissScale)
                shouldScale = dragDismissScale != 1f
            }
            if (a.hasValue(R.styleable.ElasticDragDismissFrameLayout_dragElasticity)) {
                dragElacticity = a.getFloat(R.styleable.ElasticDragDismissFrameLayout_dragElasticity,
                        dragElacticity)
            }
            a.recycle()
        }
    }

    abstract class ElasticDragDismissCallback {

        /**
         * Called for each drag event.

         * @param elasticOffset       Indicating the drag offset with elasticity applied i.e. may
         * *                            exceed 1.
         * *
         * @param elasticOffsetPixels The elastically scaled drag distance in pixels.
         * *
         * @param rawOffset           Value from [0, 1] indicating the raw drag offset i.e.
         * *                            without elasticity applied. A value of 1 indicates that the
         * *                            dismiss distance has been reached.
         * *
         * @param rawOffsetPixels     The raw distance the user has dragged
         */
        internal open fun onDrag(elasticOffset: Float, elasticOffsetPixels: Float,
                                 rawOffset: Float, rawOffsetPixels: Float) {
        }

        /**
         * Called when dragging is released and has exceeded the threshold dismiss distance.
         */
        internal open fun onDragDismissed() {}

    }

    override fun onStartNestedScroll(child: View, target: View, nestedScrollAxes: Int)=nestedScrollAxes and View.SCROLL_AXIS_VERTICAL != 0

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray) {
        // if we're in a drag gesture and the user reverses up the we should take those events
        if (draggingDown && dy > 0 || draggingUp && dy < 0) {
            dragScale(dy)
            consumed[1] = dy
        }
    }

    override fun onNestedScroll(target: View, dxConsumed: Int, dyConsumed: Int,
                                dxUnconsumed: Int, dyUnconsumed: Int)=dragScale(dyUnconsumed)

    override fun onStopNestedScroll(child: View) {
        if (Math.abs(totalDrag) >= dragDismissDistance) {
            dispatchDismissCallback()
        } else { // settle back to natural position
            animate()
                    .translationY(0f)
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(200L)
                    .setInterpolator(PresentationUtils.getFastOutSlowInInterpolator(context))
                    .setListener(null)
                    .start()
            totalDrag = 0f
            draggingUp = false
            draggingDown = draggingUp
            dispatchDragCallback(0f, 0f, 0f, 0f)
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (dragDismissFraction > 0f) {
            dragDismissDistance = h * dragDismissFraction
        }
    }

    fun addListener(listener: ElasticDragDismissCallback) {
        if (callbacks == null) {
            callbacks = ArrayList<ElasticDragDismissCallback>()
        }
        callbacks?.add(listener)
    }

    fun removeListener(listener: ElasticDragDismissCallback) =callbacks?.remove(listener)

    private fun dragScale(scroll: Int) {
        if (scroll == 0) return

        totalDrag += scroll.toFloat()

        // track the direction & set the pivot point for scaling
        // don't double track i.e. if start dragging down and then reverse, keep tracking as
        // dragging down until they reach the 'natural' position
        if (scroll < 0 && !draggingUp && !draggingDown) {
            draggingDown = true
            if (shouldScale) pivotY = height.toFloat()
        } else if (scroll > 0 && !draggingDown && !draggingUp) {
            draggingUp = true
            if (shouldScale) pivotY = 0f
        }
        // how far have we dragged relative to the distance to perform a dismiss
        // (0–1 where 1 = dismiss distance). Decreasing logarithmically as we approach the limit
        var dragFraction = Math.log10((1 + Math.abs(totalDrag) / dragDismissDistance).toDouble()).toFloat()

        // calculate the desired translation given the drag fraction
        var dragTo = dragFraction * dragDismissDistance * dragElacticity

        if (draggingUp) {
            // as we use the absolute magnitude when calculating the drag fraction, need to
            // re-apply the drag direction
            dragTo *= -1f
        }
        translationY = dragTo

        if (shouldScale) {
            val scale = 1 - (1 - dragDismissScale) * dragFraction
            scaleX = scale
            scaleY = scale
        }

        // if we've reversed direction and gone past the settle point then clear the flags to
        // allow the list to get the scroll events & reset any transforms
        if (draggingDown && totalDrag >= 0 || draggingUp && totalDrag <= 0) {
            dragFraction = 0f
            dragTo = dragFraction
            totalDrag = dragTo
            draggingUp = false
            draggingDown = draggingUp
            translationY = 0f
            scaleX = 1f
            scaleY = 1f
        }
        dispatchDragCallback(dragFraction, dragTo,
                Math.min(1f, Math.abs(totalDrag) / dragDismissDistance), totalDrag)
    }

    private fun dispatchDragCallback(elasticOffset: Float, elasticOffsetPixels: Float,
                                     rawOffset: Float, rawOffsetPixels: Float) {
        callbacks?.forEach {
            it.onDrag(elasticOffset, elasticOffsetPixels,
                    rawOffset, rawOffsetPixels)
        }
    }

    private fun dispatchDismissCallback() {
        callbacks?.forEach{
            it.onDragDismissed()
        }
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    class SystemChromeFader(private val activity: Activity) : ElasticDragDismissCallback() {
        private val statusBarAlpha=Color.alpha(activity.window.statusBarColor)
        private val navBarAlpha=Color.alpha(activity.window.navigationBarColor)
        private val fadeNavBar=isNavBarOnBottom(activity)

        public override fun onDrag(elasticOffset: Float, elasticOffsetPixels: Float,
                                   rawOffset: Float, rawOffsetPixels: Float) {
            if (elasticOffsetPixels > 0) {
                // dragging downward, fade the status bar in proportion
                activity.window.statusBarColor = modifyAlpha(activity.window
                        .statusBarColor, ((1f - rawOffset) * statusBarAlpha).toInt())
            } else if (elasticOffsetPixels == 0f) {
                // reset
                activity.window.statusBarColor = modifyAlpha(
                        activity.window.statusBarColor, statusBarAlpha)
                activity.window.navigationBarColor = modifyAlpha(
                        activity.window.navigationBarColor, navBarAlpha)
            } else if (fadeNavBar) {
                // dragging upward, fade the navigation bar in proportion
                activity.window.navigationBarColor = modifyAlpha(activity.window.navigationBarColor,
                        ((1f - rawOffset) * navBarAlpha).toInt())
            }
        }


        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        public override fun onDragDismissed() {
            activity.finishAfterTransition()
        }
    }
}