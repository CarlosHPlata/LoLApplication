package com.kingskull.lolapplication.controllers.utils;

import com.kingskull.lolapplication.models.pojos.league.Entry;
import com.kingskull.lolapplication.models.pojos.ranked.ChampionRankedStat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Usuario on 07/09/2015.
 */
public class SummonerUtils {

    final int WIN_RATE_PERCENTAGE = 45, KDA_PERCENTEGE = 10, CSS_PERCENTEGE = 17, GOLD_PERCENTEGE = 23;
    final double WIN_RATE_PERCENTAGE_TODOWN = 1;
    final double WIN_RATE_MAX = 70, KDA_MAX = 2.5, CSS_MAX = 270, GOLD_MAX = 16000;

    public int calculateWinRate(int wins, int losses){
        int total = wins + losses;
        return ( (wins*100)/ (total == 0? 1:total) );
    }

    public String getMinionsPerGame(Entry entry, ChampionRankedStat stat){
        int totalGames = entry.getWins() + entry.getLosses();
        int minions_total = stat.getStats().getTotalNeutralMinionsKilled() + stat.getStats().getTotalMinionKills();
        int minions_game = minions_total/(totalGames == 0? 1:totalGames);
        return minions_game + "";
    }

    public String getGoldPerGame(Entry entry, ChampionRankedStat stat){
        int totalGames = entry.getWins() + entry.getLosses();
        int gold_total = stat.getStats().getTotalGoldEarned();
        int gold_game = gold_total/(totalGames == 0? 1:totalGames);

        return gold_game + "";
    }

    public String getTurretsPerGame(Entry entry, ChampionRankedStat stat){
        int totalGames = entry.getWins() + entry.getLosses();
        int turrets = stat.getStats().getTotalTurretsKilled();
        int result = turrets/(totalGames == 0? 1:totalGames);
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

    public double KDARatio(ChampionRankedStat stat){

        double valueKill = stat.getStats().getTotalChampionKills();
        double valueAssist = stat.getStats().getTotalAssists();
        double valueDeaths = stat.getStats().getTotalDeathsPerSession();

        double result = (new ScoreCalculator()).calculateKDARatio(valueKill, valueDeaths, valueAssist);


        return result;
    }

    public double timePlayedPerMatch(double timePlayed, int totalGames){
        return (timePlayed/totalGames);
    }

    public double getPerformance(ChampionRankedStat stat){
        ScoreCalculator scoreCalculator = new ScoreCalculator();
        int totalGames = stat.getStats().getTotalSessionsPlayed();

        int minions_total = stat.getStats().getTotalNeutralMinionsKilled() + stat.getStats().getTotalMinionKills();
        int minions_game = minions_total/(totalGames == 0? 1:totalGames);

        int wins = stat.getStats().getTotalSessionsWon();
        int loses = stat.getStats().getTotalSessionsLost();
        int matchsTotal = wins + loses;
        int win_rate = this.calculateWinRate(wins, loses);

        double valueKill = stat.getStats().getTotalChampionKills();
        double valueAssist = stat.getStats().getTotalAssists()*.3;
        double valueDeaths = stat.getStats().getTotalDeathsPerSession();
        double kda = (valueKill + valueAssist)/valueDeaths;
        double timePerMatch = stat.getStats().getMaxTimePlayed();
        timePerMatch = timePerMatch > 2400? 2400 : timePerMatch;

        int gold_total = stat.getStats().getTotalGoldEarned();
        int gold_game = gold_total/ (totalGames == 0? 1:totalGames);

        double win_rate_value = (win_rate * WIN_RATE_PERCENTAGE) / WIN_RATE_MAX;
        if (matchsTotal > 5){
            win_rate_value = win_rate_value > WIN_RATE_PERCENTAGE? WIN_RATE_PERCENTAGE : win_rate_value;
        } else {
            if (matchsTotal == 1) win_rate_value = -10;
            else {
                double maxpercentageperwindown = WIN_RATE_PERCENTAGE_TODOWN * matchsTotal;
                win_rate_value = (win_rate * maxpercentageperwindown) / WIN_RATE_MAX;
                win_rate_value = win_rate_value > maxpercentageperwindown? maxpercentageperwindown : win_rate_value;
            }
        }

        double kda_value = (kda * KDA_PERCENTEGE)/KDA_MAX;
        kda_value = kda_value > KDA_PERCENTEGE? KDA_PERCENTEGE : kda_value;

        double gold_value = (gold_game * GOLD_PERCENTEGE) / scoreCalculator.calculateGoldPerTime(timePerMatch);
        gold_value = gold_value >  GOLD_PERCENTEGE ? GOLD_PERCENTEGE : gold_value;

        double minions_value =  (minions_game * CSS_PERCENTEGE) / scoreCalculator.calculateMinionsPerTime(timePerMatch);
        minions_value = minions_value > CSS_PERCENTEGE ? CSS_PERCENTEGE : minions_value;

        double performance = win_rate_value + kda_value + gold_value + minions_value;

        return performance;
    }

    public List<ChampionRankedStat> orderByPerformance(List<ChampionRankedStat> champsNotSorted){
        List<ChampionRankedStat> champsSorted = new ArrayList<ChampionRankedStat>();

        int idChampMostPerformance=0;
        double performanceMax = -1;
        double tempPerformance;

        while (champsNotSorted.size() > 0){

            for (int i=0; i < champsNotSorted.size(); i++){

                tempPerformance = getPerformance( champsNotSorted.get(i) );

                if ( tempPerformance > performanceMax ){
                    performanceMax = tempPerformance;
                    idChampMostPerformance = i;
                }
            }

            champsSorted.add( champsNotSorted.get(idChampMostPerformance) );
            champsNotSorted.remove(idChampMostPerformance);

            idChampMostPerformance=0;
            performanceMax = -1;
        }

        return champsSorted;
    }

}
