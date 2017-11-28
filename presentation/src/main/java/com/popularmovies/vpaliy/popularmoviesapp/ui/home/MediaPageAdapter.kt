package com.popularmovies.vpaliy.popularmoviesapp.ui.home

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.popularmovies.vpaliy.popularmoviesapp.R

class MediaPageAdapter(manager: FragmentManager, val context: Context):FragmentStatePagerAdapter(manager) {

  override fun getPageTitle(position: Int): CharSequence {
    when (position) {
      0 -> return context.getString(R.string.movies)
      1 -> return context.getString(R.string.tv_shows)
      2 -> return context.getString(R.string.personal)
    }
    return super.getPageTitle(position)
  }

  override fun getCount()=3

  override fun getItem(position: Int): Fragment? {
    when (position) {
      0 -> return MoviesFragment()
      1 -> return TVFragment()
      2 -> return TVFragment()
    }
    return null
  }

}