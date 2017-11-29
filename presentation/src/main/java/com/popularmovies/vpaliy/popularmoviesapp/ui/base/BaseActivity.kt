package com.popularmovies.vpaliy.popularmoviesapp.ui.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import javax.inject.Inject

abstract class BaseActivity:AppCompatActivity(){

  @Inject lateinit var navigator:Navigator

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    inject()
  }

  abstract fun inject()
}