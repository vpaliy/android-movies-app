package com.popularmovies.vpaliy.data.source.remote.wrapper;

import android.util.SparseArray;
import com.google.gson.annotations.SerializedName;
import com.popularmovies.vpaliy.data.entity.Genre;
import java.util.List;

public class GenreWrapper {

    @SerializedName("genres")
    private List<Genre> genres;

    public SparseArray<Genre> convert(){
        SparseArray<Genre> array=new SparseArray<>();
        for(Genre genre:genres) array.put(Integer.parseInt(genre.getId()),genre);
        return array;
    }
}
