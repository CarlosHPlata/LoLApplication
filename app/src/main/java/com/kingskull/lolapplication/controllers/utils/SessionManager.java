package com.kingskull.lolapplication.controllers.utils;

import android.content.Context;

import com.kingskull.lolapplication.models.preferences.SessionPreference;

/**
 * Created by Carlos on 12/10/2015.
 */
public class SessionManager {

    private static SessionPreference session;

    public SessionManager(Context context){
        session = getSession(context);
    }

    public void logIn(long id, String region){
        session.setId(id);
        session.setRegion(region);
        session.setIsLogged(true);

        session.save();
        session.load();
    }

    public void logOut(){
        session.clear();
        session.load();
    }

    public boolean checkSession(){
        return session.isLogged();
    }

    public static SessionPreference getSession(Context context) {
        if (session == null){
            session = new SessionPreference(context);
        }
        return session;
    }
}
