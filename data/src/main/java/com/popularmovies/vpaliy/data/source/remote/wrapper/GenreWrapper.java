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
        genres.forEach(genre -> array.put(Integer.parseInt(genre.getId()),genre));
        return array;
    }
}
