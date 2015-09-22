package com.kingskull.lolapplication.api.restfull.connections;

import com.kingskull.lolapplication.models.database.entities.SummonerEntitie;
import com.kingskull.lolapplication.models.pojos.Champion.Champion;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Usuario on 19/09/2015.
 */
public class StaticInfo {

    private String apiKey;
    private SummonerEntitie summonerEntitie;
    private Map<Integer, Champion> champions = new HashMap<Integer, Champion>();

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

    public Map<Integer, Champion> getChampions() {
        return champions;
    }

    public void setChampions(Map<Integer, Champion> champions) {
        this.champions = champions;
    }
}
