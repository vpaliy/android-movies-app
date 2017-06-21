package com.popularmovies.vpaliy.data.entity;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class Network {

    @SerializedName("id")
    private int networkId;

    @SerializedName("name")
    private String name;

    public Network(){}

    public Network(String name){
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public int getNetworkId() {
        return networkId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNetworkId(int networkId) {
        this.networkId = networkId;
    }
}
