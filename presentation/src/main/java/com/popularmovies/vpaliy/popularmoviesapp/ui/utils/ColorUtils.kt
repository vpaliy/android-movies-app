package com.popularmovies.vpaliy.popularmoviesapp.ui.utils

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.support.annotation.CheckResult
import android.support.annotation.ColorInt
import android.support.annotation.FloatRange
import android.support.annotation.IntRange
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v7.graphics.Palette
import android.widget.ImageView
import android.widget.TextView

fun fetchDominantSwatch(palette: Palette?): Palette.Swatch? {
  if (palette != null) {
    var swatch: Palette.Swatch? = palette.darkVibrantSwatch
    if (swatch == null) swatch = palette.dominantSwatch
    return swatch
  }
  return null
}

fun setDrawableColor(view: TextView, color: Int) {
  val drawables = view.compoundDrawables
  for (drawable in drawables) {
    if (drawable != null) {
      drawable.mutate()
      DrawableCompat.setTint(drawable, color)
    }
  }
}

fun dimColor(color: Int, factor: Float): Int {
  val a = Color.alpha(color)
  val r = Math.round(Color.red(color) * factor)
  val g = Math.round(Color.green(color) * factor)
  val b = Math.round(Color.blue(color) * factor)
  return Color.argb(a,
      Math.min(r, 255),
      Math.min(g, 255),
      Math.min(b, 255))
}

fun setDrawableColor(view: ImageView, color: Int) {
  val drawable = view.drawable
  if (drawable != null) {
    drawable.mutate()
    DrawableCompat.setTint(drawable, color)
  }
}

fun setDrawableColor(drawable: Drawable?, color: Int) {
  if (drawable != null) {
    drawable.mutate()
    DrawableCompat.setTint(drawable, color)
  }
}

fun getPaletteColors(palette: Palette): IntArray {
  val colors = IntArray(2)
  colors[1] = -1
  val dominant = palette.dominantSwatch
  var result = dominant
  if (palette.darkVibrantSwatch != null) {
    result = palette.darkVibrantSwatch
  }
  if (palette.darkMutedSwatch != null) {
    if (result !== dominant) {
      colors[0] = result?.rgb ?: Color.WHITE
      colors[1] = palette.darkMutedSwatch?.rgb ?: Color.WHITE
      return colors
    }
    result = palette.darkMutedSwatch
  }
  colors[0] = dominant?.rgb ?: Color.WHITE
  //check now
  colors[1] = if (result !== dominant) result?.rgb ?: Color.WHITE
  else getSecondaryColor(palette)
  return colors
}


fun getDominantColor(palette: Palette?): Int {
  palette?.let {
    var result = it.dominantSwatch
    if (it.darkVibrantSwatch != null) {
      result = it.darkVibrantSwatch
    } else if (it.darkMutedSwatch != null) {
      result = it.darkMutedSwatch
    }
    return result?.rgb ?: Color.WHITE
  }
  return Color.WHITE
}

fun getSecondaryColor(palette: Palette?): Int {
  if (palette != null) {
    val lightVibrantSwatch = palette.lightVibrantSwatch
    val lightMutedSwatch = palette.lightMutedSwatch

    val tabBackground = lightMutedSwatch ?: (lightVibrantSwatch ?: palette.dominantSwatch)
    return tabBackground?.rgb ?: Color.WHITE
  }
  return Color.WHITE
}


@CheckResult
@ColorInt
fun modifyAlpha(@ColorInt color: Int, @IntRange(from = 0, to = 255) alpha: Int) =
    color and 0x00ffffff or (alpha shl 24)

@CheckResult
@ColorInt
fun modifyAlpha(@ColorInt color: Int, @FloatRange(from = 0.0, to = 1.0) alpha: Float) =
    modifyAlpha(color, (255f * alpha).toInt())
