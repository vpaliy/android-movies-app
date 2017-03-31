package com.popularmovies.vpaliy.popularmoviesapp.data.source.remote;

import android.support.annotation.NonNull;

import com.popularmovies.vpaliy.popularmoviesapp.data.entity.MovieEntity;
import com.popularmovies.vpaliy.popularmoviesapp.data.source.DataSource;
import com.popularmovies.vpaliy.popularmoviesapp.domain.ISortConfiguration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 *  A fake data source
 */

@Singleton
public class FakeRemoteSource extends DataSource<MovieEntity> {

    private ISortConfiguration sortConfiguration;

    private List<MovieEntity> list;

    @Inject
    public FakeRemoteSource(@NonNull ISortConfiguration sortConfiguration){
        this.sortConfiguration=sortConfiguration;
    }

    @Override
    public Observable<List<MovieEntity>> getList() {
        list=new ArrayList<>(50);
        Random random=new Random();
        for(int index=0;index<50;index++) {
            MovieEntity entity = new MovieEntity();
            entity.setID(index);
            entity.setPosterPath(null);
            entity.setOriginalTitle("Title");
            entity.setPlot("Plot");
            entity.setAverageVote(random.nextInt(10));
            long ms = -946771200000L + (Math.abs(random.nextLong()) % (70L * 365 * 24 * 60 * 60 * 1000));
            Date date = new Date(ms);
            entity.setReleaseDate(date);
            entity.setUserRatings("User ratings");
            list.add(entity);

        }
        sort(sortConfiguration.getConfiguration());
        return Observable.just(list);
    }

    @Override
    public Observable<MovieEntity> findById(int ID) {
        return Observable.just(list.get(ID));
    }

    @Override
    public void sort(@NonNull ISortConfiguration.SortType type) {
        switch (type){
            case LATEST:
                Collections.sort(list,(o1, o2) -> o1.getReleaseDate().after(o2.getReleaseDate())?1:
                        (o1.getReleaseDate().before(o2.getReleaseDate())?-1:0));
                break;
            case POPULAR:
                Collections.sort(list,(o1, o2) -> o1.getAverageVote()<o2.getAverageVote()?1:
                        (o1.getAverageVote()!=o2.getAverageVote()?-1:0));
                break;

        }
    }
}
