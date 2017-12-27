package com.popularmovies.vpaliy.popularmoviesapp.ui.settings

import android.support.annotation.StringRes

object SettingsContract{
  interface Presenter{
    fun attachView()
  }

  interface View{
    fun showMessage(@StringRes resource:Int)
    fun showError(@StringRes resource:Int)
  }
}
