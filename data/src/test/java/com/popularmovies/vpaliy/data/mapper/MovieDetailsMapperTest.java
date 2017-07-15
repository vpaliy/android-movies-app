package com.popularmovies.vpaliy.data.mapper;

import com.popularmovies.vpaliy.data.FakeDataProvider;
import com.popularmovies.vpaliy.data.entity.ActorEntity;
import com.popularmovies.vpaliy.data.entity.CollectionEntity;
import com.popularmovies.vpaliy.data.entity.Movie;
import com.popularmovies.vpaliy.data.entity.MovieDetailEntity;
import com.popularmovies.vpaliy.data.entity.ReviewEntity;
import com.popularmovies.vpaliy.data.entity.TrailerEntity;
import com.popularmovies.vpaliy.domain.model.ActorCover;
import com.popularmovies.vpaliy.domain.model.MediaCollection;
import com.popularmovies.vpaliy.domain.model.MediaCover;
import com.popularmovies.vpaliy.domain.model.MovieDetails;
import com.popularmovies.vpaliy.domain.model.MovieInfo;
import com.popularmovies.vpaliy.domain.model.Review;
import com.popularmovies.vpaliy.domain.model.Trailer;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

@RunWith(MockitoJUnitRunner.class)
public class MovieDetailsMapperTest {

    @Mock
    private Mapper<MediaCover,Movie> movieMapper;

    @Mock
    private Mapper<ActorCover,ActorEntity> actorMapper;

    @Mock
    private Mapper<MovieInfo,Movie> infoMapper;

    @Mock
    private Mapper<Review,ReviewEntity> reviewMapper;

    @Mock
    private Mapper<Trailer,TrailerEntity> trailerMapper;


    @Mock
    private Mapper<MediaCollection,CollectionEntity> collectionMapper;

    private MovieDetailsMapper detailsMapper;

    @Before
    public void setUp(){
        detailsMapper=new MovieDetailsMapper(movieMapper,actorMapper,
                infoMapper,reviewMapper,trailerMapper,collectionMapper);
    }

    @Test
    public void mapsFakeToReal(){
        MovieDetailEntity entity= FakeDataProvider.provideMovieDetailsEntity();
        detailsMapper.map(entity);

        verify(movieMapper).map(eq(entity.getSimilarMovies()));
        verify(actorMapper).map(anyList());
        verify(infoMapper).map(eq(entity.getMovie()));
        verify(reviewMapper).map(eq(entity.getReviews()));
        verify(trailerMapper).map(eq(entity.getTrailers()));
    }

    @Test
    public void mapsRealToFake(){
        MovieDetails details=FakeDataProvider.provideMovieDetails();
        detailsMapper.reverseMap(details);

        verify(movieMapper).reverseMap(eq(details.getSimilarMovies()));
        verify(actorMapper).reverseMap(anyList());
        verify(infoMapper).reverseMap(eq(details.getMovieInfo()));
        verify(reviewMapper).reverseMap(eq(details.getReviews()));
        verify(trailerMapper).reverseMap(eq(details.getTrailers()));
    }

    @Test
    public void mapsFakeListToRealList(){
        List<MovieDetailEntity> entityList=FakeDataProvider.provideMovieDetailEntityList();
        detailsMapper.map(entityList);

        verify(movieMapper,times(entityList.size())).map(anyList());
        verify(actorMapper,times(entityList.size())).map(anyList());
        verify(infoMapper,times(entityList.size())).map(any(Movie.class));
        verify(reviewMapper,times(entityList.size())).map(anyList());
        verify(trailerMapper,times(entityList.size())).map(anyList());
    }

    @Test
    public void mapsRealListToFakeList(){
        List<MovieDetails> detailsList=FakeDataProvider.provideMovieDetailsList();
        detailsMapper.reverseMap(detailsList);

        verify(movieMapper,times(detailsList.size())).reverseMap(anyList());
        verify(actorMapper,times(detailsList.size())).reverseMap(anyList());
        verify(infoMapper,times(detailsList.size())).reverseMap(any(MovieInfo.class));
        verify(reviewMapper,times(detailsList.size())).reverseMap(anyList());
        verify(trailerMapper,times(detailsList.size())).reverseMap(anyList());
    }
}
