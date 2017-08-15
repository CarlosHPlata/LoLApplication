package com.kingskull.lolapplication.api.restfull.services;

import com.kingskull.lolapplication.models.pojos.item.ItemListDto;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Carlos on 06/02/2016.
 */
public interface ItemService {

    @GET("/api/lol/static-data/{region}/v1.2/item")
    void getAllItems(
        @Path("region") String region,
        @Query("itemListData") String itemListData,
        @Query("api_key") String apiKey,
        Callback<ItemListDto> callback
    );

    @GET("/api/lol/static-data/{region}/v1.2/item")
    void getAllItems(
            @Path("region") String region,
            @Query("itemListData") String itemListData,
            @Query("version") String version,
            @Query("api_key") String apiKey,
            Callback<ItemListDto> callback
    );
}
