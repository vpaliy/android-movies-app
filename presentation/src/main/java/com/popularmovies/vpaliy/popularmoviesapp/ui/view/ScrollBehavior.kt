package com.popularmovies.vpaliy.popularmoviesapp.ui.view

import android.content.Context
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View

class ScrollBehavior : AppBarLayout.Behavior() {
    private var isPositive: Boolean = false


    override fun onNestedFling(coordinatorLayout: CoordinatorLayout?, child: AppBarLayout,
                               target: View?, velocityX: Float, velocityY: Float, consumed: Boolean): Boolean {
        var velocityY = velocityY
        var consumed = consumed
        if (velocityY > 0 && !isPositive || velocityY < 0 && isPositive) {
            velocityY = velocityY * -1
        }
        if (target is RecyclerView && velocityY < 0) {
            val recyclerView = target as RecyclerView?
            val firstChild = recyclerView!!.getChildAt(0)
            val childAdapterPosition = recyclerView.getChildAdapterPosition(firstChild)
            consumed = childAdapterPosition > TOP_CHILD_FLING_THRESHOLD
        }
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed)
    }

    override fun onNestedPreScroll(coordinatorLayout: CoordinatorLayout?, child: AppBarLayout?,
                                   target: View?, dx: Int, dy: Int, consumed: IntArray?) {
        isPositive = dy > 0
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed)
    }

    companion object {
        private val TOP_CHILD_FLING_THRESHOLD = 3
    }
}