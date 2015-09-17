package com.kingskull.lolapplication.api.restfull.Utils;

import com.kingskull.lolapplication.api.observer.BusProvider;
import com.kingskull.lolapplication.api.restfull.connections.RIOT;
import com.kingskull.lolapplication.api.restfull.connections.Errors.RiotApiError;
import com.kingskull.lolapplication.api.restfull.connections.responses.SummonerResponse;
import com.kingskull.lolapplication.api.restfull.services.SummonerService;
import com.kingskull.lolapplication.models.pojos.Summoner;
import com.kingskull.lolapplication.models.pojos.league.LeagueStat;
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

    public SummonerApiCall(){
        this.restAdapter = new RestAdapter.Builder()
                .setEndpoint(RIOT.END_POINT)
                .build();

        this.service = restAdapter.create(SummonerService.class);
    }

    public void getSummonerById(final long id){
        service.getSummonerById(id, RIOT.REGION, RIOT.API_KEY, new Callback<HashMap<String, Summoner>>() {
            @Override
            public void success(HashMap<String, Summoner> stringSummonerHashMap, Response response) {
                Summoner mySummoner = stringSummonerHashMap.get(id + "");
                mySummoner.setLastUpdate( (new Date()).getTime() );
                getSummonnerRankedStat(mySummoner);
            }

            @Override
            public void failure(RetrofitError error) {
                BusProvider.post(new RiotApiError(error.getMessage()));
            }
        });

    }

    public void getSummonerByName(final String summonerName){
        service.getSummonner(summonerName, RIOT.REGION, RIOT.API_KEY, new Callback<HashMap<String, Summoner>>() {
            @Override
            public void success(HashMap<String, Summoner> summoners, retrofit.client.Response response) {
                getSummonnerRankedStat(summoners.get(summonerName));
            }

            @Override
            public void failure(RetrofitError error) {
                BusProvider.post(new RiotApiError(error.getMessage()));
            }
        });
    }

    private void getSummonnerRankedStat(final Summoner summoner){
        service.getRankedStats(summoner.getId(), RIOT.REGION, RIOT.SEASON_5, RIOT.API_KEY, new Callback<RankedStat>() {
            @Override
            public void success(RankedStat rankedStat, Response response) {
                summoner.setRankedStat(rankedStat);
                getSummonerLeagueStats(summoner);
            }

            @Override
            public void failure(RetrofitError error) {
                summoner.setRankedStat(new RankedStat());
                getSummonerLeagueStats(summoner);
            }
        });
    }

    private void getSummonerLeagueStats(final Summoner summoner){
        service.getLeagueStats(summoner.getId()+"",RIOT.REGION, RIOT.API_KEY, new Callback<HashMap<String, ArrayList<LeagueStat>>>() {
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

    public void getSummonerTemp(final long id){
        service.getSummonerById(id, RIOT.REGION, RIOT.API_KEY, new Callback<HashMap<String, Summoner>>() {
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
