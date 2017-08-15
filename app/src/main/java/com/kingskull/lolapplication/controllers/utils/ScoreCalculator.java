package com.kingskull.lolapplication.controllers.utils;

import com.kingskull.lolapplication.models.pojos.ranked.ChampionRankedStat;

/**
 * Created by Carlos on 11/05/2016.
 */
public class ScoreCalculator {

    private final double STANDARD_TIME_PLAYED = 20, STANDARD_MINIONS_KILLED = 190, STANDARD_GOLD_OBTAINED = 9000, STANDARD_WARDS = 20;
    //http://es.leagueoflegends.wikia.com/wiki/Farming

    public double calculateMinionsPerTime(double timePlayed){
        timePlayed = timePlayed == 0? 2700 : timePlayed;
        double minutes = (timePlayed) / 60;
        double minionsPerTime = ((minutes * STANDARD_MINIONS_KILLED)/STANDARD_TIME_PLAYED);
        return minionsPerTime;
    }

    public double calculateGoldPerTime(double timePlayed){
        timePlayed = timePlayed == 0? 2700 : timePlayed;
        double minutes = (timePlayed) / 60;
        double goldPerTime = ((minutes * STANDARD_GOLD_OBTAINED) / STANDARD_TIME_PLAYED);
        return goldPerTime;
    }

    public double calculateWardsPerTIme(double timePlayed) {
        return STANDARD_WARDS; //NEEDS MORE RESEARCH AND STATS
    }

    public double calculateGoldPerMinute(double timePlayed, double goldEarned){
        double goldPerMinute = ( (60 * goldEarned) / timePlayed );
        return goldPerMinute;
    }

    public double calculateKDARatio(double kills, double deaths, double assists){
        double result = (kills + (assists * .3)) / deaths;

        return result;
    }

}
