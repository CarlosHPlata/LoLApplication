package com.kingskull.lolapplication.api.restfull.connections.responses;

import com.kingskull.lolapplication.models.pojos.Summoner;

/**
 * Created by Usuario on 12/09/2015.
 */
public class SummonerResponse {

    private Summoner summoner;

    public SummonerResponse(Summoner summoner){
        this.summoner = summoner;
    }

    public Summoner getSummoner() {
        return summoner;
    }

    public void setSummoner(Summoner summoner) {
        this.summoner = summoner;
    }
}
