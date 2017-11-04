package com.popularmovies.vpaliy.popularmoviesapp.ui.utils

import android.graphics.Color
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