package com.kingskull.lolapplication.views.summoner.data.fragments;

import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kingskull.lolapplication.R;
import com.kingskull.lolapplication.api.restfull.Utils.SummonerCache;
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

        return result;
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

                Dialog dialog = new Dialog(getActivity());

                View view = getActivity().getLayoutInflater().inflate(R.layout.single_champion_card, null);

                dialog.setContentView(view);
                dialog.show();

            }
        });

        return fragment;
    }


}
