package com.kingskull.lolapplication.api.restfull.Utils;

import android.util.Log;

import com.kingskull.lolapplication.api.observer.BusProvider;
import com.kingskull.lolapplication.api.restfull.connections.RIOT;
import com.kingskull.lolapplication.api.restfull.connections.Errors.RiotApiError;
import com.kingskull.lolapplication.api.restfull.connections.StaticInfo;
import com.kingskull.lolapplication.api.restfull.connections.responses.SummonerResponse;
import com.kingskull.lolapplication.api.restfull.services.SummonerService;
import com.kingskull.lolapplication.models.pojos.Summoner;
import com.kingskull.lolapplication.models.pojos.league.LeagueStat;
import com.kingskull.lolapplication.models.pojos.match.MatchList;
import com.kingskull.lolapplication.models.pojos.ranked.RankedStat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Usuario on 12/09/2015.
 */
public class SummonerApiCall {

    RestAdapter restAdapter;
    SummonerService service;

    public SummonerApiCall(String region){
        String endpoint = StaticInfo.getInstance().getEndPoints().get(region);

        this.restAdapter = new RestAdapter.Builder()
                .setEndpoint(endpoint)
                .build();

        this.service = restAdapter.create(SummonerService.class);
    }

    public void getSummonerById(final long id, final String region){
        service.getSummonerById(id, region, StaticInfo.getInstance().getApiKey(), new Callback<HashMap<String, Summoner>>() {
            @Override
            public void success(HashMap<String, Summoner> stringSummonerHashMap, Response response) {
                Summoner mySummoner = stringSummonerHashMap.get(id + "");
                mySummoner.setLastUpdate((new Date()).getTime());
                getSummonnerRankedStat(mySummoner, region);
            }

            @Override
            public void failure(RetrofitError error) {
                BusProvider.post(new RiotApiError(error.getMessage()));
            }
        });

    }

    public void getSummonerByName(final String summonerName, final String region){
        service.getSummonner(summonerName, region, StaticInfo.getInstance().getApiKey(), new Callback<HashMap<String, Summoner>>() {
            @Override
            public void success(HashMap<String, Summoner> summoners, retrofit.client.Response response) {
                getSummonnerRankedStat(summoners.get(summonerName), region);
            }

            @Override
            public void failure(RetrofitError error) {
                BusProvider.post(new RiotApiError(error.getMessage()));
            }
        });
    }

    private void getSummonnerRankedStat(final Summoner summoner, final String region){
        service.getRankedStats(summoner.getId(), region, RIOT.SEASON_6, StaticInfo.getInstance().getApiKey(), new Callback<RankedStat>() {
            @Override
            public void success(RankedStat rankedStat, Response response) {
                Log.i("SYNERGY", response.getUrl());
                summoner.setRankedStat(rankedStat);
                getSummonerMatchHistoryStats(summoner, region);
            }

            @Override
            public void failure(RetrofitError error) {
                summoner.setRankedStat(new RankedStat());
                getSummonerMatchHistoryStats(summoner, region);
            }
        });
    }

    private void getSummonerMatchHistoryStats(final Summoner summoner, final String region){
        service.getMatchHistory(summoner.getId(), region, RIOT.NEW_RANKED + "," + RIOT.SOLO_Q + "," + RIOT.TEAM_5X5 + "," + RIOT.TEAM_3X3,
                RIOT.ACTIVE_SEASON_SEARCH, 0, 20, StaticInfo.getInstance().getApiKey(), new Callback<MatchList>() {
                    @Override
                    public void success(MatchList matchList, Response response) {
                        summoner.setMatchList(matchList);
                        getSummonerLeagueStats(summoner, region);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        summoner.setMatchList(new MatchList());
                        getSummonerLeagueStats(summoner, region);
                    }
                });
    }

    private void getSummonerLeagueStats(final Summoner summoner, String region){
        service.getLeagueStats(summoner.getId()+"", region, StaticInfo.getInstance().getApiKey(), new Callback<HashMap<String, ArrayList<LeagueStat>>>() {
            @Override
            public void success(HashMap<String, ArrayList<LeagueStat>> stringArrayListHashMap, Response response) {
                ArrayList<LeagueStat> listleagues = stringArrayListHashMap.get(summoner.getId()+"");
                LeagueStat league =  new LeagueStat();
                for (LeagueStat tempLeague : listleagues){
                    if (tempLeague.getQueue().equalsIgnoreCase(RIOT.SOLO_Q)){
                        league = tempLeague;
                        break;
                    }
                }

                summoner.setLeagueStat(league);
                BusProvider.post(new SummonerResponse(summoner));
            }

            @Override
            public void failure(RetrofitError error) {
                summoner.setLeagueStat(new LeagueStat());
                BusProvider.post(new SummonerResponse(summoner));
            }
        });
    }

    public void getSummonerTemp(final long id, String region){
        service.getSummonerById(id, region, StaticInfo.getInstance().getApiKey(), new Callback<HashMap<String, Summoner>>() {
            @Override
            public void success(HashMap<String, Summoner> stringSummonerHashMap, Response response) {
                BusProvider.post( stringSummonerHashMap.get(id+"") );
            }

            @Override
            public void failure(RetrofitError error) {
                BusProvider.post(new RiotApiError(error.getMessage()));
            }
        });
    }

}
