package com.kingskull.lolapplication.models.pojos.ranked;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Usuario on 07/09/2015.
 */
public class RankedStat implements java.io.Serializable{

    private long modifyDate = 0;
    private List<ChampionRankedStat> champions = new ArrayList<ChampionRankedStat>();
    private long summonerId = 0;

    public long getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(long modifyDate) {
        this.modifyDate = modifyDate;
    }

    public List<ChampionRankedStat> getChampions() {
        return champions;
    }

    public void setChampions(List<ChampionRankedStat> champions) {
        this.champions = champions;
    }

    public long getSummonerId() {
        return summonerId;
    }

    public void setSummonerId(long summonerId) {
        this.summonerId = summonerId;
    }
}
