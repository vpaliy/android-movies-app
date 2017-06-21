package com.popularmovies.vpaliy.data.mapper;

import com.popularmovies.vpaliy.data.FakeDataProvider;
import com.popularmovies.vpaliy.data.entity.ReviewEntity;
import com.popularmovies.vpaliy.domain.model.Review;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

@RunWith(JUnit4.class)
public class ReviewMapperTest {

    private ReviewMapper mapper=new ReviewMapper();

    @Test
    public void mapsReviewToReviewEntity(){
        Review review= FakeDataProvider.provideReview();
        ReviewEntity entity=mapper.reverseMap(review);
        assertThatAreEqual(review,entity);
    }

    @Test
    public void mapsReviewEntityToReview(){
        ReviewEntity entity=FakeDataProvider.provideReviewEntity();
        Review review=mapper.map(entity);
        assertThatAreEqual(review,entity);
    }

    @Test
    public void mapsReviewEntityListToReviewList(){
        List<ReviewEntity> entities=FakeDataProvider.provideReviewEntityList();
        List<Review> reviews=mapper.map(entities);
        assertThatAreEqual(reviews,entities);
    }

    @Test
    public void mapsReviewListToReviewEntityList(){
        List<Review> reviews=FakeDataProvider.provideReviewList();
        List<ReviewEntity> entities=mapper.reverseMap(reviews);
        assertThatAreEqual(reviews,entities);
    }

    private void assertThatAreEqual(List<Review> reviews, List<ReviewEntity> entities){
        assertThat(reviews.size(),is(entities.size()));
        for(int index=0;index<reviews.size();index++){
            assertThatAreEqual(reviews.get(index),entities.get(index));
        }
    }

    private void assertThatAreEqual(Review review,ReviewEntity entity){
        assertThat(review.getAuthor(),is(entity.getAuthor()));
        assertThat(review.getContent(),is(entity.getContent()));
        assertThat(review.getMovieId(),is(entity.getMediaId()));
        assertThat(review.getUrl(),is(entity.getUrl()));
    }
}
