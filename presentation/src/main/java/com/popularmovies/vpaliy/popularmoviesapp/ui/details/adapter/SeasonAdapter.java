package com.popularmovies.vpaliy.popularmoviesapp.ui.details.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.popularmovies.vpaliy.domain.model.TVShowSeason;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.AbstractMediaAdapter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.bus.RxBus;
import butterknife.ButterKnife;
import butterknife.BindView;
import android.support.annotation.NonNull;

public class SeasonAdapter extends AbstractMediaAdapter<TVShowSeason> {

    public SeasonAdapter(@NonNull Context context, @NonNull RxBus eventBus) {
        super(context, eventBus);
    }

    public class SeasonViewHolder extends AbstractMediaAdapter<TVShowSeason>.GenericViewHolder {

        @BindView(R.id.media_poster)
        ImageView posterImage;

        @BindView(R.id.media_title)
        TextView mediaTitle;

        SeasonViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @Override
        public void onBindData(){
            TVShowSeason season=at(getAdapterPosition());
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
