package com.popularmovies.vpaliy.popularmoviesapp.ui.actor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.AbstractMediaAdapter;
import com.popularmovies.vpaliy.popularmoviesapp.ui.base.bus.RxBus;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImagesAdapter extends AbstractMediaAdapter<String> {

    public ImagesAdapter(@NonNull Context context, @NonNull RxBus bus){
        super(context,bus);
    }

    public class ImageViewHolder extends AbstractMediaAdapter<String>.GenericViewHolder{

        @BindView(R.id.image)
        ImageView image;

        ImageViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @Override
        public void onBindData() {
            Glide.with(image.getContext())
                    .load(at(getAdapterPosition()))
                    .centerCrop()
                    .priority(Priority.HIGH)
                    .into(image);
        }
    }

    @Override
    public GenericViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root=inflater.inflate(R.layout.adapter_actor_image,parent,false);
        return new ImageViewHolder(root);
    }
}
