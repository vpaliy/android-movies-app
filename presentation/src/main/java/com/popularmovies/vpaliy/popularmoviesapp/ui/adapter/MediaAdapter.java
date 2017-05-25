package com.popularmovies.vpaliy.popularmoviesapp.ui.adapter;


import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.popularmovies.vpaliy.domain.model.MediaCover;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.ui.eventBus.RxBus;
import com.popularmovies.vpaliy.popularmoviesapp.ui.eventBus.events.ExposeDetailsEvent;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.wrapper.TransitionWrapper;

import java.util.List;
import butterknife.ButterKnife;

import android.support.annotation.NonNull;
import butterknife.BindView;

public class MediaAdapter extends AbstractMediaAdapter<MediaCover> {

    public MediaAdapter(@NonNull Context context,
                        @NonNull RxBus rxBus){
        super(context,rxBus);
    }

    public void setData(List<MediaCover> data) {
        this.data = data;
    }

    public class MediaViewHolder extends GenericViewHolder {

        @BindView(R.id.media_release_year)
        TextView releaseYear;

        @BindView(R.id.media_poster)
        ImageView posterImage;

        @BindView(R.id.media_rating)
        TextView ratings;

        @BindView(R.id.media_title)
        TextView mediaTitle;

        public MediaViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
            posterImage.setOnClickListener(v -> {
                Bundle args=new Bundle();
                TransitionWrapper wrapper=TransitionWrapper.wrap(posterImage,args);
                rxBus.send(new ExposeDetailsEvent(wrapper));
            });



        }

        void onBindData(){
            MediaCover cover=at(getAdapterPosition());
            releaseYear.setText(convertToYear(cover.getReleaseDate()));
            String bullet="\u25CF"+" ";
            String titleText=bullet+cover.getMovieTitle();
            mediaTitle.setText(titleText);
            ratings.setText(Double.toString(cover.getAverageRate()));
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
    public void onBindViewHolder(GenericViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }

    @Override
    public MediaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root=inflater.inflate(R.layout.adapter_media_item,parent,false);
        return new MediaViewHolder(root);
    }

}
