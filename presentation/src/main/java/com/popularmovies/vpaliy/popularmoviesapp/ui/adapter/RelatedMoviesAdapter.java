package com.popularmovies.vpaliy.popularmoviesapp.ui.adapter;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.popularmovies.vpaliy.domain.model.MediaCover;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.bus.RxBus;
import com.popularmovies.vpaliy.popularmoviesapp.bus.events.ExposeDetailsEvent;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.Constants;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.Permission;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.wrapper.TransitionWrapper;
import java.util.List;
import butterknife.ButterKnife;
import butterknife.BindView;
import android.support.annotation.NonNull;

public class RelatedMoviesAdapter extends RecyclerView.Adapter<RelatedMoviesAdapter.MovieViewHolder>{


    private static final String TAG=RelatedMoviesAdapter.class.getSimpleName();

    private final RxBus eventBus;
    private final List<MediaCover> data;
    private final LayoutInflater inflater;
    private boolean hasBeenClicked;

    public RelatedMoviesAdapter(@NonNull Context context,
                                @NonNull List<MediaCover> data,
                                @NonNull RxBus eventBus){
        this.eventBus=eventBus;
        this.data=data;
        this.inflater=LayoutInflater.from(context);

    }


    public class MovieViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        @BindView(R.id.movieImage)
        ImageView image;

        @BindView(R.id.movieTitle)
        TextView title;

        @BindView(R.id.movieYear)
        TextView year;

        public MovieViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(!hasBeenClicked) {
                hasBeenClicked=true;
                MediaCover victim=data.get(getAdapterPosition());
                if(Permission.checkForVersion(Build.VERSION_CODES.LOLLIPOP)){
                    image.setTransitionName(image.getContext().getString(R.string.movie_image)
                            +Integer.toString(victim.getMediaId()));
                }
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.EXTRA_ID, victim.getMediaId());
                eventBus.send(new ExposeDetailsEvent(TransitionWrapper.wrap(image, bundle)));
            }
        }

        void bindData(){
            MediaCover movieCover=data.get(getAdapterPosition());
            Glide.with(inflater.getContext())
                    .load(movieCover.getPosterPath())
                    .centerCrop()
                    .into(image);
            title.setText(data.get(getAdapterPosition()).getMovieTitle());
            String date= movieCover.getReleaseDate();
            year.setText(date);
        }
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root=inflater.inflate(R.layout.adapter_related_movie,parent,false);
        return new MovieViewHolder(root);
    }

    public void onResume(){
        hasBeenClicked=false;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.bindData();
    }
}
