package com.kingskull.lolapplication.api.restfull.Utils;

import android.util.Log;

import com.kingskull.lolapplication.api.observer.BusProvider;
import com.kingskull.lolapplication.api.restfull.connections.Errors.RiotApiError;
import com.kingskull.lolapplication.api.restfull.connections.RIOT;
import com.kingskull.lolapplication.api.restfull.connections.StaticInfo;
import com.kingskull.lolapplication.api.restfull.connections.responses.ChampionResponse;
import com.kingskull.lolapplication.api.restfull.services.LoadingService;
import com.kingskull.lolapplication.api.restfull.services.SummonerService;
import com.kingskull.lolapplication.models.pojos.Champion.Champion;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Usuario on 22/09/2015.
 */
public class ChampionApiCall {
    RestAdapter restAdapter;
    LoadingService service;

    public ChampionApiCall(String region) {
        String endpoint = StaticInfo.getInstance().getEndPoints().get("global");//StaticInfo.getInstance().getEndPoints().get(region);

        this.restAdapter = new RestAdapter.Builder()
                .setEndpoint(endpoint)
                .build();

        this.service = restAdapter.create(LoadingService.class);
    }

    public void getChampionById(int id, String region){
        service.getChampionById(id, region, "image,stats,tags", StaticInfo.getInstance().getApiKey(), new Callback<Champion>() {
            @Override
            public void success(Champion champion, Response response) {
                BusProvider.post(new ChampionResponse(champion));
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("SYNERGY", error.getUrl());
                Log.e("SYNERGY", error.getMessage());
                BusProvider.post(new RiotApiError("Error al cargar cammpeon, puede que no sea un campeon valido"));
            }
        });
    }
}
