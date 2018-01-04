package com.popularmovies.vpaliy.popularmoviesapp.ui

import android.graphics.Color
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.*
import com.popularmovies.vpaliy.data.mapper.Mapper

inline fun View.afterPost(crossinline callback: View.() -> Unit) = apply { post { callback() } }

fun <P, R, F> Mapper<R, F>.reflect(target: (P, List<R>) -> Unit) = { params: P, fakes: List<F> -> target(params, map(fakes)) }

fun View?.showMessage(@StringRes resource: Int) {
  this?.let { Snackbar.make(it, resource, Snackbar.LENGTH_LONG).show() }
}

fun View?.showErrorMessage(@StringRes resource: Int) {
  this?.let {
    Snackbar.make(it, resource, Snackbar.LENGTH_LONG)
        .setActionTextColor(Color.RED)
        .show()
  }
}

fun Fragment.showMessage(@StringRes resource: Int) = view.showMessage(resource)

fun Fragment.showErrorMessage(@StringRes resource: Int) = view.showErrorMessage(resource)
