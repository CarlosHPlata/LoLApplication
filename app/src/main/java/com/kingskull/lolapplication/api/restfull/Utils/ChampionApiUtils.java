package com.kingskull.lolapplication.api.restfull.Utils;

import com.kingskull.lolapplication.api.observer.BusProvider;
import com.kingskull.lolapplication.api.restfull.connections.StaticInfo;
import com.kingskull.lolapplication.api.restfull.connections.responses.ChampionResponse;
import com.kingskull.lolapplication.models.pojos.Champion.Champion;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

/**
 * Created by Usuario on 22/09/2015.
 */
public class ChampionApiUtils {
    private Bus bus;

    public ChampionApiUtils() {
        this.bus = BusProvider.getInstance();
        bus.register(this);
    }

    public void getChampionInfo(int id){
        Champion champion = getChampionFromMap(id);

        if (champion != null){
            bus.post(champion);
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
        ChampionApiCall championApiCall = new ChampionApiCall();
        championApiCall.getChampionById(id);
    }

    @Subscribe
    public void getChampionFromAPi(ChampionResponse championResponse){
        Champion champion = championResponse.getChampion();
        BusProvider.post( champion );
    }



}
