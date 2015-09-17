package com.kingskull.lolapplication.controllers;

import com.kingskull.lolapplication.models.pojos.league.Entry;
import com.kingskull.lolapplication.models.pojos.ranked.ChampionRankedStat;

/**
 * Created by Usuario on 07/09/2015.
 */
public class SummonerUtils {

    public int calculateWinRate(int wins, int losses){
        int total = wins + losses;
        return ( (wins*100)/total );
    }

    public String getMinionsPerGame(Entry entry, ChampionRankedStat stat){
        int totalGames = entry.getWins() + entry.getLosses();
        int minions_total = stat.getStats().getTotalNeutralMinionsKilled() + stat.getStats().getTotalMinionKills();
        int minions_game = minions_total/totalGames;
        return minions_game + "";
    }

    public String getGoldPerGame(Entry entry, ChampionRankedStat stat){
        int totalGames = entry.getWins() + entry.getLosses();
        int gold_total = stat.getStats().getTotalGoldEarned();
        int gold_game = gold_total/totalGames;

        return gold_game + "";
    }

    public String getTurretsPerGame(Entry entry, ChampionRankedStat stat){
        int totalGames = entry.getWins() + entry.getLosses();
        int turrets = stat.getStats().getTotalTurretsKilled();
        int result = turrets/totalGames;
        return result + "";
    }

    public String getKills(ChampionRankedStat stat){
        String result = "";
        if (stat.getStats().getTotalChampionKills()>999){
            result = (stat.getStats().getTotalChampionKills()/1000) + "K";
        } else {
            result = stat.getStats().getTotalChampionKills() + "";
        }

        return result+"\nK";
    }


    public String getDeaths(ChampionRankedStat stat){
        String result = "";
        int var = stat.getStats().getTotalDeathsPerSession();
        if (var>999){
            result = (var/1000) + "K";
        } else {
            result = var + "";
        }

        return result+"\nD";
    }

    public String getAssists(ChampionRankedStat stat){
        String result = "";
        int var = stat.getStats().getTotalAssists();
        if (var>999){
            result = (var/1000) + "K";
        } else {
            result = var + "";
        }

        return result+"\nA";
    }

    public String KDARatio(ChampionRankedStat stat){

        double valueKill = stat.getStats().getTotalChampionKills();
        double valueAssist = stat.getStats().getTotalAssists()*.3;
        double valueDeaths = stat.getStats().getTotalDeathsPerSession();

        double result = (valueKill + valueAssist)/valueDeaths;

        return result+"";
    }

}
