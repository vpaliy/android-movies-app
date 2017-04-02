package com.popularmovies.vpaliy.popularmoviesapp.presentation.ui.adapter;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.domain.model.Movie;
import com.popularmovies.vpaliy.popularmoviesapp.presentation.ui.utils.Constants;
import com.popularmovies.vpaliy.popularmoviesapp.presentation.ui.utils.events.ClickedMovieEvent;
import com.popularmovies.vpaliy.popularmoviesapp.presentation.ui.utils.wrapper.TransitionWrapper;
import com.popularmovies.vpaliy.popularmoviesapp.presentation.ui.view.SquareImage;
import com.squareup.otto.Bus;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>{

    private static final String TAG=MoviesAdapter.class.getSimpleName();

    private final Bus eventBus;
    private final List<Movie> data;
    private final LayoutInflater inflater;
    private boolean hasBeenClicked;

    public MoviesAdapter(@NonNull Context context,@NonNull List<Movie> data, @NonNull Bus eventBus){
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

        public MovieViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(!hasBeenClicked) {
                hasBeenClicked=true;
                Log.d(TAG, Integer.toString(getAdapterPosition()));
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.EXTRA_ID, data.get(getAdapterPosition()).getID());
                eventBus.post(new ClickedMovieEvent(TransitionWrapper.wrap(image, bundle)));
            }
        }

        void bindData(){
            Glide.with(inflater.getContext())
                    .fromResource()
                    .load(R.drawable.poster)
                    .centerCrop()
                    .into(image);
            title.setText(String.format(Locale.US,"%d",data.get(getAdapterPosition()).getAverageVote()));
            //install the rest of the data
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root=inflater.inflate(R.layout.adapter_movie_item,parent,false);
        return new MovieViewHolder(root);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.bindData();
    }
}
