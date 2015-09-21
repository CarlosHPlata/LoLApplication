package com.kingskull.lolapplication.api.restfull.services;

import com.kingskull.lolapplication.models.pojos.Summoner;
import com.kingskull.lolapplication.models.pojos.league.LeagueStat;
import com.kingskull.lolapplication.models.pojos.ranked.RankedStat;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Usuario on 07/09/2015.
 */
public interface SummonerService {

    @GET("/api/lol/{region}/v1.4/summoner/by-name/{summonerNames}")
    void getSummonner(
            @Path("summonerNames")String summonerNames,
            @Path("region") String region,
            @Query("api_key") String apiKey,
            Callback<HashMap<String,Summoner>> callback
    );

    @GET("/api/lol/{region}/v1.4/summoner/{summonerIds}")
    void getSummonerById(
            @Path("summonerIds") long id,
            @Path("region") String region,
            @Query("api_key") String apiKey,
            Callback<HashMap<String, Summoner>> callback
    );

    @GET("/api/lol/{region}/v1.3/stats/by-summoner/{summonerId}/ranked")
    void getRankedStats(
            @Path("summonerId") long summonerId,
            @Path("region") String region,
            @Query("season") String season,
            @Query("api_key") String apiKey,
            Callback<RankedStat> callback
    );

    @GET("/api/lol/{region}/v2.5/league/by-summoner/{summonerIds}/entry")
    void getLeagueStats(
            @Path("summonerIds") String summonerIds,
            @Path("region") String region,
            @Query("api_key") String apiKey,
            Callback< HashMap<String, ArrayList<LeagueStat>> > callback
    );

}
