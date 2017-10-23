package com.popularmovies.vpaliy.data.utils;

/*import com.google.common.annotations.VisibleForTesting;
import com.popularmovies.vpaliy.data.configuration.ImageQualityConfiguration;
import com.popularmovies.vpaliy.data.entity.BackdropImage;
import com.popularmovies.vpaliy.data.entity.Genre;
import com.popularmovies.vpaliy.data.entity.Network;
import com.popularmovies.vpaliy.data.entity.TvShow;
import com.popularmovies.vpaliy.data.entity.TvShowInfoEntity;
import com.popularmovies.vpaliy.domain.configuration.IImageQualityConfiguration;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MapperUtils {
    private MapperUtils(){
        throw new UnsupportedOperationException();
    }

    //TODO add resources
    @VisibleForTesting
    public static String convertToDuration(int runtime){
        if(runtime>0) {
            int hours=runtime/60;
            String duration="";
            if(hours>0) {
                duration = Integer.toString(hours)+" hr"+(hours>1?"s ":" ");
            }
            int minRemainder=runtime % 60;
            if(minRemainder!=0){
                duration+=Integer.toString(minRemainder)+" min";
            }
            return duration;
        }
        return null;
    }

    //TODO add resources
    @VisibleForTesting
    public static int convertToRuntime(String duration){
        if(duration==null) return 0;
        Pattern pattern=Pattern.compile("-?\\d+");
        Matcher matcher=pattern.matcher(duration);
        int runtime=0;
        int count=0;
        while(matcher.find()) count++;
        matcher=matcher.reset();
        if(count==2){
            matcher.find();
            runtime=Integer.parseInt(matcher.group())*60;
            if(matcher.find()) runtime+=Integer.parseInt(matcher.group());
        }else if(matcher.find()) {
            runtime = Integer.parseInt(matcher.group());
            duration=duration.trim();
            if(duration.contains("hr")||duration.contains("hrs")) runtime*=60;
        }

        return runtime;
    }

    public static String convertToYear(String date){
        if(date==null||date.length()<4) return null;
        return date.substring(0,4);
    }

    public static List<String> convert(List<BackdropImage> images, ImageQualityConfiguration configuration){
        if(images==null) return null;
        List<String> paths=new LinkedList<>();
        for(BackdropImage image:images){
            paths.add(configuration.convertBackdrop(image.getBackdropPath()));
        }
        return paths;
    }

    public static List<BackdropImage> convertToBackdrops(List<String> backdrops, ImageQualityConfiguration configuration){
        if(backdrops==null) return null;
        List<BackdropImage> images=new LinkedList<>();
        for(String backdrop:backdrops){
            String result=configuration.extractPath(backdrop);
            images.add(new BackdropImage(result));
        }
        return images;
    }

    public static TvShow createTvShowCover(TvShowInfoEntity info){
        if(info==null) return null;
        TvShow cover=new TvShow();
        cover.setFirstAirDate(info.getFirstAirDate());
        cover.setId(info.getTvShowId());
        cover.setGenreList(info.getGenres());
        cover.setVoteAverage(info.getVoteAverage());
        cover.setVoteCount(info.getVoteCount());
        cover.setBackdropPath(info.getBackdrop_path());
        cover.setPosterPath(info.getPosterPath());
        cover.setName(info.getName());
        cover.setOverview(info.getOverview());
        cover.setOriginalName(info.getOriginalName());
        cover.setPopularity(info.getPopularity());
        return cover;
    }

    public static List<String> convertToString(List<Network> networks){
        if(networks==null) return null;
        List<String> result=new ArrayList<>(networks.size());
        for(Network network:networks){
            result.add(network.getName());
        }
        return result;
    }

    public static List<Network> convertToNetworks(List<String> strings){
        if(strings==null) return null;
        List<Network> networks=new ArrayList<>(strings.size());
        for(String string:strings) networks.add(new Network(string));
        return networks;
    }

    public static List<String> convert(List<Genre> genres){
        if(genres==null) return null;
        List<String> result=new LinkedList<>();
        for(Genre genre:genres) result.add(genre.getName());
        return result;
    }

    public static List<Genre> convertToGenres(List<String> stringList){
        if(stringList==null) return null;
        List<Genre> genres=new LinkedList<>();
        for(String string:stringList) genres.add(new Genre(string));
        return genres;
    }

}
*/
