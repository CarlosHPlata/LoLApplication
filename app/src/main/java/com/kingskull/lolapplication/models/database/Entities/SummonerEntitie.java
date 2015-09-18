package com.kingskull.lolapplication.models.database.entities;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by cherrera on 9/18/2015.
 */
public class SummonerEntitie extends Entitie{

    private String summonername;
    private String region;

    private static final String TABLE_NAME = "summoners";
    private static final String[] COLUM_NAMES = {"id", "summonername", "region"};
    private static final int ID_POSITION = 0, SUMMONERNAME_POSITION=1, REGION_POSITION=2;


    public SummonerEntitie(){
        super(TABLE_NAME, COLUM_NAMES);
    }

    public SummonerEntitie(long id){
        super(id, TABLE_NAME, COLUM_NAMES);
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues content = new ContentValues();

        content.put( COLUM_NAMES[SUMMONERNAME_POSITION], summonername );
        content.put( COLUM_NAMES[REGION_POSITION], region );

        return content;
    }

    @Override
    public void setContentValues(Cursor cursor) {
        setId( cursor.getLong(ID_POSITION) );
        setSummonername( cursor.getString(SUMMONERNAME_POSITION) );
        setRegion( cursor.getString(REGION_POSITION));
    }

    @Override
    public Entitie getNewInstance() {
        return new SummonerEntitie();
    }

    public String getSummonername() {
        return summonername;
    }

    public void setSummonername(String summonername) {
        this.summonername = summonername;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
