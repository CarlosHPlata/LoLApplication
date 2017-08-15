package com.kingskull.lolapplication.controllers;

import android.content.Context;

import com.kingskull.lolapplication.controllers.utils.SessionManager;
import com.kingskull.lolapplication.models.database.entities.SummonerEntitie;
import com.kingskull.lolapplication.models.database.mappers.DBManaer;
import com.kingskull.lolapplication.models.pojos.Summoner;

/**
 * Created by Carlos on 12/10/2015.
 */
public class SyncronizeController {

    private Context context;

    public SyncronizeController(Context context) {
        this.context = context;
    }

    // SearchSummoner
    public boolean isSummonerOnDB(String summonerName, String region){
        SummonerListController controller = new SummonerListController(context);
        SummonerEntitie entitie = controller.getSummonerEntitieByName( summonerName, region );

        if (entitie.getId() == -1)
            return false;
        else
            return true;
    }



    // Final stage

    public void generateSession(Summoner summoner, String region){
        long id = summoner.getId();
        SessionManager sessionManager = new SessionManager(context);
        sessionManager.logIn(id, region);
    }

    public void saveOnDb(Summoner summoner, String region){
        SummonerEntitie entitie = new SummonerEntitie();

        entitie.setId(summoner.getId());
        entitie.setSummonername(summoner.getName());
        entitie.setRegion(region);

        DBManaer<SummonerEntitie> dbManaer = new DBManaer<>(context);
        dbManaer.insertOrUpdate(entitie, entitie.getContentValuesWithId());
    }

}
