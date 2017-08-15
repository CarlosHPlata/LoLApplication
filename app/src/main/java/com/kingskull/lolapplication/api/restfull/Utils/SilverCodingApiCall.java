package com.kingskull.lolapplication.api.restfull.Utils;

import android.util.Log;

import com.kingskull.lolapplication.api.observer.BusProvider;
import com.kingskull.lolapplication.api.restfull.connections.responses.KeyResponse;
import com.kingskull.lolapplication.api.restfull.services.SilverCodingService;


import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Carlos on 15/05/2016.
 */
public class SilverCodingApiCall {
    RestAdapter restAdapter;
    SilverCodingService service;

    public SilverCodingApiCall(){
        String endpoint = "http://synergylol.silvercoding.com/";

        this.restAdapter = new RestAdapter.Builder()
                .setEndpoint(endpoint)
                .build();

        this.service = restAdapter.create(SilverCodingService.class);
    }

    public void getKey(String type){
        service.getApiKey(type, new retrofit.Callback<String>() {
            @Override
            public void success(String s, Response response) {
                BusProvider.post(new KeyResponse(s));
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("SYNERGY", "ERROR");
            }
        });
    }
}
