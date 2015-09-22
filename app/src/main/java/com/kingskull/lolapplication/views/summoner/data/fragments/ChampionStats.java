package com.kingskull.lolapplication.views.summoner.data.fragments;

import android.app.ActionBar;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.kingskull.lolapplication.R;
import com.kingskull.lolapplication.api.observer.BusProvider;
import com.kingskull.lolapplication.api.restfull.Utils.ChampionApiUtils;
import com.kingskull.lolapplication.api.restfull.Utils.SummonerCache;
import com.kingskull.lolapplication.api.restfull.connections.RIOT;
import com.kingskull.lolapplication.api.restfull.connections.SILVERCODING;
import com.kingskull.lolapplication.controllers.SummonerUtils;
import com.kingskull.lolapplication.models.pojos.Champion.Champion;
import com.kingskull.lolapplication.models.pojos.Summoner;
import com.kingskull.lolapplication.models.pojos.ranked.ChampionRankedStat;
import com.kingskull.lolapplication.views.summoner.data.widgets.ChampionAdapter;
import com.kingskull.lolapplication.views.widgets.CircularProgressBar;
import com.koushikdutta.ion.Ion;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class ChampionStats extends Fragment {

    private View fragment;

    private ChampionAdapter adapter;
    private RecyclerView drawerList;

    private List<ChampionRankedStat> championsStats;
    private ChampionRankedStat statTemp;

    private boolean isRegistered = false;

    private View view;
    private Dialog dialog;

    public static ChampionStats newInstance() {
        ChampionStats fragment = new ChampionStats();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public ChampionStats() {
        // Required empty public constructor
    }

    private List<ChampionRankedStat> getData(){
        Summoner summoner = SummonerCache.getSummonerCache();
        List<ChampionRankedStat> champions = summoner.getRankedStat().getChampions();
        List<ChampionRankedStat> result = new ArrayList<ChampionRankedStat>();

        for(int i=0; i<champions.size()-2; i++){ //the last champions always be the all ranked stats, so we dont want it
            result.add( champions.get(i) );
        }

        SummonerUtils utils = new SummonerUtils();
        this.championsStats = utils.orderByPerformance(result);

        return this.championsStats;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.fragment = inflater.inflate(R.layout.fragment_champion_stats, container, false);
        this.drawerList = (RecyclerView) fragment.findViewById(R.id.drawerList);

        Bus bus = BusProvider.getInstance();
        if (!this.isRegistered){
            this.isRegistered = true;
            bus.register(this);
        }

        this.adapter = new ChampionAdapter(getActivity(), getData());

        drawerList.setAdapter(adapter);
        drawerList.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter.setOnItemClickListener(new ChampionAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

                view = getActivity().getLayoutInflater().inflate(R.layout.single_champion_card, null);

                dialog = new Dialog(getActivity());
                dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                // layout to display
                dialog.setContentView(view);

                // set color transpartent
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                ((LinearLayout) view.findViewById(R.id.content_champ)).setVisibility(View.GONE);
                ((LinearLayout) view.findViewById(R.id.champProgressBar)).setVisibility(View.VISIBLE);

                statTemp = championsStats.get(position);
                ChampionApiUtils apiUtils = new ChampionApiUtils();
                apiUtils.getChampionInfo( championsStats.get(position).getId() );

                dialog.show();
            }
        });

        return fragment;
    }

    @Subscribe
    public void getChampionResult(Champion champion){

        ImageView champImage = (ImageView) view.findViewById(R.id.full_card_image);

        String url = RIOT.SPLASH_ARTS_URL + champion.getKey() + "_0.jpg";

        Ion.with(champImage).placeholder(getActivity().getResources().getDrawable(R.drawable.dummie_full))
                .error(getActivity().getResources().getDrawable(R.drawable.dummie))
                .load(url);

        TextView dismissButton = (TextView) view.findViewById(R.id.dismiss);

        TextView champName = (TextView) view.findViewById(R.id.champ_name);
        final CircularProgressBar performancebar = (CircularProgressBar) view.findViewById(R.id.champ_performance);

        TextView gamesWon = (TextView) view.findViewById(R.id.champ_games_won);
        TextView gamesLost = (TextView) view.findViewById(R.id.champ_games_lost);
        TextView gamesPlayed = (TextView) view.findViewById(R.id.champ_games_played);

        TextView killsChamp = (TextView) view.findViewById(R.id.kills_single_champion);
        TextView deathsChamp = (TextView) view.findViewById(R.id.deaths_single_champion);
        TextView assistChamp = (TextView) view.findViewById(R.id.assist_single_champion);
        TextView kda = (TextView) view.findViewById(R.id.kda_perc);

        //initialize view
        ChampionRankedStat stat = statTemp;
        SummonerUtils utils = new SummonerUtils();

        champName.setText(champion.getName());

        performancebar.animateProgressTo(0, (int) utils.getPerformance(stat), new CircularProgressBar.ProgressAnimationListener() {
            @Override
            public void onAnimationStart() {

            }

            @Override
            public void onAnimationFinish() {

            }

            @Override
            public void onAnimationProgress(int progress) {
                performancebar.setTitle(progress + "%");
            }
        });

        gamesWon.setText(stat.getStats().getTotalSessionsWon() + "");
        gamesLost.setText(stat.getStats().getTotalSessionsLost() + "");
        gamesPlayed.setText(stat.getStats().getTotalSessionsPlayed() + "");

        killsChamp.setText(utils.getKills(stat));
        deathsChamp.setText(utils.getDeaths(stat));
        assistChamp.setText(utils.getAssists(stat));

        kda.setText(String.format("%.2f", utils.KDARatio(stat)) );

        dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        ((LinearLayout) view.findViewById(R.id.content_champ)).setVisibility(View.VISIBLE);
        ((LinearLayout) view.findViewById(R.id.champProgressBar)).setVisibility(View.GONE);
    }


}
