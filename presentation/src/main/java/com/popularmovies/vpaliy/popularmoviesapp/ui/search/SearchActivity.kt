package com.popularmovies.vpaliy.popularmoviesapp.ui.search

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import com.popularmovies.vpaliy.popularmoviesapp.App
import com.popularmovies.vpaliy.popularmoviesapp.R
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity:BaseActivity(){

    private val adapter by lazy { SearchAdapter(supportFragmentManager) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setupSearch()
        playTabLayout.colors= intArrayOf(R.color.colorMovies,
                R.color.colorTvShows,R.color.colorFavorite)
        results.adapter=adapter
        with(playTabLayout.tabLayout){
            setupWithViewPager(results)
            setSelectedTabIndicatorColor(Color.WHITE)
            setSelectedTabIndicatorHeight(7)
        }
    }

    private fun setupSearch(){
        val searchManager=getSystemService(Context.SEARCH_SERVICE) as SearchManager
        back.setOnClickListener{onBackPressed()}
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint=getString(R.string.search_hint)
        searchView.inputType= InputType.TYPE_TEXT_FLAG_AUTO_CORRECT
        searchView.imeOptions = searchView.imeOptions or EditorInfo.IME_ACTION_SEARCH or
                EditorInfo.IME_FLAG_NO_EXTRACT_UI or EditorInfo.IME_FLAG_NO_FULLSCREEN
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextChange(p0: String?): Boolean {
                //TODO implement this
                return true
            }
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.queryTyped(query!!)
                searchView.clearFocus()
                hideKeyboard()
                return true
            }
        })
    }

    override fun onNewIntent(intent: Intent) {
        if (intent.hasExtra(SearchManager.QUERY)) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            if (!query.isNullOrEmpty()) {
                searchView.setQuery(query, false)
                searchView.clearFocus()
                hideKeyboard()
                adapter.queryTyped(query)
            }
        }
    }

    private fun hideKeyboard() {
        currentFocus?.let{
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    override fun inject() {
        App.component?.inject(this)
    }
}