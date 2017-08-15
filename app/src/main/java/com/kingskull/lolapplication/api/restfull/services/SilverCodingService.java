package com.kingskull.lolapplication.api.restfull.services;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by cherrera on 9/18/2015.
 */
public interface SilverCodingService {

    @GET("/api/v1/riotKey/{key}")
    void getApiKey(
        @Path("key") String key,
        Callback<String> callback
    );

}
