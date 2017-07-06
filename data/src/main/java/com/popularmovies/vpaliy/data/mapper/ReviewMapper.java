package com.popularmovies.vpaliy.data.mapper;

import com.popularmovies.vpaliy.data.entity.ReviewEntity;
import com.popularmovies.vpaliy.domain.model.Review;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ReviewMapper extends Mapper<Review,ReviewEntity> {

    @Inject
    public ReviewMapper(){}

    @Override
    public Review map(ReviewEntity reviewEntity) {
        if(reviewEntity==null) return null;
        return new Review(reviewEntity.getMediaId(),reviewEntity.getAuthor(),
                reviewEntity.getContent(),reviewEntity.getUrl());
    }

    @Override
    public ReviewEntity reverseMap(Review review) {
        if(review==null) return null;
        ReviewEntity entity=new ReviewEntity();
        entity.setMediaId(review.getMovieId());
        entity.setUrl(review.getUrl());
        entity.setContent(review.getContent());
        entity.setAuthor(review.getAuthor());
        return entity;
    }
}
