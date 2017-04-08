package com.popularmovies.vpaliy.popularmoviesapp.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.bumptech.glide.Glide;
import com.popularmovies.vpaliy.domain.model.ActorCover;
import com.popularmovies.vpaliy.popularmoviesapp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


public class MovieCastAdapter extends RecyclerView.Adapter<MovieCastAdapter.CastViewHolder> {

    private List<ActorCover> castList;
    private LayoutInflater inflater;

    public MovieCastAdapter(@NonNull Context context,
                            @NonNull List<ActorCover> castList){
        this.castList=castList;
        this.inflater=LayoutInflater.from(context);
    }

    public class CastViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        @BindView(R.id.actorImage)
        CircleImageView actorImage;

        @BindView(R.id.actionName)
        TextView actorName;

        public CastViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        void onBindData(){

            Glide.with(inflater.getContext())
                    .load(castList.get(getAdapterPosition()).getActorAvatar())
                    .centerCrop()
                    .into(actorImage);
            ActorCover actor=castList.get(getAdapterPosition());
            String fullName=actor.getName();

            actorName.setText(fullName);
        }

        @Override
        public void onClick(View v) {

        }
    }


    @Override
    public CastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root=inflater.inflate(R.layout.adapter_movie_cast,parent,false);
        return new CastViewHolder(root);
    }

    @Override
    public void onBindViewHolder(CastViewHolder holder, int position) {
        holder.onBindData();
    }

    @Override
    public int getItemCount() {
        return castList.size();
    }
}
