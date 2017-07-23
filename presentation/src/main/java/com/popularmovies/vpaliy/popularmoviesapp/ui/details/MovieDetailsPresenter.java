package com.popularmovies.vpaliy.popularmoviesapp.ui.details;

import com.popularmovies.vpaliy.data.utils.scheduler.BaseSchedulerProvider;
import com.popularmovies.vpaliy.domain.model.ActorCover;
import com.popularmovies.vpaliy.domain.model.MediaCollection;
import com.popularmovies.vpaliy.domain.model.MediaCover;
import com.popularmovies.vpaliy.domain.model.MovieDetails;
import com.popularmovies.vpaliy.domain.model.MovieInfo;
import com.popularmovies.vpaliy.domain.model.Trailer;
import com.popularmovies.vpaliy.domain.repository.ICoverRepository;
import com.popularmovies.vpaliy.domain.repository.IDetailsRepository;
import com.popularmovies.vpaliy.popularmoviesapp.di.scope.ViewScope;
import javax.inject.Inject;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.popularmovies.vpaliy.data.source.qualifier.Movies;

import java.util.List;

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
        showBackdrops(cover.getBackdrops());
        showCast(details.getCast());
        showCollection(details.getCollection());
        showRecommendations(details.getRecommended());
        showSimilar(details.getSimilarMovies());
        showTrailers(details.getTrailers());
        if(!TextUtils.isEmpty(cover.getDuration())){
            view.showDuration(cover.getDuration());
        }
        view.showDescription(details.getMovieInfo().getDescription());
    }

    private void showCollection(MediaCollection collection){
        if(collection!=null) {
            if (!isEmpty(collection.getCovers())) {
                view.showCollection(collection);
            }
        }
    }

    @Override
    public void share() {
        repository.get(mediaId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(this::shareWith);
    }

    private void shareWith(MovieDetails details){
        if(details!=null) {
            MediaCover cover = details.getMovieCover();
            MovieInfo info = details.getMovieInfo();
            String text = cover.getMovieTitle();
            text += '\n';
            text += info.getDescription();
            view.share(text);
        }
    }
}
