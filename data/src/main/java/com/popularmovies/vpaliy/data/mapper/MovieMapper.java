package com.popularmovies.vpaliy.data.mapper;

import com.popularmovies.vpaliy.data.configuration.ImageQualityConfiguration;
import com.popularmovies.vpaliy.data.entity.BackdropImage;
import com.popularmovies.vpaliy.data.entity.Genre;
import com.popularmovies.vpaliy.data.entity.Movie;
import com.popularmovies.vpaliy.domain.model.MovieCover;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class MovieMapper implements Mapper<MovieCover,Movie> {

    private final ImageQualityConfiguration qualityConfiguration;

    @Inject
    public MovieMapper(ImageQualityConfiguration qualityConfiguration){
        this.qualityConfiguration=qualityConfiguration;
    }

    @Override
    public MovieCover map(Movie movieEntity) {
        MovieCover cover=new MovieCover();
        cover.setMovieId(movieEntity.getMovieId());
        cover.setPosterPath(qualityConfiguration.convertCover(movieEntity.getPosterPath()));
        cover.setGenres(Genre.convert(movieEntity.getGenres()));
        cover.setMovieTitle(movieEntity.getTitle());
        cover.setAverageRate(movieEntity.getVoteAverage());
        cover.setFavorite(movieEntity.isFavorite());
        List<BackdropImage> backdropImages=movieEntity.getBackdropImages();
        if(backdropImages==null){
            if(movieEntity.getBackdrop_path()!=null) {
                cover.setBackdrops(Collections.singletonList(movieEntity.getBackdrop_path()));
            }
        }else {
            cover.setBackdrops(BackdropImage.convert(backdropImages, qualityConfiguration));
        }

        if (movieEntity.getReleaseDate() != null){
            cover.setReleaseYear(Integer.parseInt(movieEntity.getReleaseDate().substring(0,4)));
        }

        if(movieEntity.getRuntime()>0) {
            int hours=movieEntity.getRuntime()/60;
            String duration="";
            if(hours>0) {
                duration = Integer.toString(hours)+" hr"+(hours>1?"s ":" ");
            }
            int minRemainder=movieEntity.getRuntime() % 60;
            if(minRemainder!=0){
                duration+=Integer.toString(minRemainder)+" min";
            }
            cover.setDuration(duration);
        }
        return cover;
    }

    @Override
    public List<MovieCover> map(List<Movie> from) {
        if(from!=null) {
            List<MovieCover> coverList = new ArrayList<>(from.size());
            for (int index = 0; index < from.size(); index++) {
                coverList.add(map(from.get(index)));
            }
            return coverList;
        }
        return null;
    }

    @Override
    public Movie reverseMap(MovieCover movieCover) {
        Movie result=new Movie();
        result.setMovieId(movieCover.getMovieId());
        result.setPosterPath(qualityConfiguration.extractPath(movieCover.getPosterPath()));
        result.setGenres(Genre.convertToGenres(movieCover.getGenres()));
        result.setTitle(movieCover.getMovieTitle());
        result.setVoteAverage(movieCover.getAverageRate());
        result.setFavorite(movieCover.isFavorite());
        result.setBackdropImages(BackdropImage.convertToBackdrops(movieCover.getBackdrops(), qualityConfiguration));
        result.setReleaseDate(Integer.toString(movieCover.getReleaseYear()));
        return result;
    }
}
