package com.popularmovies.vpaliy.domain.interactor.params

import com.popularmovies.vpaliy.domain.entity.PersonalType

data class PersonalArg<out T>(val arg:T,var type:PersonalType)