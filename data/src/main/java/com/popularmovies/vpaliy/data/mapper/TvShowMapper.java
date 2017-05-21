package com.popularmovies.vpaliy.data.mapper;


import com.popularmovies.vpaliy.data.entity.TvShow;
import com.popularmovies.vpaliy.domain.model.MediaCover;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TvShowMapper implements Mapper<MediaCover,TvShow> {

    @Inject
    public TvShowMapper(){}

    @Override
    public MediaCover map(TvShow tvShow) {
        MediaCover cover=new MediaCover();
        cover.setMediaId(tvShow.getId());
        cover.setAverageRate(tvShow.getVoteAverage().doubleValue());
       // cover.setBackdrops(BackdropImage.convert(tvShow.getBackdropImages(),qualityConfiguration));
//        cover.setGenres(Genre.convert(tvShow.getGenres()));
        cover.setMovieTitle(tvShow.getName());
        cover.setPosterPath(tvShow.getPosterPath());
        if(tvShow.getFirstAirDate()!=null) {
            cover.setReleaseYear(Integer.parseInt(tvShow.getFirstAirDate().substring(0,4)));
        }

        cover.setTvShow(true);
        return null;
    }

    @Override
    public TvShow reverseMap(MediaCover mediaCover) {
        TvShow tvShow=new TvShow();
        tvShow.setName(mediaCover.getMovieTitle());
        tvShow.setVoteAverage(mediaCover.getAverageRate());
        tvShow.setId(mediaCover.getMediaId());
        //TODO fix this
        tvShow.setFirstAirDate(Integer.toString(mediaCover.getReleaseYear()));
        tvShow.setPosterPath(mediaCover.getPosterPath());
        return tvShow;
    }

    @Override
    public List<MediaCover> map(List<TvShow> from) {
        if(from==null) return null;
        List<MediaCover> result=new ArrayList<>(from.size());
        from.forEach(tvShow -> result.add(map(tvShow)));
        return result;
    }

}
