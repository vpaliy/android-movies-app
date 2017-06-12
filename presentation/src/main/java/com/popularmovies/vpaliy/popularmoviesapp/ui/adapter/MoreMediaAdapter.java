package com.popularmovies.vpaliy.popularmoviesapp.ui.adapter;

import android.content.Context;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.popularmovies.vpaliy.domain.model.MediaCover;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.ui.eventBus.RxBus;
import com.popularmovies.vpaliy.popularmoviesapp.ui.eventBus.events.ExposeDetailsEvent;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.Constants;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.wrapper.TransitionWrapper;

import butterknife.ButterKnife;

import android.support.annotation.NonNull;

import java.util.Arrays;
import java.util.Locale;

import butterknife.BindView;

public class MoreMediaAdapter extends AbstractMediaAdapter<MediaCover>{

    public MoreMediaAdapter(@NonNull Context context,
                            @NonNull RxBus rxBus){
        super(context,rxBus);
    }

    public class MediaViewHolder extends AbstractMediaAdapter<MediaCover>.GenericViewHolder {

        @BindView(R.id.media_release_year)
        TextView releaseYear;

        @BindView(R.id.media_poster)
        ImageView posterImage;

        @BindView(R.id.media_ratings)
        TextView ratings;

        @BindView(R.id.media_title)
        TextView mediaTitle;

        @BindView(R.id.media_genres)
        TextView genres;

        public MediaViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
            posterImage.setOnClickListener(v -> {
                if(!isLocked()) {
                    lock();
                    Bundle args = new Bundle();
                    args.putInt(Constants.EXTRA_ID, at(getAdapterPosition()).getMediaId());
                    TransitionWrapper wrapper = TransitionWrapper.wrap(posterImage, args);
                    rxBus.send(new ExposeDetailsEvent(wrapper));
                }
            });
        }

        void onBindData(){
            MediaCover cover=at(getAdapterPosition());
            releaseYear.setText(convertToYear(cover.getReleaseDate()));
            mediaTitle.setText(cover.getMovieTitle());
            ratings.setText(String.format(Locale.US,"%.1f",cover.getAverageRate()));
            if(cover.getGenres()!=null) genres.setText(cover.getGenres().toString());
            Glide.with(itemView.getContext())
                    .load(cover.getPosterPath())
                    .priority(Priority.HIGH)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .placeholder(R.drawable.placeholder)
                    .animate(R.anim.fade_in)
                    .into(posterImage);
        }

        private String convertToYear(String date){
            return date.substring(0,4);
        }
    }

    @Override
    public MediaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root=inflater.inflate(R.layout.adapter_more_media_item,parent,false);
        return new MediaViewHolder(root);
    }

}
