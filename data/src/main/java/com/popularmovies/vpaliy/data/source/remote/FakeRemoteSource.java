package com.popularmovies.vpaliy.data.source.remote;

import android.content.Context;
import android.support.annotation.NonNull;

import com.popularmovies.vpaliy.data.R;
import com.popularmovies.vpaliy.data.entity.MovieDetailEntity;
import com.popularmovies.vpaliy.data.entity.MovieEntity;
import com.popularmovies.vpaliy.data.entity.MovieInfoEntity;
import com.popularmovies.vpaliy.data.source.DataSource;
import com.popularmovies.vpaliy.domain.ISortConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 *  A fake data source
 */

@Singleton
public class FakeRemoteSource extends DataSource<MovieEntity,MovieDetailEntity> {

    private ISortConfiguration sortConfiguration;
    private Context context;


    private List<MovieEntity> list;

    @Inject
    public FakeRemoteSource(@NonNull Context context, @NonNull ISortConfiguration sortConfiguration){
        this.sortConfiguration=sortConfiguration;
        this.context=context;
    }

    @Override
    public Observable<List<MovieEntity>> getCovers() {
        list=new ArrayList<>(50);
        for(int index=0;index<list.size();index++) {
            MovieEntity entity = new MovieEntity();
            entity.setGenres(Arrays.asList("Action", "Drama", "Comedy"));
            entity.setMovieId(index);
            entity.setMovieTitle("Title");
            entity.setReleaseYear(2016);
            list.add(entity);
        }
        return Observable.just(list);
    }

    @Override
    public Observable<MovieDetailEntity> getDetails(int ID) {
        MovieDetailEntity entity=new MovieDetailEntity();
        MovieInfoEntity infoEntity=new MovieInfoEntity(ID,null);
        infoEntity.setMovieId(ID);
        //infoEntity.setAvarageRate(10);
        infoEntity.setBudget("$100000");
        infoEntity.setDirector("Vasyl Paliy");
        infoEntity.setRevenue("$10000000");
       // infoEntity.setDescription(context.getString(R.string.content));
        entity.setMovieInfo(infoEntity);
        entity.setMovieId(ID);
        return Observable.just(entity);
    }

    @Override
    public Observable<MovieEntity> getCover(int ID) {
        return Observable.just(list.get(ID));
    }

    @Override
    public void sort(@NonNull ISortConfiguration.SortType type) {

    }
}
