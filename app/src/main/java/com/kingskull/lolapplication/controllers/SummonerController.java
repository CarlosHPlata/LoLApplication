package com.kingskull.lolapplication.controllers;

import android.content.Context;

import com.kingskull.lolapplication.api.restfull.Utils.SummonerApiUtils;
import com.kingskull.lolapplication.api.restfull.services.SummonerService;

import retrofit.RestAdapter;

/**
 * Created by Usuario on 07/09/2015.
 */
public class SummonerController {

    Context context;


    public SummonerController(Context context){
        this.context = context;
    }

    public void getSummonnerStat(final long summonerId){
        SummonerApiUtils summonerApiUtils = new SummonerApiUtils(context);
        summonerApiUtils.getSummonerById(summonerId);
    }



}
