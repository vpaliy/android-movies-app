package com.popularmovies.vpaliy.popularmoviesapp.ui.details.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.popularmovies.vpaliy.domain.model.SeasonCover;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.AbstractMediaAdapter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.bus.RxBus;
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.bus.events.ExposeEvent;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.Constants;
import butterknife.ButterKnife;
import butterknife.BindView;
import android.support.annotation.NonNull;

public class SeasonAdapter extends AbstractMediaAdapter<SeasonCover> {

    public SeasonAdapter(@NonNull Context context, @NonNull RxBus eventBus) {
        super(context, eventBus);
    }

    public class SeasonViewHolder extends AbstractMediaAdapter<SeasonCover>.GenericViewHolder {

        @BindView(R.id.media_poster)
        ImageView posterImage;

        @BindView(R.id.media_title)
        TextView mediaTitle;

        SeasonViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(view->{
                if(!isLocked()) {
                    Context context=itemView.getContext();
                    Bundle args = new Bundle();
                    SeasonCover cover=at(getAdapterPosition());
                    String transitionName=context.getString(R.string.season_transition_name);
                    args.putString(Constants.EXTRA_ID, cover.getSeasonId());
                    args.putString(Constants.EXTRA_POSTER_PATH,cover.getPosterPath());
                    ViewCompat.setTransitionName(posterImage,transitionName);
                    rxBus.send(ExposeEvent.exposeSeasonDetails(args, Pair.create(posterImage,transitionName)));
                    unlockAfter(UNLOCK_TIMEOUT);
                }
            });
        }

        @Override
        public void onBindData(){
            SeasonCover season=at(getAdapterPosition());
            Glide.with(inflater.getContext())
                    .load(season.getPosterPath())
                    .priority(Priority.IMMEDIATE)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .placeholder(R.drawable.placeholder)
                    .animate(R.anim.fade_in)
                    .into(posterImage);
            mediaTitle.setText(season.getSeasonName());
        }
    }

    @Override
    public SeasonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root=inflater.inflate(R.layout.adapter_media_item,parent,false);
        return new SeasonViewHolder(root);
    }
}
