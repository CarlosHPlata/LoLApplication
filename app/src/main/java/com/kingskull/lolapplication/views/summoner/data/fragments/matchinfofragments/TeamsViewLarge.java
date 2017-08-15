package com.kingskull.lolapplication.views.summoner.data.fragments.matchinfofragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kingskull.lolapplication.R;


public class TeamsViewLarge extends Fragment {

    public static TeamsViewLarge newInstance() {
        TeamsViewLarge fragment = new TeamsViewLarge();
        return fragment;
    }

    public TeamsViewLarge() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_teams_view_large, container, false);
    }

}
