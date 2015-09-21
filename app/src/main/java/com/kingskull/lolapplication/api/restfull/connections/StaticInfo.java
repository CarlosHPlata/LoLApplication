package com.kingskull.lolapplication.api.restfull.connections;

import com.kingskull.lolapplication.models.database.entities.SummonerEntitie;

/**
 * Created by Usuario on 19/09/2015.
 */
public class StaticInfo {

    private String apiKey;
    private SummonerEntitie summonerEntitie;

    private static StaticInfo staticInfo;

    private StaticInfo(){}

    public static StaticInfo getInstance(){
        if (staticInfo == null){
            staticInfo =  new StaticInfo();
        }
        return staticInfo;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public SummonerEntitie getSummonerEntitie() {
        return summonerEntitie;
    }

    public void setSummonerEntitie(SummonerEntitie summonerEntitie) {
        this.summonerEntitie = summonerEntitie;
    }
}
