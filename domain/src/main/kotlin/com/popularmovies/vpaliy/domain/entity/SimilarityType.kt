package com.popularmovies.vpaliy.domain.entity

sealed class SimilarityType {
  companion object {
    fun all()= listOf(Similar, Recommendation)
  }
}

object Similar : SimilarityType()
object Recommendation : SimilarityType()
