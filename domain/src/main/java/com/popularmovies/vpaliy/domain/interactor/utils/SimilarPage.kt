package com.popularmovies.vpaliy.domain.interactor.utils

import com.popularmovies.vpaliy.domain.entity.SimilarityType

class SimilarPage(val type: SimilarityType,current:Int,limit:Int):MediaPage(current,limit)