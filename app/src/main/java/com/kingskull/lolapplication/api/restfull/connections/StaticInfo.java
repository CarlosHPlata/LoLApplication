package com.kingskull.lolapplication.api.restfull.connections;

import com.kingskull.lolapplication.models.database.entities.SummonerEntitie;
import com.kingskull.lolapplication.models.pojos.Champion.Champion;
import com.kingskull.lolapplication.models.pojos.DragonVersion.Realm;
import com.kingskull.lolapplication.models.pojos.item.ItemDto;
import com.kingskull.lolapplication.models.pojos.matchinfo.MatchDetail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Usuario on 19/09/2015.
 */
public class StaticInfo {

    public static final String PREFERENCES_NAME = "synergylolkingskull";

    private String apiKey;
    private SummonerEntitie summonerEntitie;
    private Map<Long, MatchDetail> matchs = new HashMap<Long, MatchDetail>();
    private Map<String, String> endPoints = new HashMap<>();

    private List<String> dragonVersions = new ArrayList<>();
    private Realm dragonVersion = new Realm();

    private Map<Integer, Champion> champions = new HashMap<Integer, Champion>();
    private Map<String, ItemDto> items = new HashMap<String, ItemDto>();

    private static StaticInfo staticInfo;

    private StaticInfo(){
        initializeEndPoints();
    }

    public static StaticInfo getInstance(){
        if (staticInfo == null){
            staticInfo =  new StaticInfo();
        }
        return staticInfo;
    }

    public List<String> getDragonVersions() {
        return dragonVersions;
    }

    public void setDragonVersions(List<String> dragonVersions) {
        this.dragonVersions = dragonVersions;
    }

    public Realm getDragonVersion(){
        return dragonVersion;
    }

    public void setDragonVersion(Realm dragonVersion){
        this.dragonVersion = dragonVersion;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public SummonerEntitie getSummonerEntitie() {
        return summonerEntitie;
    }

    public void setSummonerEntitie(SummonerEntitie summonerEntitie) {
        this.summonerEntitie = summonerEntitie;
    }

    public Map<Integer, Champion> getChampions() {
        return champions;
    }

    public void setChampions(Map<Integer, Champion> champions) {
        this.champions = champions;
    }

    public Map<Long, MatchDetail> getMatchs() {
        return matchs;
    }

    public void setMatchs(Map<Long, MatchDetail> matchs) {
        this.matchs = matchs;
    }

    public void initializeEndPoints(){
        endPoints.put(RIOT.NA, RIOT.END_POINT_NA);
        endPoints.put(RIOT.BR, RIOT.END_POINT_BR);
        endPoints.put(RIOT.KR, RIOT.END_POINT_KR);
        endPoints.put(RIOT.TR, RIOT.END_POINT_TR);
        endPoints.put(RIOT.EUW, RIOT.END_POINT_EUW);
        endPoints.put(RIOT.EUNE, RIOT.END_POINT_EUNE);
        endPoints.put(RIOT.LAS, RIOT.END_POINT_LAS);
        endPoints.put(RIOT.LAN, RIOT.END_POINT_LAN);
        endPoints.put(RIOT.RU, RIOT.END_POINT_RU);
        endPoints.put(RIOT.OCE, RIOT.END_POINT_OCE);
        endPoints.put("pbe", RIOT.END_POINT_PBE);
        endPoints.put("global", RIOT.END_POINT_GLOBAL);
    }

    public Map<String, String> getEndPoints() {
        return endPoints;
    }

    public void setEndPoints(Map<String, String> endPoints) {
        this.endPoints = endPoints;
    }

    public Map<String, ItemDto> getItems() {
        return items;
    }

    public void setItems(Map<String, ItemDto> items) {
        this.items = items;
    }
}
