package com.popularmovies.vpaliy.popularmoviesapp.ui.model

sealed class MediaModel(val id: String, val poster: String,
                        val title: String, val backdrop: String,
                        val release: String, val ratings: String,
                        val tags: List<String>)

class MovieModel(id: String, poster: String, title: String, backdrop: String,
                 release: String, ratings: String, tags: List<String>)
  : MediaModel(id, poster, title, backdrop, release, ratings, tags)

class TVModel(id: String, poster: String, title: String, backdrop: String,
              release: String, ratings: String, tags: List<String>)
  : MediaModel(id, poster, title, backdrop, release, ratings, tags)