package com.kingskull.lolapplication.views.summoner.data.fragments.matchinfofragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.IconRoundCornerProgressBar;
import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
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


public class CreepScore extends Fragment {

    private View fragment;
    private IconRoundCornerProgressBar creepScoreBar, wardsBar, goldPerSecongBar;
    private TextView creepScoreText, wardsText, goldPerSecondText;

    private boolean isRegistered = false;

    private MatchDetail matchDetail;
    private Participant participant;
    double time;

    public static CreepScore newInstance() {
        CreepScore fragment = new CreepScore();
        return fragment;
    }

    public CreepScore() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.fragment = inflater.inflate(R.layout.fragment_creep_score, container, false);

        this.creepScoreBar = (IconRoundCornerProgressBar) fragment.findViewById(R.id.creepscorebar);
        this.wardsBar = (IconRoundCornerProgressBar) fragment.findViewById(R.id.wardsbar);
        this.goldPerSecongBar = (IconRoundCornerProgressBar) fragment.findViewById(R.id.goldbar);

        this.creepScoreText = (TextView) fragment.findViewById(R.id.creepscore);
        this.wardsText = (TextView) fragment.findViewById(R.id.wards);
        this.goldPerSecondText = (TextView) fragment.findViewById(R.id.gold);

        if (!isRegistered){
            isRegistered = true;
            BusProvider.register(this);
        }

        return this.fragment;
    }

    @Subscribe
    public void readyToLoad(MatchInfoStartFraments ready){
        this.isRegistered = false;
        BusProvider.unRegister(this);

        this.matchDetail = StaticInfo.getInstance().getMatchs().get(ready.getIdMatch());

        long idPlayer = SessionManager.getSession(getActivity().getApplicationContext()).getId();

        int idParticipant = findParticipantIdByPlayerId(idPlayer);

        this.participant = findParticipant(idParticipant);

        this.time = matchDetail.getMatchDuration();

        fillBars();
        fillText();
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

    private void fillBars(){
        ScoreCalculator calculator = new ScoreCalculator();
        ParticipantStats stats = participant.getStats();

        fillCssBar(stats, calculator);
        fillGoldBar(stats, calculator);
        fillWardsBar(stats, calculator);
    }

    private void fillCssBar(ParticipantStats stats, ScoreCalculator calculator){
        double farm = stats.getMinionsKilled() + stats.getNeutralMinionsKilled();
        double farmMax = calculator.calculateMinionsPerTime(time);
        farm = farm > farmMax? farmMax : farm;

        creepScoreBar.setMax((float) farmMax);
        creepScoreBar.setProgress((float) farm);
    }

    private void fillGoldBar(ParticipantStats stats, ScoreCalculator calculator){
        double gold = stats.getGoldEarned();
        double goldMax = calculator.calculateGoldPerTime(time);
        gold = gold > goldMax? goldMax : gold;

        goldPerSecongBar.setMax((float) goldMax);
        goldPerSecongBar.setProgress((float) gold);
    }

    private void fillWardsBar(ParticipantStats stats, ScoreCalculator calculator){
        double wards = stats.getWardsPlaced();
        double wardsMax = calculator.calculateWardsPerTIme(time);
        wards = wards > wardsMax? wardsMax : wards;

        wardsBar.setMax((float) wardsMax);
        wardsBar.setProgress((float) wards);
    }

    private void fillText(){
        ParticipantStats stats = participant.getStats();

        fillCssText(stats);
        fillGoldText(stats);
        fillWardText(stats);
    }

    private void fillCssText(ParticipantStats stats){
        String TEXT = " CREEP SCORE";
        double farm = stats.getMinionsKilled() + stats.getNeutralMinionsKilled();

        creepScoreText.setText((int)farm + TEXT);
    }

    private void fillWardText(ParticipantStats stats){
        String TEXT = "  WARDS PLACED";

        wardsText.setText(stats.getWardsPlaced() + TEXT);
    }

    private void fillGoldText(ParticipantStats stats){
        String TEXT = " GOLD PER MINUTE";

        ScoreCalculator calculator = new ScoreCalculator();
        DecimalFormat df = new DecimalFormat("#.#");
        double goldPerMin = calculator.calculateGoldPerMinute(time, stats.getGoldEarned());

        goldPerSecondText.setText(df.format(goldPerMin) + TEXT);
    }

}
