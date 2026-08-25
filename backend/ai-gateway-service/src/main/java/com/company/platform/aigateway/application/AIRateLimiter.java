package com.company.platform.aigateway.application;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;
@Component public class AIRateLimiter { private record Bucket(long window,int count){} private final ConcurrentHashMap<String,Bucket> buckets=new ConcurrentHashMap<>(); public boolean allow(String key,int limit){long window=Instant.now().getEpochSecond()/60;var result=new boolean[1];buckets.compute(key,(k,old)->{Bucket current=old==null||old.window()!=window?new Bucket(window,0):old;result[0]=current.count()<limit;return result[0]?new Bucket(window,current.count()+1):current;});return result[0];} }
