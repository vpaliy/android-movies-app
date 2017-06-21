package com.popularmovies.vpaliy.data.utils;

import com.google.common.annotations.VisibleForTesting;
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
}
