package com.popularmovies.vpaliy.popularmoviesapp.ui

import android.view.*
import com.popularmovies.vpaliy.data.mapper.Mapper

inline fun View.afterPost(crossinline callback:View.() -> Unit)=apply { post{ callback() } }

fun <R,F> Mapper<R, F>.reflect(target:(List<R>)->Unit)={ fakes:List<F> -> target(map(fakes)) }

fun <P,R,F> Mapper<R,F>.reflect(target:(P, List<R>)->Unit)= { params:P, fakes:List<F> -> target(params,map(fakes))}