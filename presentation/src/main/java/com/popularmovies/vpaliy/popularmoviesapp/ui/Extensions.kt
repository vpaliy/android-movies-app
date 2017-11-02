package com.popularmovies.vpaliy.popularmoviesapp.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup

fun ViewGroup.inflate(resource:Int)= context.inflater().inflate(resource,this,false)

fun Context.inflater()=LayoutInflater.from(this)!!

fun <T> Boolean.then(item:T)=if(this) item else null