package com.kingskull.lolapplication.controllers;

import android.content.Context;

import com.kingskull.lolapplication.api.observer.BusProvider;
import com.kingskull.lolapplication.api.restfull.Utils.DragonApiCall;
import com.kingskull.lolapplication.api.restfull.connections.StaticInfo;
import com.kingskull.lolapplication.api.restfull.connections.responses.DragonVersionCached;
import com.kingskull.lolapplication.api.restfull.connections.responses.ListOfVersions;
import com.kingskull.lolapplication.api.restfull.connections.responses.ListOfVersionsReady;
import com.kingskull.lolapplication.models.pojos.DragonVersion.Realm;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.List;

/**
 * Created by Carlos on 07/05/2016.
 */
public class MainLoadController {

    private Context context;

    DragonApiCall dragonApiCall = null;

    public MainLoadController(Context context){
        this.context = context;
    }

    public void getApiKey(){
        //por terminar
    }




    //================= GET ACTIVE DRAGON VERSION =============================

    public void getActiveDragonVersion(String region){
        BusProvider.register(this);
        dragonApiCall = this.dragonApiCall == null? new DragonApiCall():this.dragonApiCall;

        dragonApiCall.getDragonCurrentVersion(region);
    }

    @Subscribe
    public void getActiveDragonVersionResponse(Realm dragonVersion){
        StaticInfo staticInfo = StaticInfo.getInstance();
        staticInfo.setDragonVersion(dragonVersion);

        BusProvider.unRegister(this);
        BusProvider.post(new DragonVersionCached(dragonVersion));
    }

    //==========================================================================





    //======================== GET DRAGON VERSIONS =============================

    public void getListOfDragonVersion(String region){
        BusProvider.register(this);
        dragonApiCall = this.dragonApiCall == null? new DragonApiCall():this.dragonApiCall;
        dragonApiCall.getListOfVersions(region);
    }

    @Subscribe
    public void getListOfVersionsResponse(ListOfVersions response){
        List<String> versions = response.getVersions();

        StaticInfo staticInfo = StaticInfo.getInstance();
        staticInfo.setDragonVersions(versions);

        BusProvider.unRegister(this);
        BusProvider.post(new ListOfVersionsReady(versions));
    }
    //==========================================================================
    
}
