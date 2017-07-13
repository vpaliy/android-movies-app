package com.popularmovies.vpaliy.popularmoviesapp.ui.more;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.graphics.Palette;
import android.util.Log;
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
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.bus.RxBus;
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.bus.events.ExposeDetailsEvent;
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.AbstractMediaAdapter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.bus.events.ExposeEvent;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.Constants;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.wrapper.TransitionWrapper;
import com.vpaliy.chips_lover.ChipBuilder;
import com.vpaliy.chips_lover.ChipsLayout;
import butterknife.ButterKnife;

import android.support.annotation.NonNull;
import butterknife.BindView;

public class MoreMediaAdapter extends AbstractMediaAdapter<MediaCover> {

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
                    args.putString(Constants.EXTRA_DATA,at(getAdapterPosition()).getMainBackdrop());
                    ViewCompat.setTransitionName(posterImage,inflater.getContext().getString(R.string.backdrop_transition_name));
                    Context context=itemView.getContext();
                    rxBus.send(ExposeEvent.dispatchEvent(args, Pair.create(itemView,context.getString(R.string.background_transition_name)),
                            Pair.create(posterImage,context.getString(R.string.backdrop_transition_name)),
                            Pair.create(mediaTitle,context.getString(R.string.title_transition_name)),
                            Pair.create(releaseYear,context.getString(R.string.date_transition_name)),
                            Pair.create(background,context.getString(R.string.media_details_transition_name)),
                            Pair.create(chipsContainer,context.getString(R.string.tags_transition_name))));
                    unlockAfter(UNLOCK_TIMEOUT);
                }
            });
        }

        @Override
        public void onBindData(){
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

        //that's where the magic happens
        private void applyPalette(Palette palette){
            Palette.Swatch swatch=palette.getDarkVibrantSwatch();
            if(swatch==null) swatch=palette.getDominantSwatch();
            //apply if not null
            if(swatch!=null){
                background.setBackgroundColor(swatch.getRgb());
                ChipBuilder builder=chipsContainer.getChipBuilder()
                        .setBackgroundColor(swatch.getTitleTextColor())
                        .setTextColor(swatch.getBodyTextColor());
                chipsContainer.updateChipColors(builder);
                mediaTitle.setTextColor(swatch.getBodyTextColor());
                releaseYear.setTextColor(swatch.getBodyTextColor());
                ratings.setTextColor(swatch.getBodyTextColor());
                setDrawableColor(releaseYear,swatch.getBodyTextColor());
                setDrawableColor(ratings,swatch.getBodyTextColor());
            }
        }

        //color the drawable, if there are any
        private void setDrawableColor(TextView view, int color){
            Drawable[] drawables=view.getCompoundDrawables();
            for(Drawable drawable:drawables){
                if(drawable!=null){
                    drawable.mutate();
                    DrawableCompat.setTint(drawable,color);
                }
            }
        }
    }

    @Override
    public MediaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root=inflater.inflate(R.layout.adapter_more_media_item,parent,false);
        return new MediaViewHolder(root);
    }
}
