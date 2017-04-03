package com.popularmovies.vpaliy.data.mapper;


import java.util.List;

/**
 * Maps one a fake entity into a real
 * @param <To> real entity
 * @param <From> fake entity
 */
public interface Mapper<To, From> {
    To map(From from);
    List<To> map(List<From> from);
}
