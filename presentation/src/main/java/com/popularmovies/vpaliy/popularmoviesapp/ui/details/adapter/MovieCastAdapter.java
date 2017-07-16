package com.popularmovies.vpaliy.popularmoviesapp.ui.details.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.popularmovies.vpaliy.domain.model.ActorCover;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.AbstractMediaAdapter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.bus.RxBus;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


public class MovieCastAdapter extends AbstractMediaAdapter<ActorCover> {

    public MovieCastAdapter(@NonNull Context context, @NonNull RxBus rxBus){
        super(context,rxBus);
    }

    public class CastViewHolder extends AbstractMediaAdapter<ActorCover>.GenericViewHolder{

        @BindView(R.id.actor_image)
        CircleImageView actorImage;

        @BindView(R.id.actor_name)
        TextView actorName;

        CastViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void onBindData(){
            Glide.with(inflater.getContext())
                    .load(at(getAdapterPosition()).getActorAvatar())
                    .centerCrop()
                    .into(actorImage);
            ActorCover actor=at(getAdapterPosition());
            String fullName=actor.getName();

            actorName.setText(fullName);
        }
    }
    @Override
    public CastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root=inflater.inflate(R.layout.adapter_movie_cast,parent,false);
        return new CastViewHolder(root);
    }
}
