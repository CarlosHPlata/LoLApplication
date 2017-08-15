package com.kingskull.lolapplication.api.restfull.Utils;

import android.util.Log;

import com.kingskull.lolapplication.api.observer.BusProvider;
import com.kingskull.lolapplication.api.restfull.connections.Errors.RiotApiError;
import com.kingskull.lolapplication.api.restfull.connections.RIOT;
import com.kingskull.lolapplication.api.restfull.connections.StaticInfo;
import com.kingskull.lolapplication.api.restfull.connections.responses.ItemsResponse;
import com.kingskull.lolapplication.api.restfull.services.ItemService;
import com.kingskull.lolapplication.models.pojos.item.ItemListDto;
import com.kingskull.lolapplication.models.pojos.matchinfo.MatchDetail;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Carlos on 06/02/2016.
 */
public class ItemApiCall {

    RestAdapter restAdapter;
    ItemService itemService;

    public ItemApiCall(String region){
        String endpoint = StaticInfo.getInstance().getEndPoints().get("global");

        this.restAdapter = new RestAdapter.Builder()
                .setEndpoint(endpoint)
                .build();

        this.itemService = restAdapter.create(ItemService.class);
    }

    public void getItemInfo(String itemListData, String version, String region){
        itemService.getAllItems(region, itemListData, version, StaticInfo.getInstance().getApiKey(), new Callback<ItemListDto>() {
            @Override
            public void success(ItemListDto itemListDto, Response response) {
                Log.e("SYNERGY", response.getUrl());
                BusProvider.post(new ItemsResponse(itemListDto));
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("SYNERGY", error.getUrl());
                Log.e("SYNERGY", error.getMessage());
                BusProvider.post(new RiotApiError("Error al cargar todos los items"));
            }
        });
    }
}
