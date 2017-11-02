package com.popularmovies.vpaliy.popularmoviesapp.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent
import com.popularmovies.vpaliy.popularmoviesapp.ui.then


class MediaPager(context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {
    var allowed= false

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent)=
            allowed.then(super.onTouchEvent(event))?:false

    override fun onInterceptTouchEvent(event: MotionEvent)=
            allowed.then(super.onInterceptTouchEvent(event))?:false
}