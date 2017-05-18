package com.popularmovies.vpaliy.data.entity;

import com.google.gson.annotations.SerializedName;

public class Network {

    @SerializedName("id")
    private int networkId;

    @SerializedName("name")
    private String name;

    public String getName() {
        return name;
    }

    public int getNetworkId() {
        return networkId;
    }
}
