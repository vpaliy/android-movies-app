package com.popularmovies.vpaliy.data.entity;

import android.support.annotation.NonNull;

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

    public static List<String> convertToString(List<Network> networks){
        if(networks==null) return null;
        List<String> result=new ArrayList<>(networks.size());
        networks.forEach(network -> result.add(network.getName()));
        return result;
    }

    public static List<Network> convertToNetworks(List<String> strings){
        if(strings==null) return null;
        List<Network> networks=new ArrayList<>(strings.size());
        strings.forEach(string->networks.add(new Network(string)));
        return networks;
    }
}
