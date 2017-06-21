package com.popularmovies.vpaliy.data;


import com.popularmovies.vpaliy.data.entity.ActorEntity;
import com.popularmovies.vpaliy.data.entity.BackdropImage;
import com.popularmovies.vpaliy.data.entity.Genre;
import com.popularmovies.vpaliy.data.entity.Movie;
import com.popularmovies.vpaliy.data.entity.MovieDetailEntity;
import com.popularmovies.vpaliy.data.entity.ReviewEntity;
import com.popularmovies.vpaliy.data.entity.TrailerEntity;
import com.popularmovies.vpaliy.data.entity.TvShow;
import com.popularmovies.vpaliy.data.entity.TvShowDetailEntity;
import com.popularmovies.vpaliy.data.entity.TvShowEpisodeEntity;
import com.popularmovies.vpaliy.data.entity.TvShowInfoEntity;
import com.popularmovies.vpaliy.data.entity.TvShowSeasonEntity;
import com.popularmovies.vpaliy.domain.model.ActorCover;
import com.popularmovies.vpaliy.domain.model.MediaCover;
import com.popularmovies.vpaliy.domain.model.TVShowEpisode;
import com.popularmovies.vpaliy.domain.model.TVShowSeason;

import java.util.Arrays;
import java.util.List;


public class FakeDataProvider {

    public static final int FAKE_ID=123;
    public static final int FAKER_ID=1234;
    public static final int FAKE_RUNTIME=120;
    public static final int FAKE_VOTE_COUNT=1000;
    public static final int FAKE_EPISODE_NUMBER=12;
    public static final int FAKE_NUMBER_OF_SEASONS=14;
    public static final int FAKE_NUMBER_OF_EPISODES=14;
    public static final int FAKE_SEASON_NUMBER=16;
    public static final long FAKE_BUDGET=1000000;
    public static final long FAKE_REVENUE=100000;
    public static final double FAKE_POPULARITY=10;
    public static final double FAKE_VOTE_AVERAGE=7.5d;
    public static final String FAKE_REVIEW_ID="fake_review_id";
    public static final String FAKE_ROLE="fake_role";
    public static final String FAKE_AVATAR="fake_avatar";
    public static final String FAKE_NAME="fake_name";
    public static final String FAKE_TITLE="fake_title";
    public static final String FAKE_STATUS="fake_status";
    public static final String FAKE_OVERVIEW="fake_overview";
    public static final String FAKE_POSTER_PATH="fake_poster_path";
    public static final String FAKE_BACKDROP_PATH="fake_backdrop_path";
    public static final String FAKE_HOMEPAGE="fake_home_page";
    public static final String FAKE_RELEASE_DATE="fake_date";
    public static final String FAKE_ORIGINAL_TITLE="fake_original_title";
    public static final String FAKE_ORIGINAL_LANGUAGE="fake_language";
    public static final String FAKE_TRAILER_URL="fake_trailer_url";
    public static final String FAKE_SITE="fake_site";
    public static final String FAKE_AUTHOR="fake_author";
    public static final String FAKE_CONTENT="fake_content";
    public static final String FAKE_REVIEW_URL="fake_review_url";
    public static final String FAKE_FIRST_AIR_DATE="fake_first_air_date";
    public static final String FAKE_LAST_AIR_DATE="fake_last_air_date";
    public static final String FAKE_STILL_PATH="fake_still_path";
    public static final String FAKE_TYPE="fake_type";
    public static final String FAKE_DURATION="fake_duration";

    public static ActorEntity provideActorEntity(){
        ActorEntity actorEntity=new ActorEntity();
        actorEntity.setMovieId(FAKER_ID);
        actorEntity.setActorId(FAKE_ID);
        actorEntity.setRole(FAKE_ROLE);
        actorEntity.setActorAvatar(FAKE_AVATAR);
        actorEntity.setName(FAKE_NAME);
        return actorEntity;
    }

    public static ActorCover provideActorCover(){
        ActorCover actorCover=new ActorCover(FAKE_ID,FAKER_ID);
        actorCover.setRole(FAKE_ROLE);
        actorCover.setActorAvatar(FAKE_AVATAR);
        actorCover.setName(FAKE_NAME);
        return actorCover;
    }

    public static List<String> provideBackdropStrings(){
        return Arrays.asList(FAKE_BACKDROP_PATH,FAKE_BACKDROP_PATH,FAKE_BACKDROP_PATH,
                FAKE_BACKDROP_PATH,FAKE_BACKDROP_PATH,FAKE_BACKDROP_PATH);
    }

    public static List<String> provideGenreStrings(){
        return Arrays.asList(FAKE_NAME,FAKE_NAME,FAKE_NAME,FAKE_NAME);
    }

    public static MediaCover provideMediaCover(boolean isTvShow){
        MediaCover mediaCover=new MediaCover();
        mediaCover.setBackdrops(provideBackdropStrings());
        mediaCover.setTvShow(isTvShow);
        mediaCover.setPosterPath(FAKE_POSTER_PATH);
        mediaCover.setMovieTitle(FAKE_TITLE);
        mediaCover.setAverageRate(FAKE_VOTE_AVERAGE);
        mediaCover.setPosterPath(FAKE_POSTER_PATH);
        mediaCover.setDuration(FAKE_DURATION);
        mediaCover.setGenres(provideGenreStrings());
        mediaCover.setMediaId(FAKE_ID);
        mediaCover.setReleaseDate(FAKE_RELEASE_DATE);
        return mediaCover;
    }

    public static List<MediaCover> provideMediaCoverList(boolean isTvShow){
        return Arrays.asList(provideMediaCover(isTvShow),provideMediaCover(isTvShow),
                provideMediaCover(isTvShow),provideMediaCover(isTvShow),provideMediaCover(isTvShow));
    }

    public static TVShowEpisode provideTvShowEpisode(){
        TVShowEpisode episode=new TVShowEpisode();
        episode.setVoteAverage(FAKE_VOTE_AVERAGE);
        episode.setEpisodeOverview(FAKE_OVERVIEW);
        episode.setEpisodeNumber(FAKE_EPISODE_NUMBER);
        episode.setEpisodeName(FAKE_NAME);
        episode.setEpisodeId(FAKE_ID);
        episode.setVoteCount(FAKE_VOTE_COUNT);
        return episode;
    }

    public static List<TVShowEpisode> provideTvShowEpisodeList(){
        return Arrays.asList(provideTvShowEpisode(),provideTvShowEpisode(),
                provideTvShowEpisode(),provideTvShowEpisode(),provideTvShowEpisode());
    }

    public static TvShowEpisodeEntity provideTvEpisodeEntity(){
        TvShowEpisodeEntity entity=new TvShowEpisodeEntity();
        entity.setOverview(FAKE_OVERVIEW);
        entity.setVoteAverage(FAKE_VOTE_AVERAGE);
        entity.setVoteCount(FAKE_VOTE_COUNT);
        entity.setName(FAKE_NAME);
        entity.setEpisodeNumber(FAKE_EPISODE_NUMBER);
        entity.setAirDate(FAKE_FIRST_AIR_DATE);
        entity.setId(FAKE_ID);
        entity.setStillPath(FAKE_STILL_PATH);
        return entity;
    }

    public static TVShowSeason provideTvShowSeason(){
        TVShowSeason season=new TVShowSeason();
        season.setSeasonName(FAKE_NAME);
        season.setSeasonId(FAKE_ID);
        season.setAirDate(FAKE_FIRST_AIR_DATE);
        season.setPosterPath(FAKE_POSTER_PATH);
        season.setSeasonNumber(FAKE_SEASON_NUMBER);
        season.setEpisodeList(provideTvShowEpisodeList());
        return season;
    }
    public static TvShowSeasonEntity provideTvShowSeasonEntity(){
        TvShowSeasonEntity entity=new TvShowSeasonEntity();
        entity.setId(FAKE_ID);
        entity.setEpisodes(provideTvShowEpisodeEntityList());
        entity.setSeasonNumber(FAKE_SEASON_NUMBER);
        entity.setName(FAKE_NAME);
        entity.setPosterPath(FAKE_POSTER_PATH);
        entity.setOverview(FAKE_OVERVIEW);
        entity.setAirDate(FAKE_FIRST_AIR_DATE);
        return entity;
    }

    public static List<TvShowSeasonEntity> provideTvShowSeasonEntityList(){
        return Arrays.asList(provideTvShowSeasonEntity(),provideTvShowSeasonEntity(),provideTvShowSeasonEntity(),
                    provideTvShowSeasonEntity(),provideTvShowSeasonEntity(),provideTvShowSeasonEntity());
    }

    public static TvShowInfoEntity provideTvShowInfoEntity(){
        TvShowInfoEntity entity=new TvShowInfoEntity();
        entity.setStatus(FAKE_STATUS);
        entity.setOriginalLanguage(FAKE_ORIGINAL_LANGUAGE);
        entity.setNumberOfSeasons(FAKE_NUMBER_OF_SEASONS);
        entity.setNumberOfEpisodes(FAKE_NUMBER_OF_EPISODES);
        entity.setBackdrop_path(FAKE_BACKDROP_PATH);
        entity.setFirstAirDate(FAKE_FIRST_AIR_DATE);
        entity.setLastAirDate(FAKE_LAST_AIR_DATE);
        entity.setGenreList(provideGenreList());
        entity.setHomepage(FAKE_HOMEPAGE);
        entity.setOriginalName(FAKE_ORIGINAL_TITLE);
        entity.setVoteCount(FAKE_VOTE_COUNT);
        entity.setVoteAverage(FAKE_VOTE_AVERAGE);
        entity.setType(FAKE_TYPE);
        entity.setTvShowId(FAKE_ID);
        entity.setPosterPath(FAKE_POSTER_PATH);
        entity.setPopularity(FAKE_POPULARITY);
        entity.setSeasonEntities(provideTvShowSeasonEntityList());
        return entity;
    }

    public static List<TvShowEpisodeEntity> provideTvShowEpisodeEntityList(){
        return Arrays.asList(provideTvEpisodeEntity(),provideTvEpisodeEntity(),provideTvEpisodeEntity(),
                    provideTvEpisodeEntity(),provideTvEpisodeEntity(),provideTvEpisodeEntity());
    }

    public static TvShowDetailEntity provideTvShowDetailsEntity(){
        TvShowDetailEntity detailEntity=new TvShowDetailEntity();
        detailEntity.setSeasons(provideTvShowSeasonEntityList());
        detailEntity.setTvShowCover(provideTvShowEntity());
        detailEntity.setCast(provideActorEntityList());
        detailEntity.setInfoEntity(provideTvShowInfoEntity());
        return detailEntity;
    }

    public static TvShow provideTvShowEntity(){
        TvShow tvShow=new TvShow();
        tvShow.setId(FAKE_ID);
        tvShow.setBackdrops(provideBackdrops());
        tvShow.setPopularity(FAKE_POPULARITY);
        tvShow.setOverview(FAKE_OVERVIEW);
        tvShow.setName(FAKE_NAME);
        tvShow.setBackdropPath(FAKE_BACKDROP_PATH);
        tvShow.setFirstAirDate(FAKE_FIRST_AIR_DATE);
        tvShow.setGenreList(provideGenreList());
        tvShow.setOriginalName(FAKE_ORIGINAL_TITLE);
        tvShow.setPosterPath(FAKE_POSTER_PATH);
        tvShow.setVoteCount(FAKE_VOTE_COUNT);
        tvShow.setVoteAverage(FAKE_VOTE_AVERAGE);
        return tvShow;
    }

    public static List<ActorEntity> provideActorEntityList(){
        return Arrays.asList(provideActorEntity(),provideActorEntity(),
                    provideActorEntity(),provideActorEntity(),provideActorEntity());
    }

    public static BackdropImage provideBackdropImage(){
        return new BackdropImage(FAKE_BACKDROP_PATH);
    }

    public static List<BackdropImage> provideBackdrops(){
        return Arrays.asList(provideBackdropImage(),provideBackdropImage(),
                provideBackdropImage(),provideBackdropImage());
    }


    public static Genre provideGenre(){
        Genre genre=new Genre();
        genre.setId(FAKE_ID);
        genre.setName(FAKE_NAME);
        return genre;
    }

    public static List<Genre> provideGenreList(){
        return Arrays.asList(provideGenre(),provideGenre(),provideGenre(),
                provideGenre(),provideGenre(),provideGenre());
    }

    public static Movie provideMovieEntity(){
        Movie movie=new Movie();
        movie.setPosterPath(FAKE_POSTER_PATH);
        movie.setMovieId(FAKE_ID);
        movie.setReleaseDate(FAKE_RELEASE_DATE);
        movie.setTitle(FAKE_TITLE);
        movie.setOriginalTitle(FAKE_ORIGINAL_TITLE);
        movie.setStatus(FAKE_STATUS);
        movie.setBudget(FAKE_BUDGET);
        movie.setRuntime(FAKE_RUNTIME);
        movie.setVoteCount(FAKE_VOTE_COUNT);
        movie.setVoteAverage(FAKE_VOTE_AVERAGE);
        movie.setOverview(FAKE_OVERVIEW);
        movie.setBackdropPath(FAKE_BACKDROP_PATH);
        movie.setHomepage(FAKE_HOMEPAGE);
        movie.setOriginalLanguage(FAKE_ORIGINAL_LANGUAGE);
        movie.setRevenue(FAKE_REVENUE);
        movie.setPopularity(FAKE_POPULARITY);
        movie.setBackdropImages(provideBackdrops());
        movie.setGenres(provideGenreList());
        return movie;
    }

    public static List<Movie> provideMovieList(){
        return Arrays.asList(provideMovieEntity(),provideMovieEntity(),
                provideMovieEntity(),provideMovieEntity(),provideMovieEntity());
    }

    public static TrailerEntity provideTrailerEntity(){
        TrailerEntity trailerEntity=new TrailerEntity();
        trailerEntity.setMovieId(FAKER_ID);
        trailerEntity.setTrailerUrl(FAKE_TRAILER_URL);
        trailerEntity.setSite(FAKE_SITE);
        trailerEntity.setTrailerId(FAKE_ID);
        trailerEntity.setTrailerTitle(FAKE_TITLE);
        return trailerEntity;
    }

    public static ReviewEntity provideReviewEntity(){
        ReviewEntity reviewEntity=new ReviewEntity();
        reviewEntity.setReviewId(FAKE_REVIEW_ID);
        reviewEntity.setAuthor(FAKE_AUTHOR);
        reviewEntity.setContent(FAKE_CONTENT);
        reviewEntity.setUrl(FAKE_REVIEW_URL);
        return reviewEntity;
    }

    public static List<ReviewEntity> provideReviewEntityList() {
        return Arrays.asList(provideReviewEntity(),provideReviewEntity(),provideReviewEntity(),
                provideReviewEntity(),provideReviewEntity(),provideReviewEntity());
    }

    public static List<TrailerEntity> provideTrailerEntityList(){
        return Arrays.asList(provideTrailerEntity(),provideTrailerEntity(),
                provideTrailerEntity(),provideTrailerEntity(),provideTrailerEntity());
    }

    public static MovieDetailEntity provideMovieDetailsEntity(){
        MovieDetailEntity detailEntity=new MovieDetailEntity();
        detailEntity.setGenres(provideGenreList());
        detailEntity.setBackdropImages(provideBackdrops());
        detailEntity.setMovie(provideMovieEntity());
        detailEntity.setSimilarMovies(provideMovieList());
        detailEntity.setCast(provideActorEntityList());
        detailEntity.setTrailers(provideTrailerEntityList());
        detailEntity.setReviews(provideReviewEntityList());
        return detailEntity;
    }
}
