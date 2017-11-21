package com.popularmovies.vpaliy.popularmoviesapp.ui.search

import android.app.SearchManager
import android.app.SharedElementCallback
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import com.popularmovies.vpaliy.popularmoviesapp.App
import com.popularmovies.vpaliy.popularmoviesapp.R
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.BaseActivity
import com.vpaliy.kotlin_extensions.scale
import com.vpaliy.kotlin_extensions.then
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity:BaseActivity(){

    private val adapter by lazy { SearchAdapter(this,supportFragmentManager) }

    private var checked=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setupSearch()
        setupTransition()
        results.adapter=adapter
        results.offscreenPageLimit=3
        tabLayout.setup(results,adapter)
        tabLayout.tabs.forEach { it.scale(0f) }
    }

    private fun setupTransition(){
        setEnterSharedElementCallback(object : SharedElementCallback(){
            override fun onSharedElementStart(sharedElementNames: MutableList<String>?,
                                              sharedElements: MutableList<View>?,
                                              sharedElementSnapshots: MutableList<View>?) {
                checked=!checked
                back.setImageState(intArrayOf(android.R.attr.state_checked * (checked.then(1)?:-1)),true)
                super.onSharedElementStart(sharedElementNames, sharedElements, sharedElementSnapshots)
            }

            override fun onSharedElementEnd(sharedElementNames: MutableList<String>?, sharedElements: MutableList<View>?, sharedElementSnapshots: MutableList<View>?) {
                super.onSharedElementEnd(sharedElementNames, sharedElements, sharedElementSnapshots)
                tabLayout.tabs.forEach {
                    it.animate().scale(1f)
                            .apply {
                                duration=300
                                start()
                            }
                }
                tabLayout.postDelayed({
                    tabLayout.select(0)
                },300)
            }
        })
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
            override fun onQueryTextChange(query: String?): Boolean {
                if(query.isNullOrEmpty()){
                    adapter.onCleared()
                }
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