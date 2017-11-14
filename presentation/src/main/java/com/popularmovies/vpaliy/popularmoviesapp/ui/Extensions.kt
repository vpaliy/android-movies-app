package com.popularmovies.vpaliy.popularmoviesapp.ui

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.annotation.ColorRes
import android.support.annotation.DimenRes
import android.support.annotation.DrawableRes
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.view.*

fun ViewGroup.inflate(resource:Int)= context.inflater().inflate(resource,this,false)

fun Context.inflater()=LayoutInflater.from(this)!!

infix fun <T> Boolean.then(item:T)=if(this) item else null

infix fun <T> Boolean.then(result:()->T)=if(this) result() else null

fun Context.getCompatDrawable(@DrawableRes id:Int)=ContextCompat.getDrawable(this,id)

fun View.getDrawable(@DrawableRes id:Int): Drawable =ContextCompat.getDrawable(context,id)

fun Context.color(@ColorRes id:Int)=ContextCompat.getColor(this,id)

fun Fragment.color(@ColorRes id:Int)=context.color(id)

fun View.getDimensInt(@DimenRes id:Int)=resources.getDimension(id).toInt()

fun View.getDimens(@DimenRes id:Int)=resources.getDimension(id)

fun View.getColor(@ColorRes color:Int)= ContextCompat.getColor(context,color)

fun View.assignBackground(drawable: Drawable)= ViewCompat.setBackground(this,drawable)

fun Context.getDimen(@DimenRes id:Int)=resources.getDimension(id)

fun SwipeRefreshLayout.turnOff()=setOnRefreshListener { isRefreshing=false }

fun View.getMinHeight()=ViewCompat.getMinimumHeight(this)

fun View.click(callback: () -> Unit)=setOnClickListener { callback() }

fun View.addTemporaryOnPreDraw(callback:()->Unit){
    viewTreeObserver.addOnPreDrawListener(object:ViewTreeObserver.OnPreDrawListener{
        override fun onPreDraw(): Boolean {
            viewTreeObserver.removeOnPreDrawListener(this)
            callback()
            return true
        }
    })
}

fun View.setScale(factor:Float)=apply {
    scaleX=factor
    scaleY=factor
}

fun ViewPropertyAnimator.scale(factor:Float)=apply {
    scaleX(factor)
    scaleY(factor)
}
