package com.kingskull.lolapplication.api.restfull.Utils;

import com.kingskull.lolapplication.models.pojos.Summoner;

import java.io.Serializable;

/**
 * Created by Usuario on 12/09/2015.
 */
public class SummonerCache implements Serializable {

    private static Summoner summonerCache;

    public static Summoner getSummonerCache(){
        return summonerCache;
    }

    public static void setSummonerCache(Summoner summoner){
        summonerCache = summoner;
    }

}
