package com.popularmovies.vpaliy.popularmoviesapp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import com.popularmovies.vpaliy.domain.model.MediaCover;
import com.popularmovies.vpaliy.popularmoviesapp.ui.eventBus.RxBus;
import java.util.ArrayList;
import java.util.List;
import android.support.annotation.NonNull;

public abstract class AbstractMediaAdapter extends
        RecyclerView.Adapter<AbstractMediaAdapter.GenericViewHolder>{

    protected List<MediaCover> data;
    protected LayoutInflater inflater;
    protected RxBus rxBus;

    public AbstractMediaAdapter(@NonNull Context context,
                                @NonNull RxBus rxBus){
        this.inflater=LayoutInflater.from(context);
        this.rxBus=rxBus;
        this.data=new ArrayList<>();
    }

    public void setData(List<MediaCover> data) {
        this.data = data;
    }

    public abstract class GenericViewHolder extends RecyclerView.ViewHolder{

        public GenericViewHolder(View itemView){
            super(itemView);
        }

        abstract void onBindData();

    }


    protected MediaCover at(int index){
        return data.get(index);
    }

    public void appendData(@NonNull List<MediaCover> movies){
        int size=getItemCount();
        data.addAll(movies);
        notifyItemRangeInserted(size,getItemCount());
    }


    @Override
    public int getItemCount() {
        return data.size();
    }


    @Override
    public void onBindViewHolder(GenericViewHolder holder, int position) {
        holder.onBindData();
    }
}