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
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BaseActivity


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
        setBottomNavigation()
    }

    override fun inject() {

    }

    private fun setDrawer(){
        drawerLayout.setDrawerListener(drawerToggle)
        next@navigation.setNavigationItemSelectedListener {
            it.isChecked=true
            when (it.itemId) {
                R.id.settings -> {
                   // startActivity(Intent(this, SettingsActivity::class.java))
                    return@next true
                }
            }
            false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_movies, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem)
            = drawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item)

    private fun setActionBar() {
        val statusBarHeight = PresentationUtils.getStatusBarHeight(resources)
        toolbar.layoutParams.height += statusBarHeight
        toolbar.setPadding(0, statusBarHeight, 0, 0)
        setSupportActionBar(toolbar)
        navigation.setCheckedItem(R.id.movies)
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
                                    actionBar.setTitle(R.string.movies)
                                    mediaPager.setCurrentItem(0, false)
                                }
                                R.id.tv_shows -> {
                                    actionBar.setTitle(R.string.tv_shows)
                                    mediaPager.setCurrentItem(1, false)
                                }
                                R.id.personal -> {
                                    actionBar.setTitle(R.string.personal)
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