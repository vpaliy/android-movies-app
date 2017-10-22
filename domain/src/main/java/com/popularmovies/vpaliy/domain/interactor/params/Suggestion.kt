package com.popularmovies.vpaliy.domain.interactor.params

import com.popularmovies.vpaliy.domain.entity.SimilarityType

class Suggestion<out T> (val item:T, val type: SimilarityType,current:Int, limit:Int) :MediaPage(current,limit)