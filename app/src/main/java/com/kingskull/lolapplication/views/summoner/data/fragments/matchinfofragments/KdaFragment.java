package com.kingskull.lolapplication.views.summoner.data.fragments.matchinfofragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kingskull.lolapplication.R;
import com.kingskull.lolapplication.api.observer.BusProvider;
import com.kingskull.lolapplication.api.restfull.connections.StaticInfo;
import com.kingskull.lolapplication.api.restfull.connections.responses.MatchInfoStartFraments;
import com.kingskull.lolapplication.controllers.utils.ScoreCalculator;
import com.kingskull.lolapplication.controllers.utils.SessionManager;
import com.kingskull.lolapplication.models.pojos.matchinfo.MatchDetail;
import com.kingskull.lolapplication.models.pojos.matchinfo.Participant;
import com.kingskull.lolapplication.models.pojos.matchinfo.ParticipantIdentity;
import com.kingskull.lolapplication.models.pojos.matchinfo.ParticipantStats;
import com.squareup.otto.Subscribe;

import java.text.DecimalFormat;

/**
 * Created by Carlos on 01/02/2016.
 */
public class KdaFragment extends Fragment{

    private View fragment;

    private TextView kills, deaths, assist, kda;

    private boolean isRegistered = false;
    private MatchDetail matchDetail;
    private Participant participant;

    public static KdaFragment newInstance() {
        KdaFragment fragment = new KdaFragment();
        return fragment;
    }

    public KdaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.fragment = inflater.inflate(R.layout.fragment_kda, container, false);

        this.kills = (TextView) fragment.findViewById(R.id.kills_single_champion);
        this.deaths = (TextView)fragment.findViewById(R.id.deaths_single_champion);
        this.assist = (TextView) fragment.findViewById(R.id.assist_single_champion);
        this.kda = (TextView) fragment.findViewById(R.id.kda_perc);

        if (!isRegistered){
            isRegistered = true;
            BusProvider.register(this);
        }

        return fragment;
    }

    @Subscribe
    public void readyToLoad(MatchInfoStartFraments ready){
        this.isRegistered = false;
        BusProvider.unRegister(this);

        this.matchDetail = StaticInfo.getInstance().getMatchs().get(ready.getIdMatch());

        long idPlayer = SessionManager.getSession(getActivity().getApplicationContext()).getId();

        int idParticipant = findParticipantIdByPlayerId(idPlayer);

        this.participant = findParticipant(idParticipant);

        fillKda();
    }

    private void fillKda(){
        ParticipantStats stats = participant.getStats();

        kills.setText(stats.getKills() + "\nK");
        deaths.setText(stats.getDeaths() + "\nD");
        assist.setText(stats.getAssists() + "\nA");

        ScoreCalculator calculator = new ScoreCalculator();
        double kdaRatio = calculator.calculateKDARatio(stats.getKills(), stats.getDeaths(), stats.getAssists());
        DecimalFormat df = new DecimalFormat("#.#");

        kda.setText("KDA: " + df.format(kdaRatio));
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

    private Participant findParticipant(int idPlayer){
        Participant result = null;
        for (Participant participant : matchDetail.getParticipants()){
            if (participant.getParticipantId() == idPlayer){
                result = participant;
            }
        }

        return result;
    }

}
