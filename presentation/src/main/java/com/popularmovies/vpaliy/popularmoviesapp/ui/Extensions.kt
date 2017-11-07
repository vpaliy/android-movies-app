package com.popularmovies.vpaliy.popularmoviesapp.ui

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.annotation.ColorRes
import android.support.annotation.DimenRes
import android.support.annotation.DrawableRes
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

fun ViewGroup.inflate(resource:Int)= context.inflater().inflate(resource,this,false)

fun Context.inflater()=LayoutInflater.from(this)!!

infix fun <T> Boolean.then(item:T)=if(this) item else null

infix fun <T> Boolean.then(result:()->T)=if(this) result() else null

fun Context.getCompatDrawable(@DrawableRes id:Int)=ContextCompat.getDrawable(this,id)

fun Context.color(@ColorRes id:Int)=ContextCompat.getColor(this,id)

fun Fragment.color(@ColorRes id:Int)=context.color(id)

fun View.getDimensInt(@DimenRes id:Int)=resources.getDimension(id).toInt()

fun View.getDimens(@DimenRes id:Int)=resources.getDimension(id)

fun View.getColor(@ColorRes color:Int)= ContextCompat.getColor(context,color)

fun View.assignBackground(drawable: Drawable)= ViewCompat.setBackground(this,drawable)