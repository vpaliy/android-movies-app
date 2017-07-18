package com.popularmovies.vpaliy.popularmoviesapp.ui.details;

import com.popularmovies.vpaliy.data.utils.scheduler.BaseSchedulerProvider;
import com.popularmovies.vpaliy.domain.model.MediaCover;
import com.popularmovies.vpaliy.domain.model.MovieDetails;
import com.popularmovies.vpaliy.domain.repository.ICoverRepository;
import com.popularmovies.vpaliy.domain.repository.IDetailsRepository;
import com.popularmovies.vpaliy.popularmoviesapp.di.scope.ViewScope;
import javax.inject.Inject;
import android.support.annotation.NonNull;
import com.popularmovies.vpaliy.data.source.qualifier.Movies;

@ViewScope
public class MovieDetailsPresenter extends DetailsPresenter<MovieDetails> {

    @Inject
    public MovieDetailsPresenter(@NonNull IDetailsRepository<MovieDetails> repository,
                                 @NonNull @Movies ICoverRepository<MediaCover> iCoverRepository,
                                 @NonNull BaseSchedulerProvider schedulerProvider){
        super(repository,iCoverRepository,schedulerProvider);
    }
    public void processData(@NonNull MovieDetails details){
        MediaCover cover=details.getMovieCover();
        if(cover.getBackdrops()!=null){
            view.showBackdrops(cover.getBackdrops());
        }

        if(!isEmpty(details.getCast())){
            view.showCast(details.getCast());
        }

        if(details.getCollection()!=null &&
                !isEmpty(details.getCollection().getCovers())){
            view.showCollection(details.getCollection());
        }

        if(!isEmpty(details.getSimilarMovies())){
            view.showSimilar(details.getSimilarMovies());
        }

        if(!isEmpty(details.getTrailers())){
            view.showTrailers(details.getTrailers());
        }

        view.showDescription(details.getMovieInfo().getDescription());
    }
}
