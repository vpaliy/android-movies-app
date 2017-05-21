package com.popularmovies.vpaliy.data.mapper;


import android.support.annotation.NonNull;

import com.popularmovies.vpaliy.data.configuration.ImageQualityConfiguration;
import com.popularmovies.vpaliy.data.entity.ActorEntity;
import com.popularmovies.vpaliy.data.entity.BackdropImage;
import com.popularmovies.vpaliy.data.entity.Genre;
import com.popularmovies.vpaliy.data.entity.Network;
import com.popularmovies.vpaliy.data.entity.TvShow;
import com.popularmovies.vpaliy.data.entity.TvShowDetailEntity;
import com.popularmovies.vpaliy.data.entity.TvShowSeasonEntity;
import com.popularmovies.vpaliy.domain.model.ActorCover;
import com.popularmovies.vpaliy.domain.model.MediaCover;
import com.popularmovies.vpaliy.domain.model.TVShowDetails;
import com.popularmovies.vpaliy.domain.model.TVShowInfo;
import com.popularmovies.vpaliy.domain.model.TVShowSeason;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class TvShowDetailsMapper implements Mapper<TVShowDetails,TvShowDetailEntity> {

    private final Mapper<TVShowSeason,TvShowSeasonEntity> seasonMapper;
    private final Mapper<ActorCover,ActorEntity> actorMapper;
    private ImageQualityConfiguration qualityConfiguration;

    @Inject
    public TvShowDetailsMapper(@NonNull Mapper<TVShowSeason,TvShowSeasonEntity> seasonMapper,
                               @NonNull Mapper<ActorCover,ActorEntity> actorMapper,
                               @NonNull ImageQualityConfiguration qualityConfiguration){
        this.seasonMapper=seasonMapper;
        this.actorMapper=actorMapper;
        this.qualityConfiguration=qualityConfiguration;
    }

    @Override
    public TVShowDetails map(TvShowDetailEntity tvShowDetailEntity) {
        if(tvShowDetailEntity==null) return null;
        TVShowDetails details=new TVShowDetails();
        details.setCast(actorMapper.map(tvShowDetailEntity.getCast()));
        details.setSeasons(seasonMapper.map(tvShowDetailEntity.getSeasons()));
        //TODO set duration
        MediaCover cover=new MediaCover();
        cover.setMediaId(tvShowDetailEntity.getTvShowId());
        cover.setAverageRate(tvShowDetailEntity.getVoteAverage().doubleValue());
        cover.setBackdrops(BackdropImage.convert(tvShowDetailEntity.getBackdropImages(),qualityConfiguration));
        cover.setGenres(Genre.convert(tvShowDetailEntity.getGenres()));
        cover.setMovieTitle(tvShowDetailEntity.getName());
        cover.setPosterPath(tvShowDetailEntity.getPosterPath());
        if(tvShowDetailEntity.getFirstAirDate()!=null) {
            cover.setReleaseYear(Integer.parseInt(tvShowDetailEntity.getFirstAirDate().substring(0,4)));
        }
        cover.setTvShow(true);
        //
        details.setTvShowCover(cover);
        TVShowInfo info=new TVShowInfo();
        info.setAverageRate(tvShowDetailEntity.getVoteAverage().doubleValue());
        info.setEpisodeRuntime(tvShowDetailEntity.getEpisodeRuntime());
        info.setFirstAirDate(tvShowDetailEntity.getFirstAirDate());
        info.setLastAirDate(tvShowDetailEntity.getLastAirDate());
        info.setName(tvShowDetailEntity.getName());
        info.setNetworks(Network.convertToString(tvShowDetailEntity.getNetworks()));
        info.setNumberOfEpisodes(tvShowDetailEntity.getNumberOfEpisodes());
        info.setNumberOfSeasons(tvShowDetailEntity.getNumberOfSeasons());
        info.setOriginalLanguage(tvShowDetailEntity.getOriginalLanguage());
        info.setOverview(tvShowDetailEntity.getOverview());
        info.setStatus(tvShowDetailEntity.getStatus());
        info.setVoteCount(tvShowDetailEntity.getVoteCount());
        info.setPopularity(tvShowDetailEntity.getPopularity().doubleValue());
        info.setTvShowId(tvShowDetailEntity.getTvShowId());
        //
        details.setTvShowInfo(info);
        return null;
    }

    @Override
    public TvShowDetailEntity reverseMap(TVShowDetails tvShowDetails) {
        if(tvShowDetails==null) return null;
        TvShowDetailEntity entity=new TvShowDetailEntity();
        TVShowInfo info=tvShowDetails.getTvShowInfo();
        //
        entity.setTvShowId(tvShowDetails.getTvShowId());
        entity.setVoteCount(info.getVoteCount());
        entity.setStatus(info.getStatus());
        if(tvShowDetails.getCast()!=null){
            List<ActorEntity> actorEntities=new ArrayList<>(tvShowDetails.getCast().size());
            tvShowDetails.getCast().forEach(actor->actorEntities.add(actorMapper.reverseMap(actor)));
            entity.setCast(actorEntities);
        }
        entity.setEpisodeRuntime(info.getEpisodeRuntime());
        entity.setFirstAirDate(info.getFirstAirDate());
        entity.setLastAirDate(info.getLastAirDate());
        entity.setNumberOfEpisodes(info.getNumberOfEpisodes());
        entity.setNumberOfSeasons(info.getNumberOfSeasons());
        entity.setNetworks(Network.convertToNetworks(info.getNetworks()));
        entity.setOverview(info.getOverview());
        entity.setOriginalLanguage(info.getOriginalLanguage());
        entity.setPopularity(info.getPopularity());
        entity.setVoteAverage(info.getAverageRate());
        //
        MediaCover cover=tvShowDetails.getTvShowCover();
        entity.setPosterPath(cover.getPosterPath());
        entity.setGenres(Genre.convertToGenres(cover.getGenres()));
        entity.setBackdropImages(BackdropImage.convertToBackdrops(cover.getGenres(),qualityConfiguration));
        if(tvShowDetails.getSeasons()!=null){
            List<TvShowSeasonEntity> seasonEntities=new ArrayList<>(tvShowDetails.getSeasons().size());
            tvShowDetails.getSeasons().forEach(season->seasonEntities.add(seasonMapper.reverseMap(season)));
            entity.setSeasons(seasonEntities);
        }
        return entity;
    }

    @Override
    public List<TVShowDetails> map(List<TvShowDetailEntity> from) {
        if(from==null) return null;
        List<TVShowDetails> details=new ArrayList<>(from.size());
        from.forEach(tvShowDetailEntity -> details.add(map(tvShowDetailEntity)));
        return details;
    }
}
