package com.popularmovies.vpaliy.data.mapper;


import java.util.ArrayList;
import java.util.List;

/**
 * Maps one a fake entity into a real
 * @param <To> real entity
 * @param <From> fake entity
 */
public abstract class Mapper<To, From> {
    public abstract To map(From from);
    public abstract From reverseMap(To to);

    public List<To> map(List<From> froms){
        if(froms!=null){
            List<To> result=new ArrayList<>(froms.size());
            for(From from:froms) result.add(map(from));
            return result;
        }
        return null;
    }

    public List<From> reverseMap(List<To> toList){
        if(toList!=null){
            List<From> result=new ArrayList<>(toList.size());
            for(To to:toList) result.add(reverseMap(to));
            return result;
        }
        return null;
    }
}
