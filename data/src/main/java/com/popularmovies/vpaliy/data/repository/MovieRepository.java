package com.popularmovies.vpaliy.data.repository;

import android.support.annotation.NonNull;
import android.util.Log;

import com.popularmovies.vpaliy.data.entity.MovieDetailEntity;
import com.popularmovies.vpaliy.data.entity.MovieEntity;
import com.popularmovies.vpaliy.data.mapper.Mapper;
import com.popularmovies.vpaliy.data.source.DataSource;
import com.popularmovies.vpaliy.domain.IRepository;
import com.popularmovies.vpaliy.domain.ISortConfiguration;
import com.popularmovies.vpaliy.domain.model.MovieCover;
import com.popularmovies.vpaliy.domain.model.MovieDetails;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

@Singleton
public class MovieRepository implements IRepository<MovieCover,MovieDetails> {

    private static final String TAG=MovieRepository.class.getSimpleName();

    private final DataSource<MovieEntity, MovieDetailEntity> dataSource;
    private final Mapper<MovieCover, MovieEntity> entityMapper;
    private final Mapper<MovieDetails, MovieDetailEntity> detailsMapper;
    private final ISortConfiguration sortConfiguration;


    @Inject
    public MovieRepository(@NonNull DataSource<MovieEntity, MovieDetailEntity> dataSource,
                      @NonNull Mapper<MovieCover, MovieEntity> entityMapper,
                      @NonNull Mapper<MovieDetails, MovieDetailEntity> detailsMapper,
                      @NonNull ISortConfiguration sortConfiguration) {
        Log.d(TAG,"Created");
        this.dataSource = dataSource;
        this.entityMapper = entityMapper;
        this.detailsMapper = detailsMapper;
        this.sortConfiguration = sortConfiguration;
    }

    @Override
    public Observable<List<MovieCover>> getCovers() {
        Log.d(TAG,"getCovers()");
        return dataSource.getCovers()
                .map(entityMapper::map);
    }

    @Override
    public Observable<MovieDetails> getDetails(int ID) {
        return dataSource.getDetails(ID)
                .map(detailsMapper::map);
    }

    @Override
    public Observable<MovieCover> getCover(int ID) {
        return dataSource.getCover(ID)
                .map(entityMapper::map);
    }

    private Observable<List<MovieCover>> queryRemoteSource() {
       /* return dataSource.getList()
                .map(mapper::map)
                .doOnNext(data->inMemoryCache=new ArrayList<>(data));   */
        return null;
    }

    @Override
    public void sort(@NonNull ISortConfiguration.SortType type) {
        sortConfiguration.saveConfiguration(type);
       /* switch (type){
            case POPULAR:
                Collections.sort(inMemoryCache,(o1, o2) -> o1.getAverageVote()<o2.getAverageVote()?1:
                        (o1.getAverageVote()!=o2.getAverageVote()?-1:0));
                break;
            case LATEST:
                Collections.sort(inMemoryCache,(o1, o2) -> o1.getReleaseDate().after(o2.getReleaseDate())?1:
                        (o1.getReleaseDate().before(o2.getReleaseDate())?-1:0));
                break;

        }   */
    }
}