package com.kingskull.lolapplication.models.pojos.matchinfo;

/**
 * Created by cherrera on 1/26/2016.
 */
public class BannedChampion {

    private int championId;
    private int pickTurn;

    public int getPickTurn() {
        return pickTurn;
    }

    public void setPickTurn(int pickTurn) {
        this.pickTurn = pickTurn;
    }

    public int getChampionId() {
        return championId;
    }

    public void setChampionId(int championId) {
        this.championId = championId;
    }
}
