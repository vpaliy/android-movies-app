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
import com.popularmovies.vpaliy.domain.model.MediaCover;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.AbstractMediaAdapter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.bus.RxBus;
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.bus.events.ExposeEvent;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.Constants;
import java.util.List;
import butterknife.ButterKnife;
import butterknife.BindView;
import android.support.annotation.NonNull;

public class RelatedMoviesAdapter extends AbstractMediaAdapter<MediaCover> {

    public RelatedMoviesAdapter(@NonNull Context context, @NonNull List<MediaCover> data,
                                @NonNull RxBus eventBus){
        super(context,eventBus);
        this.data=data;
    }

    public class MovieViewHolder extends AbstractMediaAdapter<MediaCover>.GenericViewHolder {

        @BindView(R.id.media_poster)
        ImageView posterImage;

        @BindView(R.id.media_title)
        TextView mediaTitle;

        MovieViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
            posterImage.setOnClickListener(v -> {
                if(!isLocked()) {
                    Context context=itemView.getContext();
                    Bundle args = new Bundle();
                    String transitionName=context.getString(R.string.poster_transition_name)+getAdapterPosition();
                    args.putString(Constants.EXTRA_ID, at(getAdapterPosition()).getMediaId());
                    args.putString(Constants.EXTRA_DATA,at(getAdapterPosition()).getMainBackdrop());
                    args.putString(Constants.EXTRA_POSTER_PATH,at(getAdapterPosition()).getPosterPath());
                    args.putString(Constants.EXTRA_TRANSITION_NAME,transitionName);
                    ViewCompat.setTransitionName(posterImage,transitionName);
                    rxBus.send(ExposeEvent.exposeMovieDetails(args,Pair.create(posterImage,transitionName)));
                    unlockAfter(UNLOCK_TIMEOUT);
                }
            });
        }

        @Override
        public void onBindData(){
            MediaCover movieCover=data.get(getAdapterPosition());
            Glide.with(inflater.getContext())
                    .load(movieCover.getPosterPath())
                    .priority(Priority.IMMEDIATE)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .placeholder(R.drawable.placeholder)
                    .animate(R.anim.fade_in)
                    .into(posterImage);
            mediaTitle.setText(movieCover.getMovieTitle());
        }
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root=inflater.inflate(R.layout.adapter_media_item,parent,false);
        return new MovieViewHolder(root);
    }
}
