package com.popularmovies.vpaliy.popularmoviesapp.ui.details.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.popularmovies.vpaliy.domain.model.ActorCover;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.AbstractMediaAdapter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.bus.RxBus;
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.bus.events.ExposeEvent;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.Constants;

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
            itemView.setOnClickListener(view->{
                ActorCover actorCover=at(getAdapterPosition());
                Context context=inflater.getContext();
                Bundle args=new Bundle();
                args.putString(Constants.EXTRA_ID,actorCover.getActorId());
                ViewCompat.setTransitionName(actorImage,context.getString(R.string.actor_poster_transition_name));
                rxBus.send(ExposeEvent.exposeActorDetails(args,Pair.create(actorImage,context.getString(R.string.actor_poster_transition_name))));
            });
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
