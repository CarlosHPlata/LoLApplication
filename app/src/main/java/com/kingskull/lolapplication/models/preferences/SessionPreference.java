package com.kingskull.lolapplication.models.preferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Carlos on 12/10/2015.
 */
public class SessionPreference {

    private static final String PREFERENCES_SESSION_NAME = "seesion_synergy_lol";

    private static final String ID_FIELD = "id", REGION_FIELD = "region", IS_LOGGED_FIELD = "isLogged";
    private static final long ID_DEFAULT = -1; private static final String REGION_DEFAULT = ""; private static final boolean IS_LOGGED_DEFAULT = false;

    private long id;
    private String region;
    private boolean isLogged;

    private Context context;
    private SharedPreferences preferences;

    public SessionPreference(Context context){
        this.context = context;
        load();
    }

    public void load(){
        preferences = context.getSharedPreferences(this.PREFERENCES_SESSION_NAME, context.MODE_PRIVATE);

        this.id = preferences.getLong(ID_FIELD, ID_DEFAULT);
        this.region = preferences.getString(REGION_FIELD, REGION_DEFAULT);
        this.isLogged = preferences.getBoolean(IS_LOGGED_FIELD, IS_LOGGED_DEFAULT);
    }

    public void save(){
        SharedPreferences.Editor editor = preferences.edit();

        editor.putLong(ID_FIELD, id);
        editor.putString(REGION_FIELD, region);
        editor.putBoolean(IS_LOGGED_FIELD, isLogged);

        editor.commit();
    }

    public void clear(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }

    public boolean isLogged() {
        return isLogged;
    }

    public void setIsLogged(boolean isLogged) {
        this.isLogged = isLogged;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
