package com.popularmovies.vpaliy.popularmoviesapp.ui.utils

import android.content.Context
import android.content.res.Resources

fun getStatusBarHeight(resources: Resources): Int {
  var result = 0
  val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
  if (resourceId > 0) {
    result = resources.getDimensionPixelSize(resourceId)
  }
  return result
}

fun isNavBarOnBottom(context: Context): Boolean {
  val res = context.resources
  val cfg = context.resources.configuration
  val dm = res.displayMetrics
  val canMove = dm.widthPixels != dm.heightPixels && cfg.smallestScreenWidthDp < 600
  return !canMove || dm.widthPixels < dm.heightPixels
}