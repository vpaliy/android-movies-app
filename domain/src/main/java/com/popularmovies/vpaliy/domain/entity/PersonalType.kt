package com.popularmovies.vpaliy.domain.entity

sealed class PersonalType

object Saved : PersonalType()
object Watched : PersonalType()
object Favorite : PersonalType()