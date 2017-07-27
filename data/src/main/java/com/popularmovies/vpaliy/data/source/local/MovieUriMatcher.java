package com.popularmovies.vpaliy.data.source.local;


import android.content.UriMatcher;
import android.net.Uri;
import android.util.SparseArray;

public class MovieUriMatcher {

    private UriMatcher uriMatcher;
    private SparseArray<MovieUriEnum> codeMap;

    public MovieUriMatcher(){
        buildUriMatcher();
        buildUriMap();
    }

    private void buildUriMatcher(){
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        MovieUriEnum[] movieUriEnum=MovieUriEnum.values();
        for(MovieUriEnum uriEnum:movieUriEnum){
            uriMatcher.addURI(MoviesContract.CONTENT_AUTHORITY,uriEnum.path,uriEnum.code);
        }
    }

    private void buildUriMap(){
        codeMap=new SparseArray<>();
        MovieUriEnum[] movieUriEnum=MovieUriEnum.values();
        for(MovieUriEnum uriEnum:movieUriEnum){
            codeMap.put(uriEnum.code,uriEnum);
        }
    }

    public MovieUriEnum match(Uri uri){
        final int code=uriMatcher.match(uri);
        if(codeMap.get(code)==null){
            throw new UnsupportedOperationException("Unknown uri with code " + code);
        }
        return codeMap.get(code);
    }

    public String getType(Uri uri){
        final int code=uriMatcher.match(uri);
        if(codeMap.get(code)==null){
            throw new UnsupportedOperationException("Unknown uri with code " + code);
        }
        return codeMap.get(code).contentType;
    }
}
