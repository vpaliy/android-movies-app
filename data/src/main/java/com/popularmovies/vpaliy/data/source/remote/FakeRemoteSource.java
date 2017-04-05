package com.popularmovies.vpaliy.data.source.remote;

import android.content.Context;
import android.util.Log;

import com.popularmovies.vpaliy.data.R;
import com.popularmovies.vpaliy.data.entity.ActorEntity;
import com.popularmovies.vpaliy.data.entity.MovieDetailEntity;
import com.popularmovies.vpaliy.data.entity.MovieEntity;
import com.popularmovies.vpaliy.data.entity.MovieInfoEntity;
import com.popularmovies.vpaliy.data.source.DataSource;
import com.popularmovies.vpaliy.domain.ISortConfiguration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import android.support.annotation.NonNull;
import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 *  A fake data source
 */

@Singleton
public class FakeRemoteSource extends DataSource<MovieEntity,MovieDetailEntity> {

    private static final String TAG=FakeRemoteSource.class.getSimpleName();

    private ISortConfiguration sortConfiguration;
    private Context context;


    private List<MovieEntity> list;

    @Inject
    public FakeRemoteSource(@NonNull Context context, @NonNull ISortConfiguration sortConfiguration){
        Log.d(TAG,"Created");
        this.sortConfiguration=sortConfiguration;
        this.context=context;
    }

    @Override
    public Observable<List<MovieEntity>> getCovers() {
        Log.d(TAG,"getCovers()");
        list=new ArrayList<>(50);
        for(int index=0;index<50;index++) {
            MovieEntity entity = new MovieEntity();
            entity.setGenres(Arrays.asList("Action", "Drama", "Comedy"));
            entity.setMovieId(index);
            entity.setMovieTitle("Title");
            entity.setReleaseYear(2016);
            entity.setDuration("2 hrs 16 min");
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
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY,17);
        cal.set(Calendar.MINUTE,30);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MILLISECOND,0);

        infoEntity.setReleaseDate(cal.getTime());
        infoEntity.setBudget("$100000");
        infoEntity.setDirector("Vasyl Paliy");
        infoEntity.setRevenue("$10000000");
        infoEntity.setDescription(context.getString(R.string.content));
        entity.setCast(getFakeActors(ID));
        entity.setMovieInfo(infoEntity);
        entity.setMovieId(ID);

        return Observable.just(entity);
    }

    private List<ActorEntity> getFakeActors(int movieID){
        List<ActorEntity> list=new ArrayList<>(50);
        for(int index=0;index<50;index++) {
            ActorEntity entity = new ActorEntity(index, movieID);
            entity.setFirstName("Johnny");
            entity.setLastName("Depp");
            entity.setRole("Jack Sparrow");
            list.add(entity);
        }
        return list;
    }

    @Override
    public Observable<MovieEntity> getCover(int ID) {
        return Observable.just(list.get(ID));
    }

    @Override
    public void sort(@NonNull ISortConfiguration.SortType type) {

    }
}
