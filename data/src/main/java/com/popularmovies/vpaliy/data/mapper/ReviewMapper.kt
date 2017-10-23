package com.popularmovies.vpaliy.data.mapper

import com.popularmovies.vpaliy.domain.entity.Review
import com.vpaliy.tmdb.model.TMDBReview
import javax.inject.Singleton

@Singleton
class ReviewMapper:Mapper<Review,TMDBReview>{

    override fun map(fake: TMDBReview)= Review(fake.content,fake.url,fake.author)

    override fun reverse(real: Review)= TMDBReview("",real.author,real.content,real.url)
}