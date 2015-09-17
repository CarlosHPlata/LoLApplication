package com.kingskull.lolapplication.models.pojos.league;

/**
 * Created by Usuario on 07/09/2015.
 */
public class Entry implements java.io.Serializable{


    private String division = "";
    private Boolean isFreshBlood = false;
    private Boolean isHotStreak = false;
    private Boolean isInactive = true;
    private Boolean isVeteran = false;
    private Integer leaguePoints = 0;
    private Integer losses = 0;
    private MiniSeries miniSeries = new MiniSeries();
    private String playerOrTeamId = "";
    private String playerOrTeamName = "";
    private Integer wins = 0;

    public MiniSeries getMiniSeries() {
        return miniSeries;
    }

    public void setMiniSeries(MiniSeries miniSeries) {
        this.miniSeries = miniSeries;
    }

    /**
     *
     * @return
     * The leaguePoints
     */
    public Integer getLeaguePoints() {
        return leaguePoints;
    }

    /**
     *
     * @param leaguePoints
     * The leaguePoints
     */
    public void setLeaguePoints(Integer leaguePoints) {
        this.leaguePoints = leaguePoints;
    }

    /**
     *
     * @return
     * The isFreshBlood
     */
    public Boolean getIsFreshBlood() {
        return isFreshBlood;
    }

    /**
     *
     * @param isFreshBlood
     * The isFreshBlood
     */
    public void setIsFreshBlood(Boolean isFreshBlood) {
        this.isFreshBlood = isFreshBlood;
    }

    /**
     *
     * @return
     * The isHotStreak
     */
    public Boolean getIsHotStreak() {
        return isHotStreak;
    }

    /**
     *
     * @param isHotStreak
     * The isHotStreak
     */
    public void setIsHotStreak(Boolean isHotStreak) {
        this.isHotStreak = isHotStreak;
    }

    /**
     *
     * @return
     * The division
     */
    public String getDivision() {
        return division;
    }

    /**
     *
     * @param division
     * The division
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     *
     * @return
     * The isInactive
     */
    public Boolean getIsInactive() {
        return isInactive;
    }

    /**
     *
     * @param isInactive
     * The isInactive
     */
    public void setIsInactive(Boolean isInactive) {
        this.isInactive = isInactive;
    }

    /**
     *
     * @return
     * The isVeteran
     */
    public Boolean getIsVeteran() {
        return isVeteran;
    }

    /**
     *
     * @param isVeteran
     * The isVeteran
     */
    public void setIsVeteran(Boolean isVeteran) {
        this.isVeteran = isVeteran;
    }

    /**
     *
     * @return
     * The losses
     */
    public Integer getLosses() {
        return losses;
    }

    /**
     *
     * @param losses
     * The losses
     */
    public void setLosses(Integer losses) {
        this.losses = losses;
    }

    /**
     *
     * @return
     * The playerOrTeamName
     */
    public String getPlayerOrTeamName() {
        return playerOrTeamName;
    }

    /**
     *
     * @param playerOrTeamName
     * The playerOrTeamName
     */
    public void setPlayerOrTeamName(String playerOrTeamName) {
        this.playerOrTeamName = playerOrTeamName;
    }

    /**
     *
     * @return
     * The playerOrTeamId
     */
    public String getPlayerOrTeamId() {
        return playerOrTeamId;
    }

    /**
     *
     * @param playerOrTeamId
     * The playerOrTeamId
     */
    public void setPlayerOrTeamId(String playerOrTeamId) {
        this.playerOrTeamId = playerOrTeamId;
    }

    /**
     *
     * @return
     * The wins
     */
    public Integer getWins() {
        return wins;
    }

    /**
     *
     * @param wins
     * The wins
     */
    public void setWins(Integer wins) {
        this.wins = wins;
    }


}
