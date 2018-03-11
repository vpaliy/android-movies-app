package com.popularmovies.vpaliy.domain.interactor.params

import com.popularmovies.vpaliy.domain.entity.SimilarityType

class Suggestion(
    val id: String,
    val type: SimilarityType,
    current: Int = 1
) : MediaPage(current)