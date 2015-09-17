package com.kingskull.lolapplication.models.pojos.league;

/**
 * Created by Usuario on 07/09/2015.
 */
public class MiniSeries implements java.io.Serializable{

    private Integer losses = 0;
    private String progress = "";
    private Integer target = 0;
    private Integer wins = 0;

    public Integer getLosses() {
        return losses;
    }

    public void setLosses(Integer losses) {
        this.losses = losses;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public Integer getTarget() {
        return target;
    }

    public void setTarget(Integer target) {
        this.target = target;
    }

    public Integer getWins() {
        return wins;
    }

    public void setWins(Integer wins) {
        this.wins = wins;
    }
}
