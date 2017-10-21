package com.popularmovies.vpaliy.popularmoviesapp.ui.details;

import com.popularmovies.vpaliy.data.utils.scheduler.BaseSchedulerProvider;
import com.popularmovies.vpaliy.domain.model.MediaCover;
import com.popularmovies.vpaliy.domain.model.SeasonCover;
import com.popularmovies.vpaliy.domain.model.TVShowDetails;
import com.popularmovies.vpaliy.domain.model.TVShowInfo;
import com.popularmovies.vpaliy.popularmoviesapp.di.scope.ViewScope;
import javax.inject.Inject;
import android.support.annotation.NonNull;
import com.popularmovies.vpaliy.data.source.qualifier.TV;
import android.text.TextUtils;
import java.util.List;

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
        showBackdrops(cover.getBackdrops());
        showCast(details.getCast());
        showSeasons(details.getSeasons());
        showSimilar(details.getSimilar());
        showTrailers(details.getTrailers());
        if(!TextUtils.isEmpty(details.getDuration())) {
            view.showDuration(details.getDuration());
        }
        view.showDescription(details.getTvShowInfo().getOverview());
    }

    private void showSeasons(List<SeasonCover> seasons){
        if(!isEmpty(seasons)){
            view.showSeasons(seasons);
        }
    }

    @Override
    public void share() {
        repository.get(mediaId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(this::shareWith);
    }

    private void shareWith(TVShowDetails details){
        if(details!=null) {
            MediaCover cover = details.getTvShowCover();
            TVShowInfo info = new TVShowInfo();
            String text = (cover.getMovieTitle());
            text += '\n';
            text += info.getOverview();
            view.share(text);
        }
    }
}
