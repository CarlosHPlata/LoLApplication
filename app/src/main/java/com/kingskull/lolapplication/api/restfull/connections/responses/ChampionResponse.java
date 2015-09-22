package com.kingskull.lolapplication.api.restfull.connections.responses;

import com.kingskull.lolapplication.models.pojos.Champion.Champion;

/**
 * Created by Usuario on 22/09/2015.
 */
public class ChampionResponse {

    private Champion champion;

    public ChampionResponse(Champion champion) {
        this.champion = champion;
    }

    public Champion getChampion() {
        return champion;
    }

    public void setChampion(Champion champion) {
        this.champion = champion;
    }
}
