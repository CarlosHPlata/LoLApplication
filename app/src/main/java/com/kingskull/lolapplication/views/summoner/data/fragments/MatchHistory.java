package com.kingskull.lolapplication.views.summoner.data.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kingskull.lolapplication.R;
import com.kingskull.lolapplication.api.observer.BusProvider;
import com.squareup.otto.Bus;


public class MatchHistory extends Fragment {

    private View fragment;

    private boolean isRegistered = false;

    private RecyclerView drawerList;


    public static MatchHistory newInstance() {
        MatchHistory fragment = new MatchHistory();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    public MatchHistory() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.fragment = inflater.inflate(R.layout.fragment_match_history, container, false);

        if (!this.isRegistered){
            this.isRegistered = true;
            Bus bus = BusProvider.getInstance();
            bus.register(this);
        }

        this.drawerList = (RecyclerView) fragment.findViewById(R.id.matchList);

        return this.fragment;
    }
}
