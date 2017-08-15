package com.kingskull.lolapplication.api.restfull.services;

import com.kingskull.lolapplication.models.pojos.matchinfo.MatchDetail;

import retrofit.Callback;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by cherrera on 1/26/2016.
 */
public interface MatchService {

    @GET("/api/lol/{region}/v2.2/match/{matchId}")
    void getMatchDetails(
        @Path("matchId") long matchId,
        @Path("region")String region,
        @Query("includeTimeline")  boolean includeTimeline,
        @Query("api_key") String apiKey,
        Callback<MatchDetail> callback
    );
}
