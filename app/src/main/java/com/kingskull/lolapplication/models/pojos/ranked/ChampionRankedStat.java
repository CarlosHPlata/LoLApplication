package com.kingskull.lolapplication.models.pojos.ranked;

/**
 * Created by Usuario on 07/09/2015.
 */
public class ChampionRankedStat implements java.io.Serializable{

    private Integer id = 0;
    private AggregatedStats stats =  new AggregatedStats();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AggregatedStats getStats() {
        return stats;
    }

    public void setStats(AggregatedStats stats) {
        this.stats = stats;
    }
}
