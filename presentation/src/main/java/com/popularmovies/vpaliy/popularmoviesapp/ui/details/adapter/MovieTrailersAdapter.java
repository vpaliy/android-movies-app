package com.popularmovies.vpaliy.popularmoviesapp.ui.details.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.popularmovies.vpaliy.domain.model.Trailer;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieTrailersAdapter extends
        RecyclerView.Adapter<MovieTrailersAdapter.TrailerViewHolder> {


    private static final String YOUTUBE_THUMBNAIL = "https://img.youtube.com/vi/%s/mqdefault.jpg";
    private List<Trailer> trailers;
    private LayoutInflater inflater;


    public MovieTrailersAdapter(@NonNull Context context){
        this.inflater=LayoutInflater.from(context);
        trailers=new ArrayList<>();
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        @BindView(R.id.trailerImage)
        ImageView trailerImage;

        @BindView(R.id.trailerTitle)
        TextView trailerText;

        public TrailerViewHolder(@NonNull View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Trailer trailer=trailers.get(getAdapterPosition());
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=" + trailer.getTrailerUrl()));
            inflater.getContext().startActivity(intent);
        }

        void onBindData(){
            Glide.with(itemView.getContext())
                    .load(String.format(YOUTUBE_THUMBNAIL,trailers.get(getAdapterPosition()).getTrailerUrl()))
                    .centerCrop()
                    .priority(Priority.HIGH)
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .placeholder(R.drawable.placeholder)
                    .animate(R.anim.fade_in)
                    .into(trailerImage);
            trailerText.setText(trailers.get(getAdapterPosition()).getTrailerTitle());
        }
    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }


    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root=inflater.inflate(R.layout.adapter_trailer_item,parent,false);
        return new TrailerViewHolder(root);
    }


    @Override
    public void onBindViewHolder(TrailerViewHolder holder, int position) {
        holder.onBindData();
    }

    public void setData(@NonNull List<Trailer> trailers){
        this.trailers=trailers;
        notifyDataSetChanged();
    }
}
