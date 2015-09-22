package com.kingskull.lolapplication.api.restfull.Utils;

import com.kingskull.lolapplication.api.observer.BusProvider;
import com.kingskull.lolapplication.api.restfull.connections.Errors.RiotApiError;
import com.kingskull.lolapplication.api.restfull.connections.RIOT;
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

    public ChampionApiCall() {
        this.restAdapter = new RestAdapter.Builder()
                .setEndpoint(RIOT.END_POINT)
                .build();

        this.service = restAdapter.create(LoadingService.class);
    }

    public void getChampionById(int id){
        service.getChampionById(id, RIOT.REGION, "image,stats,tags", RIOT.API_KEY, new Callback<Champion>() {
            @Override
            public void success(Champion champion, Response response) {
                BusProvider.post(new ChampionResponse(champion));
            }

            @Override
            public void failure(RetrofitError error) {
                BusProvider.post(new RiotApiError("Error al cargar cammpeon, puede que no sea un campeon valido"));
            }
        });
    }
}
