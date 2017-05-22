package com.popularmovies.vpaliy.popularmoviesapp.ui.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MediaTypeAdapter extends RecyclerView.Adapter<MediaTypeAdapter.TypeViewHolder>{

    private List<MediaTypeWrapper> data;
    private LayoutInflater inflater;

    public MediaTypeAdapter(@NonNull Context context){
        this.inflater=LayoutInflater.from(context);
        this.data=new ArrayList<>();
    }

    public void setData(@NonNull List<MediaTypeWrapper> wrappers){
        this.data=wrappers;
        notifyDataSetChanged();
    }

    public void addWrapper(@NonNull MediaTypeWrapper wrapper){
        data.add(wrapper);
        notifyItemInserted(data.size()-1);
    }

    public class TypeViewHolder extends RecyclerView.ViewHolder {

        RecyclerView list;
        TextView title;

        public TypeViewHolder(View itemView){
            super(itemView);
        }

        void bindData(){
            MediaTypeWrapper wrapper=at(getAdapterPosition());
            list.setAdapter(wrapper.adapter);
            title.setText(wrapper.text);
        }
    }

    private MediaTypeWrapper at(int index){
        return data.get(index);
    }

    @Override
    public TypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(TypeViewHolder holder, int position) {
        holder.bindData();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class MediaTypeWrapper {
        private String text;
        private RecyclerView.Adapter<?> adapter;

        private MediaTypeWrapper(@NonNull String text,
                                @NonNull RecyclerView.Adapter<?> adapter){
            this.text=text;
            this.adapter=adapter;
        }

        public static MediaTypeWrapper wrap(@NonNull String text,
                                            @NonNull RecyclerView.Adapter<?> adapter){
            return new MediaTypeWrapper(text,adapter);
        }
    }
}
