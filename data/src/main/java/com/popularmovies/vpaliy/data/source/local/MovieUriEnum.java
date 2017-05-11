package com.popularmovies.vpaliy.data.source.local;


public enum MovieUriEnum {

    ;
    public int code;
    public String contentType;
    public String table;
    public String path;

    MovieUriEnum(int code, String path, String table, String contentType){
        this.code=code;
        this.path=path;
        this.table=table;
        this.contentType=contentType;
    }
}
