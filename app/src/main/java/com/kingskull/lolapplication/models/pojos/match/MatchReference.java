package com.kingskull.lolapplication.models.pojos.match;

/**
 * Created by cherrera on 9/24/2015.
 */
public class MatchReference {

    private long champion;
    private String lane;
    private long matchId;
    private String patformId;
    private String queue;
    private String role;
    private String season;
    private long timestamp;

    public long getChampion() {
        return champion;
    }

    public void setChampion(long champion) {
        this.champion = champion;
    }

    public String getLane() {
        return lane;
    }

    public void setLane(String lane) {
        this.lane = lane;
    }

    public long getMatchId() {
        return matchId;
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }

    public String getPatformId() {
        return patformId;
    }

    public void setPatformId(String patformId) {
        this.patformId = patformId;
    }

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
