package com.kingskull.lolapplication.api.restfull.services;

import com.kingskull.lolapplication.models.pojos.DragonVersion.Realm;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Carlos on 07/05/2016.
 */
public interface DragonService {

    @GET("/api/lol/static-data/{region}/v1.2/realm")
    void getActiveVersion(
            @Path("region") String region,
            @Query("api_key") String apiKey,
            Callback<Realm> callback
    );

    @GET("/api/lol/static-data/{region}/v1.2/versions")
    void getVersionsList(
            @Path("region") String region,
            @Query("api_key") String apiKey,
            Callback<ArrayList<String>> callback
    );

}
