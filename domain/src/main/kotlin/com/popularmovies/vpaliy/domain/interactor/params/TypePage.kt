package com.popularmovies.vpaliy.domain.interactor.params

import com.popularmovies.vpaliy.domain.entity.MediaType

class TypePage(
    val type: MediaType,
    current: Int = 1
) : MediaPage(current)
