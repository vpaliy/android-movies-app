package com.popularmovies.vpaliy.popularmoviesapp.ui.home

import android.animation.Animator
import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.view.View
import com.popularmovies.vpaliy.popularmoviesapp.R
import kotlinx.android.synthetic.main.activity_home.*
import android.view.Menu
import android.view.MenuItem
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.PresentationUtils
import android.animation.AnimatorListenerAdapter
import com.popularmovies.vpaliy.popularmoviesapp.App
import com.popularmovies.vpaliy.popularmoviesapp.di.component.DaggerApplicationComponent
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BaseActivity
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.getStatusBarHeight

class HomeActivity: BaseActivity(){

    private val drawerToggle by lazy {
        ActionBarDrawerToggle(this, drawerLayout,toolbar, 0, 0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        setDrawer()
        setActionBar()
        setMediaPager()
        setBottomNavigation()
        setSupportActionBar(toolbar)
    }

    override fun inject() {
        App.component?.inject(this)
    }

    private fun setDrawer(){
        drawerLayout.setDrawerListener(drawerToggle)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main,menu)
        return true
    }

    override fun onPostCreate(savedInstanceState: Bundle?){
        super.onPostCreate(savedInstanceState)
        drawerToggle.syncState()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.search-> {
                val search =toolbar.findViewById<View>(R.id.search)
                search.transitionName=getString(R.string.search_trans)
                //navigator.search(this, Pair(search, getString(R.string.search_trans)))
                navigator.navigateToSearch(this)
                return true
            }
        }
        return drawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item)
    }

    private fun setActionBar() {
        val statusBarHeight = getStatusBarHeight(resources)
        toolbar.layoutParams.height += statusBarHeight
        toolbar.setPadding(0, statusBarHeight, 0, 0)
        setSupportActionBar(toolbar)
        navigation.setCheckedItem(R.id.movies)
    }

    private fun setMediaPager(){
        mediaPager.adapter = MediaPageAdapter(supportFragmentManager, this)
        mediaPager.offscreenPageLimit = 3
    }

    private fun setBottomNavigation() {
        val duration = resources.getInteger(R.integer.page_fade_duration).toLong()
        bottomNavigator.setOnTabSelectListener({ tabId ->
            mediaPager.animate()
                    .alpha(0f)
                    .setDuration(duration)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            super.onAnimationEnd(animation)
                            when (tabId) {
                                R.id.movies -> {
                                    toolbar.setTitle(R.string.movies)
                                    mediaPager.setCurrentItem(0, false)
                                }
                                R.id.tv_shows -> {
                                    toolbar.setTitle(R.string.tv_shows)
                                    mediaPager.setCurrentItem(1, false)
                                }
                                R.id.personal -> {
                                    toolbar.setTitle(R.string.personal)
                                    mediaPager.setCurrentItem(2, false)
                                }
                            }
                            mediaPager.animate()
                                    .alpha(1f)
                                    .setDuration(duration)
                                    .setListener(null).start()
                        }}).start()
        })
    }
}