package com.kingskull.lolapplication.controllers;

import android.content.Context;
import android.content.SharedPreferences;

import com.kingskull.lolapplication.api.restfull.connections.StaticInfo;
import com.kingskull.lolapplication.models.database.entities.SummonerEntitie;
import com.kingskull.lolapplication.models.database.mappers.DBManaer;

import java.util.List;

/**
 * Created by Carlos on 26/09/2015.
 */
public class SummonerListController {

    private Context context;

    public SummonerListController(Context context){
        this.context = context;
    }

    public List<SummonerEntitie> summonersSaved(){
        DBManaer<SummonerEntitie> summonerManager = new DBManaer<SummonerEntitie>(context);
        List<SummonerEntitie> summoners = summonerManager.getAll(new SummonerEntitie());

        return summoners;
    }

    public SummonerEntitie getSummonerById(long id){
        SummonerEntitie result = new SummonerEntitie(id);

        DBManaer<SummonerEntitie> summonerEntitieDBManaer = new DBManaer<SummonerEntitie>(context);

        result = summonerEntitieDBManaer.getById(result);

        return result;
    }

    public SummonerEntitie getCachingSummoner(){
        SharedPreferences preferences = context.getSharedPreferences(StaticInfo.PREFERENCES_NAME, context.MODE_PRIVATE);
        long id = preferences.getLong("id", -1);
        if (id != -1){
            return getSummonerById(id);
        } else {
            return null;
        }
    }

    public SummonerEntitie getSummonerEntitieByName(String summonerName, String region){
        SummonerEntitie result = new SummonerEntitie();
        result.setSummonername(summonerName);
        result.setRegion(region.toLowerCase());

        DBManaer<SummonerEntitie> summonerMapper = new DBManaer<SummonerEntitie>(context);

        result = summonerMapper.getBySummonerName(result);

        return result;
    }

    public void deleteSummoner(SummonerEntitie entitie){
        DBManaer<SummonerEntitie> dbManaer = new DBManaer<>(context);
        dbManaer.delete(entitie);
    }

}
