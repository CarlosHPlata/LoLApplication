package com.kingskull.lolapplication.api.restfull.Utils;

import android.util.Log;

import com.kingskull.lolapplication.api.observer.BusProvider;
import com.kingskull.lolapplication.api.restfull.connections.Errors.RiotApiError;
import com.kingskull.lolapplication.api.restfull.connections.RIOT;
import com.kingskull.lolapplication.api.restfull.connections.StaticInfo;
import com.kingskull.lolapplication.api.restfull.connections.responses.MatchResponse;
import com.kingskull.lolapplication.api.restfull.services.MatchService;
import com.kingskull.lolapplication.models.pojos.matchinfo.MatchDetail;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by cherrera on 2/2/2016.
 */
public class MatchApiCall {

    RestAdapter restAdapter;
    MatchService service;

    public MatchApiCall(String region) {
        String endpoint = StaticInfo.getInstance().getEndPoints().get(region.toLowerCase());

        this.restAdapter = new RestAdapter.Builder()
                .setEndpoint(endpoint)
                .build();

        this.service =  restAdapter.create(MatchService.class);
    }

    public void getMatchInfo(long id, String region){
        service.getMatchDetails(id, region, true, StaticInfo.getInstance().getApiKey(), new Callback<MatchDetail>() {
            @Override
            public void success(MatchDetail matchDetail, Response response) {
                Log.i("SYNERGY", response.getUrl());
                BusProvider.post(new MatchResponse(matchDetail));
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("SYNERGY ERROR", error.getUrl());
                Log.e("SYNERGY ERROR", error.getMessage());
                BusProvider.post(new RiotApiError("Error al encontrar informacion de la partida"));
            }
        });
    }
}
