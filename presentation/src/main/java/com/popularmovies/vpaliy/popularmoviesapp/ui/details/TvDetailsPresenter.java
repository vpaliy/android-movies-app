package com.popularmovies.vpaliy.popularmoviesapp.ui.details;

import com.popularmovies.vpaliy.data.utils.scheduler.BaseSchedulerProvider;
import com.popularmovies.vpaliy.domain.model.MediaCover;
import com.popularmovies.vpaliy.domain.model.TVShowDetails;
import com.popularmovies.vpaliy.domain.repository.ICoverRepository;
import com.popularmovies.vpaliy.domain.repository.IDetailsRepository;
import com.popularmovies.vpaliy.popularmoviesapp.di.scope.ViewScope;
import javax.inject.Inject;
import android.support.annotation.NonNull;
import com.popularmovies.vpaliy.data.source.qualifier.TV;

@ViewScope
public class TvDetailsPresenter extends DetailsPresenter<TVShowDetails> {

    @Inject
    public TvDetailsPresenter(@NonNull IDetailsRepository<TVShowDetails> repository,
                                 @NonNull @TV ICoverRepository<MediaCover> iCoverRepository,
                                 @NonNull BaseSchedulerProvider schedulerProvider){
        super(repository,iCoverRepository,schedulerProvider);
    }

    @Override
    public void processData(@NonNull TVShowDetails details) {
        MediaCover cover=details.getTvShowCover();
        if(cover.getBackdrops()!=null){
            view.showBackdrops(cover.getBackdrops());
        }

        if(!isEmpty(details.getCast())){
            view.showCast(details.getCast());
        }

        if(!isEmpty(details.getSeasons())){
            view.showSeasons(details.getSeasons());
        }

        if(!isEmpty(details.getSimilar())){
            view.showSimilar(details.getSimilar());
        }

        if(!isEmpty(details.getTrailers())){
            view.showTrailers(details.getTrailers());
        }

        view.showDescription(details.getTvShowInfo().getOverview());
    }
}
