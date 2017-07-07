package com.popularmovies.vpaliy.popularmoviesapp.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.icu.text.NumberFormat;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.popularmovies.vpaliy.domain.model.MediaCover;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.bus.RxBus;
import com.popularmovies.vpaliy.popularmoviesapp.bus.events.ExposeDetailsEvent;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.Constants;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.PresentationUtils;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.wrapper.TransitionWrapper;
import com.vpaliy.chips_lover.ChipView;
import com.vpaliy.chips_lover.ChipsLayout;
import butterknife.ButterKnife;

import android.support.annotation.NonNull;
import butterknife.BindView;

@SuppressWarnings("WeakerAccess")
public class MoreMediaAdapter extends AbstractMediaAdapter<MediaCover>{

    public MoreMediaAdapter(@NonNull Context context, @NonNull RxBus rxBus){
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

        @BindView(R.id.chipsContainer)
        ChipsLayout chipsContainer;

        @BindView(R.id.details_background)
        View background;

        MediaViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
            posterImage.setOnClickListener(v -> {
                if(!isLocked()) {
                    lock();
                    Bundle args = new Bundle();
                    args.putInt(Constants.EXTRA_ID, at(getAdapterPosition()).getMediaId());
                    TransitionWrapper wrapper = TransitionWrapper.wrap(posterImage, args);
                    rxBus.send(new ExposeDetailsEvent(wrapper));
                    unlockAfter(UNLOCK_TIMEOUT);
                }
            });
        }

        void onBindData(){
            MediaCover cover=at(getAdapterPosition());
            releaseYear.setText(cover.getFormattedDate());
            mediaTitle.setText(cover.getMovieTitle());
            chipsContainer.setTags(cover.getGenres());
            ratings.setText(cover.getAverageRate());
            Glide.with(itemView.getContext())
                    .load(cover.getMainBackdrop())
                    .asBitmap()
                    .priority(Priority.IMMEDIATE)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .placeholder(R.drawable.placeholder)
                    .animate(R.anim.fade_in)
                    .into(new ImageViewTarget<Bitmap>(posterImage) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            posterImage.setImageBitmap(resource);
                            new Palette.Builder(resource).generate(MediaViewHolder.this::applyPalette);
                        }
                    });
        }

        private void applyPalette(Palette palette){
            int defaultColor=ContextCompat.getColor(itemView.getContext(),R.color.light_grey);
            background.setBackgroundColor(palette.getDominantColor(defaultColor));
        }

    }

    @Override
    public MediaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root=inflater.inflate(R.layout.adapter_more_media_item,parent,false);
        return new MediaViewHolder(root);
    }

}
