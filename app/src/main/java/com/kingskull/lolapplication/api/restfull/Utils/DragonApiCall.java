package com.kingskull.lolapplication.api.restfull.Utils;

import android.util.Log;

import com.kingskull.lolapplication.api.observer.BusProvider;
import com.kingskull.lolapplication.api.restfull.connections.Errors.RiotApiError;
import com.kingskull.lolapplication.api.restfull.connections.RIOT;
import com.kingskull.lolapplication.api.restfull.connections.StaticInfo;
import com.kingskull.lolapplication.api.restfull.connections.responses.ListOfVersions;
import com.kingskull.lolapplication.api.restfull.services.DragonService;
import com.kingskull.lolapplication.models.pojos.DragonVersion.Realm;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Carlos on 07/05/2016.
 */
public class DragonApiCall {

    RestAdapter restAdapter;
    DragonService service;

    public DragonApiCall () {
        String endpoint = StaticInfo.getInstance().getEndPoints().get("global");

        this.restAdapter = new RestAdapter.Builder()
                .setEndpoint(endpoint)
                .build();

        this.service = restAdapter.create(DragonService.class);
    }

    public void getDragonCurrentVersion(String region){
        service.getActiveVersion(region, StaticInfo.getInstance().getApiKey(), new Callback<Realm>() {
            @Override
            public void success(Realm realm, Response response) {
                BusProvider.post(realm);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("SYNERGY ERROR", error.getUrl());
                Log.e("SYNERGY ERROR", error.getMessage());
                BusProvider.post(new RiotApiError("Error al encontrar dragon actualizado"));
            }
        });
    }

    public void getListOfVersions(String region){
        service.getVersionsList(region, StaticInfo.getInstance().getApiKey(), new Callback<ArrayList<String>>() {
            @Override
            public void success(ArrayList<String> versions, Response response) {
                BusProvider.post(new ListOfVersions(versions));
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("SYNERGY ERROR", error.getUrl());
                Log.e("SYNERGY ERROR", error.getMessage());
                BusProvider.post(new RiotApiError("Error al encontrar versiones"));
            }
        });
    }
}
