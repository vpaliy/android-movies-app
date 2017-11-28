package com.popularmovies.vpaliy.popularmoviesapp.ui.view

import android.annotation.SuppressLint
import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

class MediaPager(context: Context, attrs: AttributeSet):ViewPager(context, attrs) {
  @SuppressLint("ClickableViewAccessibility")
  override fun onTouchEvent(event: MotionEvent)=false
  override fun onInterceptTouchEvent(event: MotionEvent)=false
}