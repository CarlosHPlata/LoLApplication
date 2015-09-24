package com.kingskull.lolapplication.models.pojos;

import com.kingskull.lolapplication.models.pojos.league.LeagueStat;
import com.kingskull.lolapplication.models.pojos.match.MatchList;
import com.kingskull.lolapplication.models.pojos.ranked.RankedStat;

/**
 * Created by Usuario on 07/09/2015.
 */
public class Summoner implements java.io.Serializable{

    private long id;
    private String name;
    private Integer profileIconId;
    private long revisionDate;
    private long summonerLevel;
    private long lastUpdate;

    private RankedStat rankedStat;
    private LeagueStat leagueStat;
    private MatchList matchList;

    public MatchList getMatchList() {
        return matchList;
    }

    public void setMatchList(MatchList matchList) {
        this.matchList = matchList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getProfileIconId() {
        return profileIconId;
    }

    public void setProfileIconId(Integer profileIconId) {
        this.profileIconId = profileIconId;
    }

    public long getRevisionDate() {
        return revisionDate;
    }

    public void setRevisionDate(long revisionDate) {
        this.revisionDate = revisionDate;
    }

    public long getSummonerLevel() {
        return summonerLevel;
    }

    public void setSummonerLevel(long summonerLevel) {
        this.summonerLevel = summonerLevel;
    }

    public RankedStat getRankedStat() {
        return rankedStat;
    }

    public void setRankedStat(RankedStat rankedStat) {
        this.rankedStat = rankedStat;
    }

    public LeagueStat getLeagueStat() {
        return leagueStat;
    }

    public void setLeagueStat(LeagueStat leagueStat) {
        this.leagueStat = leagueStat;
    }

    public long getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
