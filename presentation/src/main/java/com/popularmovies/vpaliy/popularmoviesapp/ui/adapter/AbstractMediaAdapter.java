package com.popularmovies.vpaliy.popularmoviesapp.ui.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import com.popularmovies.vpaliy.popularmoviesapp.bus.RxBus;
import java.util.ArrayList;
import java.util.List;
import android.support.annotation.NonNull;

@SuppressWarnings("WeakerAccess")
public abstract class AbstractMediaAdapter<T> extends
        RecyclerView.Adapter<AbstractMediaAdapter<T>.GenericViewHolder>{

    protected List<T> data;
    protected LayoutInflater inflater;
    protected RxBus rxBus;
    private Handler handler=new Handler();
    private volatile boolean lock;

    protected final static long UNLOCK_TIMEOUT=500;

    public AbstractMediaAdapter(@NonNull Context context,
                                @NonNull RxBus rxBus){
        this.inflater=LayoutInflater.from(context);
        this.rxBus=rxBus;
        this.data=new ArrayList<>();
    }

    public void setData(List<T> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public abstract class GenericViewHolder extends RecyclerView.ViewHolder{
        public GenericViewHolder(View itemView){
            super(itemView);
        }
        abstract void onBindData();
    }

    public boolean isLocked(){
        return lock;
    }

    protected void unlockAfter(long milliSec){
        handler.postDelayed(this::unlock,milliSec);
    }

    public void unlock(){
        lock=false;
    }

    public void lock(){
        lock=true;
    }

    protected T at(int index){
        return data.get(index);
    }

    public void appendData(@NonNull List<T> data){
        int size=getItemCount();
        this.data.addAll(data);
        notifyItemRangeInserted(size,getItemCount());
    }

    public AbstractMediaAdapter<T> addItem(T item){
        int size=data.size();
        data.add(item);
        notifyItemRangeInserted(size,getItemCount());
        return this;
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