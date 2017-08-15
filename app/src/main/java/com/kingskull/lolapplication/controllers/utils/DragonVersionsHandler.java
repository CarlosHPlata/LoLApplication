package com.kingskull.lolapplication.controllers.utils;

import com.kingskull.lolapplication.api.restfull.connections.RIOT;
import com.kingskull.lolapplication.api.restfull.connections.StaticInfo;
import com.kingskull.lolapplication.models.pojos.DragonVersion.Realm;

import java.util.List;

/**
 * Created by Carlos on 08/05/2016.
 */
public class DragonVersionsHandler {

    private List<String> versions;
    private Realm currentVersion;

    public DragonVersionsHandler(){
        StaticInfo staticInfo = StaticInfo.getInstance();
        this.versions = staticInfo.getDragonVersions();
        this.currentVersion = staticInfo.getDragonVersion();
    }

    public String getClosestVersion (String versionComplete){
        //String newVersion = versionComplete.substring(0, 5);

        String result = versions.get(0);
        for(String version : versions){
            if ( versionComplete.toLowerCase().contains(version.toLowerCase()) ){
                result = version;
            }
        }

        return result;
    }

    public String getCurrentVersionStat (String url){
        return currentVersion.getN().get(url);
    }

    public String getCurrentDragonVersion (){
        return currentVersion.getN().get("summoner");
    }

    public String urlBuilder(String dragonUrl){
        return RIOT.DRAGON_URL + getCurrentDragonVersion() + dragonUrl;
    }

    public String urlBuilder(String dragonUrl, String specificVersion){
        return RIOT.DRAGON_URL + getCurrentVersionStat(specificVersion) + dragonUrl;
    }

    public List<String> getVersions() {
        return versions;
    }

    public void setVersions(List<String> versions) {
        this.versions = versions;
    }

    public Realm getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(Realm currentVersion) {
        this.currentVersion = currentVersion;
    }
}
