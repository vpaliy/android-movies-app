package com.popularmovies.vpaliy.data.mapper

import com.popularmovies.vpaliy.domain.entity.Review
import com.vpaliy.tmdb.model.ReviewModel
import javax.inject.Singleton

@Singleton
class ReviewMapper:Mapper<Review,ReviewModel>{

    override fun map(fake: ReviewModel)= Review(fake.content,fake.url,fake.author)

    override fun reverse(real: Review):ReviewModel{
        return ReviewModel()
    }
}