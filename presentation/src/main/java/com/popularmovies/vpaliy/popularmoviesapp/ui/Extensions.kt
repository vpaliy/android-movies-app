package com.popularmovies.vpaliy.popularmoviesapp.ui

import android.view.*

inline fun View.afterPost(crossinline callback:View.() -> Unit)=apply { post{ callback() } }
