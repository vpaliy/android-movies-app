package com.popularmovies.vpaliy.domain.interactor.params

import com.popularmovies.vpaliy.domain.entity.MovieType

class TypePage(val type:MovieType, current:Int=0):MediaPage(current)
