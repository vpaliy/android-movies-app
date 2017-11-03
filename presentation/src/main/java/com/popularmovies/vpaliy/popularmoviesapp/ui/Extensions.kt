package com.popularmovies.vpaliy.popularmoviesapp.ui

import android.content.Context
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.ViewGroup

fun ViewGroup.inflate(resource:Int)= context.inflater().inflate(resource,this,false)

fun Context.inflater()=LayoutInflater.from(this)!!

fun <T> Boolean.then(item:T)=if(this) item else null

fun Context.getCompatDrawable(@DrawableRes id:Int)=ContextCompat.getDrawable(this,id)

fun Context.color(@ColorRes id:Int)=ContextCompat.getColor(this,id)

fun Fragment.color(@ColorRes id:Int)=context.color(id)