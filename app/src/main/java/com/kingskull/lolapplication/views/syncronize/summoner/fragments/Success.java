package com.kingskull.lolapplication.views.syncronize.summoner.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.kingskull.lolapplication.R;
import com.kingskull.lolapplication.controllers.SyncronizeController;
import com.kingskull.lolapplication.models.pojos.Summoner;
import com.kingskull.lolapplication.views.syncronize.summoner.activities.Syncronize;

import java.util.HashMap;


public class Success extends Fragment implements View.OnClickListener {
    private static final String SUMMONER = "summoner";
    private static final String REGION = "region";

    private Summoner summoner;
    private String region;

    private View fragment;
    private Syncronize parent;
    private Button mNext;

    private SyncronizeController controller;

    public static Success newInstance(Summoner summoner, String region) {
        Success fragment = new Success();
        Bundle args = new Bundle();
        args.putSerializable(SUMMONER, summoner);
        args.putString(REGION, region);
        fragment.setArguments(args);
        return fragment;
    }

    public Success() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            summoner = (Summoner) getArguments().get(SUMMONER);
            region = getArguments().getString(REGION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragment = inflater.inflate(R.layout.fragment_success, container, false);
        parent = (Syncronize) getActivity();

        mNext = (Button) fragment.findViewById(R.id.next);
        mNext.setOnClickListener(this);

        controller = new SyncronizeController(getActivity().getApplicationContext());

        saveSummoner();
        createCache();

        return fragment;
    }

    public void saveSummoner(){
        controller.saveOnDb(summoner, region);
    }

    public void createCache(){
        controller.generateSession(summoner, region);
    }


    @Override
    public void onClick(View view) {
        parent.replaceFragment(Syncronize.FINALLY, new HashMap<String, Object>());
    }
}
