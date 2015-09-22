package com.kingskull.lolapplication.api.restfull.Utils;

import android.content.Context;
import android.os.AsyncTask;

import com.kingskull.lolapplication.api.observer.BusProvider;
import com.kingskull.lolapplication.api.restfull.connections.Errors.RiotApiError;
import com.kingskull.lolapplication.api.restfull.connections.responses.SummonerNameResponse;
import com.kingskull.lolapplication.api.restfull.connections.responses.SummonerResponse;
import com.kingskull.lolapplication.models.pojos.Summoner;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.io.IOException;
import java.util.Date;

/**
 * Created by Usuario on 12/09/2015.
 */
public class SummonerApiUtils {

    private Bus bus;
    private Context context;

    public SummonerApiUtils(Context context){
        this.bus = BusProvider.getInstance();
        this.context = context;
        bus.register(this);
    }

    public void getSummonerById(long id){
        Process proc = new Process(id);
        proc.execute();
    }

    private Summoner getSummonerInCache(){
        Summoner summoner = SummonerCache.getSummonerCache();
        return summoner;
    }

    private Summoner getSummonerOnDisk(long id){
        try {
            Summoner summoner = (new SummonerStream(context)).load(id);
            return summoner;
        } catch (IOException e) {
            return null;
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    private void getSummonerWithAPI(long id){
        SummonerApiCall api =  new SummonerApiCall();
        api.getSummonerById(id);
    }

    @Subscribe
    public void onApiResponse(SummonerResponse response) {
        Summoner summoner = response.getSummoner();

        bus.post(summoner);

        try {
            ( new SummonerStream(context) ).save(summoner);
        } catch (IOException e) {
            e.printStackTrace();
        }

        SummonerCache.setSummonerCache(summoner);
    }

    private class Process extends AsyncTask<Void, Void, Void> {

        private final long SIXTEN_MINUTE_IN_MILLIS = 60000*45;
        private long id;

        public Process(long id){
            this.id = id;
        }

        @Override
        protected Void doInBackground(Void... params) {
            long id = this.id;

            Summoner summoner = getSummonerInCache();
            if ( summoner != null && summoner.getId() == id && summoner.getLeagueStat() != null
                    && summoner.getLeagueStat().getEntries().size() > 0
                    && summoner.getRankedStat() != null && summoner.getRankedStat().getChampions().size() > 0){

                if ( summoner.getLastUpdate() - (new Date()).getTime() <= this.SIXTEN_MINUTE_IN_MILLIS){
                    bus.post(summoner);
                } else {

                    getSummonerWithAPI(id);

                }

            } else {
                summoner = getSummonerOnDisk(id);

                if ( summoner != null && summoner.getLeagueStat() != null
                        && summoner.getLeagueStat().getEntries().size() > 0
                        && summoner.getRankedStat() != null && summoner.getRankedStat().getChampions().size() > 0){
                    long difference = (new Date()).getTime() - summoner.getLastUpdate();
                    if ( difference <= this.SIXTEN_MINUTE_IN_MILLIS){
                        SummonerCache.setSummonerCache(summoner);
                        bus.post(summoner);
                    } else {
                        getSummonerWithAPI(id);
                    }
                } else {
                    getSummonerWithAPI(id);
                }

            }

            return null;
        }
    }

}
