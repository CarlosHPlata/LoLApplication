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
import android.widget.PopupWindow;
import android.widget.TextView;

import com.kingskull.lolapplication.R;
import com.kingskull.lolapplication.api.restfull.Utils.SummonerCache;
import com.kingskull.lolapplication.controllers.SummonerUtils;
import com.kingskull.lolapplication.models.pojos.Summoner;
import com.kingskull.lolapplication.models.pojos.ranked.ChampionRankedStat;
import com.kingskull.lolapplication.views.summoner.data.widgets.ChampionAdapter;

import java.util.ArrayList;
import java.util.List;

public class ChampionStats extends Fragment {

    private View fragment;

    private ChampionAdapter adapter;
    private RecyclerView drawerList;

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

        return utils.orderByPerformance(result);
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

        this.adapter = new ChampionAdapter(getActivity(), getData());

        drawerList.setAdapter(adapter);
        drawerList.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter.setOnItemClickListener(new ChampionAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

                View view = getActivity().getLayoutInflater().inflate(R.layout.single_champion_card, null);

                final Dialog dialog = new Dialog(getActivity());
                dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                // layout to display
                dialog.setContentView(view);

                // set color transpartent
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                TextView dismissButton = (TextView) view.findViewById(R.id.dismiss);

                dismissButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        return fragment;
    }


}
