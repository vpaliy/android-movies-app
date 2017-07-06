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

    public List<To> map(List<From> from){
        if(from!=null){
            List<To> result=new ArrayList<>(from.size());
            from.forEach(item->result.add(map(item)));
            return result;
        }
        return null;
    }

    public List<From> reverseMap(List<To> toList){
        if(toList!=null){
            List<From> result=new ArrayList<>(toList.size());
            toList.forEach(item->result.add(reverseMap(item)));
            return result;
        }
        return null;
    }
}
