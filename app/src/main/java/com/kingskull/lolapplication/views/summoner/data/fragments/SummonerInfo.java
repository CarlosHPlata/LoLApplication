package com.kingskull.lolapplication.views.summoner.data.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kingskull.lolapplication.R;
import com.kingskull.lolapplication.api.observer.BusProvider;
import com.kingskull.lolapplication.api.restfull.Utils.SummonerCache;
import com.kingskull.lolapplication.api.restfull.connections.RIOT;
import com.kingskull.lolapplication.controllers.utils.DragonVersionsHandler;
import com.kingskull.lolapplication.controllers.utils.SummonerUtils;
import com.kingskull.lolapplication.models.pojos.Summoner;
import com.kingskull.lolapplication.models.pojos.league.Entry;
import com.kingskull.lolapplication.models.pojos.ranked.ChampionRankedStat;
import com.kingskull.lolapplication.views.widgets.CircularProgressBar;
import com.koushikdutta.ion.Ion;

import java.text.DecimalFormat;

public class SummonerInfo extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;


    private View fragment;

    private Summoner summoner;
    private CircularProgressBar bar;

    public static SummonerInfo newInstance() {
        SummonerInfo fragment = new SummonerInfo();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
       // args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public SummonerInfo() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //if (getArguments() != null) {
         //   mParam1 = getArguments().getString(ARG_PARAM1);
           // mParam2 = getArguments().getString(ARG_PARAM2);
        //}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragment = inflater.inflate(R.layout.fragment_summoner_info, container, false);


        bar = (CircularProgressBar) fragment.findViewById(R.id.winratepercentage);

       this.summoner = SummonerCache.getSummonerCache();
        fillVars();

        return fragment;
    }

    private void fillVars(){

        SummonerUtils utils = new SummonerUtils();
        ChampionRankedStat stat;
        if (summoner.getRankedStat().getChampions().size() >= 1) {
            stat = summoner.getRankedStat().getChampions().get(summoner.getRankedStat().getChampions().size() - 1);
            for (ChampionRankedStat temp : summoner.getRankedStat().getChampions()) {
                if (temp.getId() == 0)
                    stat = temp;
            }
        }
        else
            stat = new ChampionRankedStat();


        //filling WLP rate
        ImageView icon = (ImageView) fragment.findViewById(R.id.icon);
        Ion.with(icon).placeholder(getResources().getDrawable(R.drawable.icon)).error(getResources().getDrawable(R.drawable.icon))
                .load(new DragonVersionsHandler().urlBuilder(RIOT.PROFILE_ICONS_URL) + summoner.getProfileIconId() + ".png");


        Entry entry;
        if (summoner.getLeagueStat().getEntries().size() >= 1)
            entry= summoner.getLeagueStat().getEntries().get(0);
        else
            entry = new Entry();

        String wins = entry.getWins() > 999? (entry.getWins()/1000)+"K" : entry.getWins()+"";
        String losses = entry.getLosses() > 999? (entry.getLosses()/1000)+"K" : entry.getLosses()+"";
        String played = (entry.getLosses()+entry.getWins()) > 999? ((entry.getLosses()+entry.getWins())/1000)+"K" : (entry.getLosses()+entry.getWins())+"";
        int rate = (int) utils.getPerformance(stat);

        ((TextView) fragment.findViewById(R.id.win_points) ).setText(wins);
        ((TextView) fragment.findViewById(R.id.lose_points) ).setText( losses );
        ((TextView) fragment.findViewById(R.id.played_ponts) ).setText( played );

        bar.setProgress( rate );
        bar.setTitle(rate+"%");




        //filling division section
        String division = summoner.getLeagueStat().getTier() + " " + entry.getDivision();
        ((TextView) fragment.findViewById(R.id.division_text) ).setText(division);

        switch (summoner.getLeagueStat().getTier()){
            case RIOT.TIER_BRONZE:
                ((ImageView) fragment.findViewById(R.id.tierImage)).setImageDrawable(getResources().getDrawable(R.drawable.tier_bronze));
                break;
            case RIOT.TIER_SILVER:
                ((ImageView) fragment.findViewById(R.id.tierImage)).setImageDrawable(getResources().getDrawable(R.drawable.tier_silver));
                break;
            case RIOT.TIER_GOLD:
                ((ImageView) fragment.findViewById(R.id.tierImage)).setImageDrawable(getResources().getDrawable(R.drawable.tier_gold));
                break;
            case RIOT.TIER_PLATINUM:
                ((ImageView) fragment.findViewById(R.id.tierImage)).setImageDrawable(getResources().getDrawable(R.drawable.tier_platinum));
                break;
            case RIOT.TIER_DIAMOND:
                ((ImageView) fragment.findViewById(R.id.tierImage)).setImageDrawable(getResources().getDrawable(R.drawable.tier_diamond));
                break;
            case RIOT.TIER_MASTER:
                ((ImageView) fragment.findViewById(R.id.tierImage)).setImageDrawable(getResources().getDrawable(R.drawable.tier_master));
                break;
            case RIOT.TIER_CHALLENGER:
                ((ImageView) fragment.findViewById(R.id.tierImage)).setImageDrawable(getResources().getDrawable(R.drawable.tier_challenger));
                break;
            default:
                ((ImageView) fragment.findViewById(R.id.tierImage)).setImageDrawable(getResources().getDrawable(R.drawable.tier_provisional));
        }


        //filling ranked stats section:


        String minions_game = utils.getMinionsPerGame(entry, stat);
        ((TextView) fragment.findViewById(R.id.minions) ).setText( minions_game );

        String gold_game = utils.getGoldPerGame(entry, stat);
        ((TextView) fragment.findViewById(R.id.gold_earned) ).setText( gold_game );

        String turrets =  utils.getTurretsPerGame(entry, stat);
        ((TextView) fragment.findViewById(R.id.turrets) ).setText( turrets );

        ((TextView) fragment.findViewById(R.id.sprees) ).setText( stat.getStats().getKillingSpree()+"" );

        ((TextView) fragment.findViewById(R.id.leaguepoints) ).setText( entry.getLeaguePoints() + " league points" );


        //filling kdaa
        double kdaRatio = utils.KDARatio(stat);
        DecimalFormat df = new DecimalFormat("#.#");
        ((TextView) fragment.findViewById(R.id.kills_summoner)).setText( utils.getKills(stat) );
        ((TextView) fragment.findViewById(R.id.deaths_single_champion)).setText( utils.getDeaths(stat) );
        ((TextView) fragment.findViewById(R.id.assist_summoner)).setText( utils.getAssists(stat) );
        ((TextView) fragment.findViewById(R.id.kda_perc)).setText( "KDA:" + df.format(kdaRatio) );
    }

}
