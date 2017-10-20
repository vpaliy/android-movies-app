package com.popularmovies.vpaliy.domain.interactor.utils

import com.popularmovies.vpaliy.domain.entity.MovieType

class TypePage(val type:MovieType, current:Int,limit:Int):MediaPage(current,limit)
