package com.popularmovies.vpaliy.domain

import com.popularmovies.vpaliy.domain.entity.MediaType
import com.popularmovies.vpaliy.domain.interactor.params.SearchPage
import com.popularmovies.vpaliy.domain.interactor.params.TypePage

const val ID = "ID"
const val CONTENT = "CONTENT"
const val URL = "URL"
const val AUTHOR = "AUTHOR"

val DEFAULT=TypePage(MediaType.AIRING)
val SEARCH_DEFAULT=SearchPage("")