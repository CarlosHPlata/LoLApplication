package com.kingskull.lolapplication.views.summoner.data.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kingskull.lolapplication.R;
import com.kingskull.lolapplication.api.observer.BusProvider;
import com.kingskull.lolapplication.api.restfull.Utils.SummonerCache;
import com.kingskull.lolapplication.models.pojos.Summoner;
import com.kingskull.lolapplication.models.pojos.match.MatchReference;
import com.kingskull.lolapplication.views.summoner.data.activities.MatchInfo;
import com.kingskull.lolapplication.views.summoner.data.widgets.MatchAdapter;
import com.squareup.otto.Bus;

import java.util.List;


public class MatchHistory extends Fragment {

    private View fragment;

    private boolean isRegistered = false;

    private RecyclerView drawerList;
    private List<MatchReference> matchHistory;
    private MatchAdapter adapter;


    public static MatchHistory newInstance() {
        MatchHistory fragment = new MatchHistory();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    public MatchHistory() {
        // Required empty public constructor
    }

    private List<MatchReference> getData(){
        Summoner summoner = SummonerCache.getSummonerCache();
        List<MatchReference> matchHistory = summoner.getMatchList().getMatches();

        this.matchHistory = matchHistory;
        return this.matchHistory;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.fragment = inflater.inflate(R.layout.fragment_match_history, container, false);

        this.drawerList = (RecyclerView) fragment.findViewById(R.id.matchList);

        this.adapter = new MatchAdapter(getActivity(), getData());

        drawerList.setAdapter(adapter);
        drawerList.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter.setOnItemClickListener(new MatchAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent i = new Intent(getActivity().getApplicationContext(), MatchInfo.class);
                i.putExtra("longMatchId", matchHistory.get(position).getMatchId());
                BusProvider.unRegisterAllListeners();
                startActivity(i);
            }
        });

        return this.fragment;
    }
}
