package com.popularmovies.vpaliy.popularmoviesapp.ui.adapter;

import android.content.Context;
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
import java.util.List;
import butterknife.ButterKnife;

import android.support.annotation.NonNull;
import butterknife.BindView;

public class MoreMediaAdapter extends AbstractMediaAdapter{

    public MoreMediaAdapter(@NonNull Context context,
                            @NonNull RxBus rxBus){
        super(context,rxBus);
    }

    public void setData(List<MediaCover> data) {
        this.data = data;
    }

    public class MediaViewHolder extends AbstractMediaAdapter.GenericViewHolder {

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
        }

        void onBindData(){
            MediaCover cover=at(getAdapterPosition());
            releaseYear.setText(convertToYear(cover.getReleaseDate()));
            String bullet="\u25CF"+" ";
            String titleText=bullet+cover.getMovieTitle();
            mediaTitle.setText(titleText);

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
