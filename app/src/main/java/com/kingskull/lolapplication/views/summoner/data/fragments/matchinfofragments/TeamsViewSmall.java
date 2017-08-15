package com.kingskull.lolapplication.views.summoner.data.fragments.matchinfofragments;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.Telephony;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kingskull.lolapplication.R;
import com.kingskull.lolapplication.api.observer.BusProvider;
import com.kingskull.lolapplication.api.restfull.Utils.ChampionApiUtils;
import com.kingskull.lolapplication.api.restfull.connections.RIOT;
import com.kingskull.lolapplication.api.restfull.connections.StaticInfo;
import com.kingskull.lolapplication.api.restfull.connections.responses.MatchInfoStartFraments;
import com.kingskull.lolapplication.controllers.utils.DragonVersionsHandler;
import com.kingskull.lolapplication.controllers.utils.SessionManager;
import com.kingskull.lolapplication.models.pojos.Champion.Champion;
import com.kingskull.lolapplication.models.pojos.matchinfo.MatchDetail;
import com.kingskull.lolapplication.models.pojos.matchinfo.Participant;
import com.kingskull.lolapplication.models.pojos.matchinfo.ParticipantIdentity;
import com.kingskull.lolapplication.models.pojos.matchinfo.Team;
import com.koushikdutta.ion.Ion;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import retrofit.RestAdapter;


public class TeamsViewSmall extends Fragment {



    private View fragment;

    private boolean isRegistered = false;

    private ImageView champ1, champ2, champ3, champ4, champ5, champ6, champ7, champ8, champ9, champ10;
    private ImageView plat1, plat2, plat3, plat4, plat5, plat6, plat7, plat8, plat9, plat10;
    private LinearLayout summonerArea1, summonerArea2, summonerArea3, summonerArea4, summonerArea5,
            summonerArea6, summonerArea7, summonerArea8, summonerArea9, summonerArea10;
    private ImageView winnerTeam;
    private TextView duration;

    private MatchDetail matchDetail;

    private List<Participant> participantsTemp;

    public static TeamsViewSmall newInstance() {
        TeamsViewSmall fragment = new TeamsViewSmall();
        return fragment;
    }

    public TeamsViewSmall() {

        if (!this.isRegistered){
            isRegistered = true;
            BusProvider.register(this);
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.fragment = inflater.inflate(R.layout.fragment_teams_view_small, container, false);

        champ1 = (ImageView) fragment.findViewById(R.id.summoner1champ);
        champ2 = (ImageView) fragment.findViewById(R.id.summoner2champ);
        champ3 = (ImageView) fragment.findViewById(R.id.summoner3champ);
        champ4 = (ImageView) fragment.findViewById(R.id.summoner4champ);
        champ5 = (ImageView) fragment.findViewById(R.id.summoner5champ);
        champ6 = (ImageView) fragment.findViewById(R.id.summoner6champ);
        champ7 = (ImageView) fragment.findViewById(R.id.summoner7champ);
        champ8 = (ImageView) fragment.findViewById(R.id.summoner8champ);
        champ9 = (ImageView) fragment.findViewById(R.id.summoner9champ);
        champ10 = (ImageView) fragment.findViewById(R.id.summoner10champ);

        plat1 = (ImageView) fragment.findViewById(R.id.summoner1plate);
        plat2 = (ImageView) fragment.findViewById(R.id.summoner2plate);
        plat3 = (ImageView) fragment.findViewById(R.id.summoner3plate);
        plat4 = (ImageView) fragment.findViewById(R.id.summoner4plate);
        plat5 = (ImageView) fragment.findViewById(R.id.summoner5plate);
        plat6 = (ImageView) fragment.findViewById(R.id.summoner6plate);
        plat7 = (ImageView) fragment.findViewById(R.id.summoner7plate);
        plat8 = (ImageView) fragment.findViewById(R.id.summoner8plate);
        plat9 = (ImageView) fragment.findViewById(R.id.summoner9plate);
        plat10 = (ImageView) fragment.findViewById(R.id.summoner10plate);

        summonerArea1 = (LinearLayout) fragment.findViewById(R.id.summonerArea1);
        summonerArea2 = (LinearLayout) fragment.findViewById(R.id.summonerArea2);
        summonerArea3 = (LinearLayout) fragment.findViewById(R.id.summonerArea3);
        summonerArea4 = (LinearLayout) fragment.findViewById(R.id.summonerArea4);
        summonerArea5 = (LinearLayout) fragment.findViewById(R.id.summonerArea5);
        summonerArea6 = (LinearLayout) fragment.findViewById(R.id.summonerArea6);
        summonerArea7 = (LinearLayout) fragment.findViewById(R.id.summonerArea7);
        summonerArea8 = (LinearLayout) fragment.findViewById(R.id.summonerArea8);
        summonerArea9 = (LinearLayout) fragment.findViewById(R.id.summonerArea9);
        summonerArea10  = (LinearLayout) fragment.findViewById(R.id.summonerArea10);

        winnerTeam = (ImageView) fragment.findViewById(R.id.winnerTeam);

        duration = (TextView) fragment.findViewById(R.id.duration);

        return fragment;
    }

    @Subscribe
    public void readyToLoad(MatchInfoStartFraments ready){
        this.matchDetail = StaticInfo.getInstance().getMatchs().get(ready.getIdMatch());

        this.participantsTemp = matchDetail.getParticipants();

        long seconds = matchDetail.getMatchDuration();

        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        seconds = seconds % 60;

        String date;

        if (hours > 0)
            date = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        else
            date = String.format("%02d:%02d", minutes, seconds);

        duration.setText( date + "\nDURATION" );

        setWinnerTeam();

        setPlayerArea();

        setPlateImages();

        findChampions();
    }

    private void setPlayerArea(){
        long idPlayer = SessionManager.getSession(getActivity().getApplicationContext()).getId();

        int idParticipant = findParticipantIdByPlayerId(idPlayer);

        LinearLayout area = findArea(idParticipant);

        if (idParticipant < 5){
            area.setBackgroundResource(R.drawable.blue_player_small);
        } else {
            area.setBackgroundResource(R.drawable.red_player_small);
        }

    }

    private void setWinnerTeam(){
        boolean blueWins;

        Team team = matchDetail.getTeams().get(0);

        if ( team.getTeamId() == 100 ){
            if (team.isWinner()) blueWins = true;
            else blueWins = false;
        } else {
            if (team.isWinner()) blueWins = false;
            else blueWins = true;
        }

        Drawable image;

        if (blueWins){
            image = getActivity().getApplicationContext().getResources().getDrawable(R.drawable.blue_wins);
            winnerTeam.setImageDrawable(image);
        } else {
            image = getActivity().getApplicationContext().getResources().getDrawable(R.drawable.red_wins);
            winnerTeam.setImageDrawable(image);
        }
    }

    private void setPlateImages(){
        for (Participant participant : matchDetail.getParticipants()){
            ImageView plateImage = getPlateImage(participant.getParticipantId());

            switch (participant.getHighestAchievedSeasonTier()){
                case RIOT.TIER_BRONZE:
                    plateImage.setImageDrawable( getActivity().getApplicationContext().getResources().getDrawable(R.drawable.bronze_small_plate) );
                    break;
                case RIOT.TIER_SILVER:
                    plateImage.setImageDrawable( getActivity().getApplicationContext().getResources().getDrawable(R.drawable.silver_small_plate) );
                    break;
                case RIOT.TIER_GOLD:
                    plateImage.setImageDrawable( getActivity().getApplicationContext().getResources().getDrawable(R.drawable.gold_small_plate) );
                    break;
                case RIOT.TIER_PLATINUM:
                    plateImage.setImageDrawable( getActivity().getApplicationContext().getResources().getDrawable(R.drawable.platinum_small_plate) );
                    break;
                case RIOT.TIER_DIAMOND:
                    plateImage.setImageDrawable( getActivity().getApplicationContext().getResources().getDrawable(R.drawable.diamond_small_plate) );
                    break;
                case RIOT.TIER_MASTER:
                    plateImage.setImageDrawable( getActivity().getApplicationContext().getResources().getDrawable(R.drawable.master_small_plate) );
                    break;
                case RIOT.TIER_CHALLENGER:
                    plateImage.setImageDrawable( getActivity().getApplicationContext().getResources().getDrawable(R.drawable.challenger_small_plate) );
                    break;
                default:
                    plateImage.setImageDrawable( getActivity().getApplicationContext().getResources().getDrawable(R.drawable.unranked_small_plate) );
                    break;
            }
        }
    }

    private void findChampions(){
        if (participantsTemp.size() > 0){
            ChampionApiUtils apiUtils = new ChampionApiUtils(getActivity().getApplicationContext());

            if (!this.isRegistered){
                isRegistered = true;
                BusProvider.register(this);
            }

            apiUtils.getChampionInfo(participantsTemp.get(0).getChampionId());
        }
    }


    @Subscribe
    public void setChampImages(Champion champion){

        if (this.isRegistered){
            isRegistered = false;
            BusProvider.unRegister(this);
        }

        int idPlayer = findAndReplatePlayerWithChamp(champion.getId());

        ImageView champImage = getChampImage(idPlayer);

        String urlChamp = new DragonVersionsHandler().urlBuilder(RIOT.CHAMP_SQUARES) + champion.getKey() + ".png";

        Log.i("SYNERGY", urlChamp);

        if (idPlayer != -1){
            Ion.with(champImage).placeholder(getActivity().getResources().getDrawable(R.drawable.champ))
                    .error(getActivity().getResources().getDrawable(R.drawable.champ))
                    .load(urlChamp);


            findChampions();
        }
    }

    private ImageView getChampImage(int idParticipant){
        ImageView temp = null;
        switch (idParticipant){
            case 1:
                temp = champ1;
                break;
            case 2:
                temp = champ2;
                break;
            case 3:
                temp = champ3;
                break;
            case 4:
                temp = champ4;
                break;
            case 5:
                temp = champ5;
                break;
            case 6:
                temp = champ6;
                break;
            case 7:
                temp = champ7;
                break;
            case 8:
                temp = champ8;
                break;
            case 9:
                temp = champ9;
                break;
            case 10:
                temp = champ10;
                break;
        }

        return temp;
    }

    private ImageView getPlateImage(int idParticipant){
        ImageView temp = null;
        switch (idParticipant){
            case 1:
                temp = plat1;
                break;
            case 2:
                temp = plat2;
                break;
            case 3:
                temp = plat3;
                break;
            case 4:
                temp = plat4;
                break;
            case 5:
                temp = plat5;
                break;
            case 6:
                temp = plat6;
                break;
            case 7:
                temp = plat7;
                break;
            case 8:
                temp = plat8;
                break;
            case 9:
                temp = plat9;
                break;
            case 10:
                temp = plat10;
                break;
        }

        return temp;
    }

    private LinearLayout findArea(int idParticipant){
        LinearLayout temp = null;
        switch (idParticipant){
            case 1:
                temp = summonerArea1;
                break;
            case 2:
                temp = summonerArea2;
                break;
            case 3:
                temp = summonerArea3;
                break;
            case 4:
                temp = summonerArea4;
                break;
            case 5:
                temp = summonerArea5;
                break;
            case 6:
                temp = summonerArea6;
                break;
            case 7:
                temp = summonerArea7;
                break;
            case 8:
                temp = summonerArea8;
                break;
            case 9:
                temp = summonerArea9;
                break;
            case 10:
                temp = summonerArea10;
                break;
        }

        return temp;
    }

    private int findAndReplatePlayerWithChamp(int champId){
        int idPlayer = -1;

        for (Participant participant : participantsTemp){
            if ( participant.getChampionId() == champId ){
                idPlayer = participant.getParticipantId();
                break;
            }
        }

        List<Participant> participantsNew = new ArrayList<>();

        for ( Participant participant : participantsTemp ){
            if ( participant.getParticipantId() != idPlayer ){
                participantsNew.add(participant);
            }
        }

        participantsTemp = participantsNew;

        return idPlayer;
    }

    private Participant findParticipant(int idPlayer){
        Participant result = null;
        for (Participant participant : matchDetail.getParticipants()){
            if (participant.getParticipantId() == idPlayer){
                result = participant;
            }
        }

        return result;
    }

    private int findParticipantIdByPlayerId(long id){
        int player = -1;

        for (ParticipantIdentity identity : matchDetail.getParticipantIdentities()){
            if (identity.getPlayer().getSummonerId() == id){
                player = identity.getParticipantId();
                break;
            }
        }

        return player;
    }

}
