package com.kingskull.lolapplication.api.restfull.services;

import com.kingskull.lolapplication.models.pojos.Champion.Champion;
import com.kingskull.lolapplication.models.pojos.Champion.ChampionStatic;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.Callback;

/**
 * Created by Usuario on 20/09/2015.
 */
public interface LoadingService {

    @GET("/api/lol/static-data/{region}/v1.2/champion")
    void getChampions(
            @Path("region") String region,
            @Query("locale") String locale,
            @Query("dataById") boolean dataById,
            @Query("champData") String champData,
            @Query("api_key") String apiKey,
            Callback<ChampionStatic> callback
    );

    @GET("/api/lol/static-data/{region}/v1.2/champion/{id}")
    void getChampionById(
            @Path("id") int id,
            @Path("region") String region,
            @Query("champData") String champData,
            @Query("api_key") String apiKey,
            Callback<Champion> callback
    );

}
