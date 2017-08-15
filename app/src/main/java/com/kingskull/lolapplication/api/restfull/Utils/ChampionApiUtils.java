package com.kingskull.lolapplication.api.restfull.Utils;

import android.content.Context;

import com.kingskull.lolapplication.api.observer.BusProvider;
import com.kingskull.lolapplication.api.restfull.connections.StaticInfo;
import com.kingskull.lolapplication.api.restfull.connections.responses.ChampionResponse;
import com.kingskull.lolapplication.controllers.utils.SessionManager;
import com.kingskull.lolapplication.models.pojos.Champion.Champion;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

/**
 * Created by Usuario on 22/09/2015.
 */
public class ChampionApiUtils {
    private Context context;

    public ChampionApiUtils(Context context) {
        this.context = context;
    }

    public void getChampionInfo(int id){
        Champion champion = getChampionFromMap(id);

        if (champion != null){
            BusProvider.post(champion);
        } else {
            getChampionFormApi(id);
        }
    }

    private Champion getChampionFromMap(int id){

        StaticInfo info = StaticInfo.getInstance();

        if (info.getChampions().containsKey(id)){
            return info.getChampions().get(id);
        } else {
            return null;
        }
    }


    private void getChampionFormApi(int id){
        BusProvider.register(this);
        String region = SessionManager.getSession(context).getRegion();
        ChampionApiCall championApiCall = new ChampionApiCall(region);
        championApiCall.getChampionById(id, region);
    }

    @Subscribe
    public void getChampionFromAPi(ChampionResponse championResponse){
        Champion champion = championResponse.getChampion();
        StaticInfo.getInstance().getChampions().put(champion.getId(), champion);
        BusProvider.unRegister(this);
        BusProvider.post(champion);
    }



}
