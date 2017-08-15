package com.kingskull.lolapplication.views.syncronize.summoner.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kingskull.lolapplication.R;
import com.kingskull.lolapplication.api.restfull.connections.RIOT;
import com.kingskull.lolapplication.api.restfull.connections.StaticInfo;
import com.kingskull.lolapplication.api.restfull.services.SummonerService;
import com.kingskull.lolapplication.controllers.utils.MixedUtils;
import com.kingskull.lolapplication.models.pojos.Runes.RunePage;
import com.kingskull.lolapplication.models.pojos.Runes.RunePages;
import com.kingskull.lolapplication.models.pojos.Summoner;
import com.kingskull.lolapplication.views.syncronize.summoner.activities.Syncronize;

import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CodeRunes extends Fragment implements View.OnClickListener {

    private static final String SUMMONER_NAME = "summonerName";
    private static final String REGION = "region";

    private Summoner summoner;
    private String region;

    private String code;

    private View fragment;
    private Syncronize parent;

    private Button mNext;
    private TextView mCode;

    public static CodeRunes newInstance(Summoner summonerName, String region) {
        CodeRunes fragment = new CodeRunes();
        Bundle args = new Bundle();

        args.putSerializable(SUMMONER_NAME, summonerName);
        args.putString(REGION, region);

        fragment.setArguments(args);
        return fragment;
    }

    public CodeRunes() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            summoner = (Summoner) getArguments().getSerializable(SUMMONER_NAME);
            region = getArguments().getString(REGION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragment = inflater.inflate(R.layout.fragment_code_runes, container, false);
        parent = (Syncronize) getActivity();

        mNext = (Button) fragment.findViewById(R.id.next);
        mNext.setOnClickListener(this);

        mCode = (TextView) fragment.findViewById(R.id.code);

        initCode();

        return fragment;
    }

    private void initCode(){
        code = MixedUtils.getRandomHexString(4).toUpperCase() + "-" + MixedUtils.getRandomHexString(4).toUpperCase();
        mCode.setText(code);
    }


    @Override
    public void onClick(View view) {
        parent.showProgressBar();

        String endpoint = StaticInfo.getInstance().getEndPoints().get(region);

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(endpoint)
                .build();

        SummonerService service = restAdapter.create(SummonerService.class);
        service.getRunes(summoner.getId() + "", region, StaticInfo.getInstance().getApiKey(), new Callback<HashMap<String, RunePages>>() {
            @Override
            public void success(HashMap<String, RunePages> stringRunePagesHashMap, Response response) {
                RunePages runes = stringRunePagesHashMap.get(summoner.getId()+"");
                Log.i("SYNERGY", response.getUrl());
                validateRunes(runes);

            }

            @Override
            public void failure(RetrofitError error) {
                showToast("error - " + summoner.getId());
                parent.hideProgressBar();
            }
        });


    }

    private void validateRunes(RunePages runes){
        boolean runeWithCode = false;
        for (RunePage page : runes.getPages()) {
            Log.i("SYNERGY LOL runes", page.getName());

            if (page.getName().equalsIgnoreCase(code)){
                runeWithCode = true;
            }

            parent.hideProgressBar();
        }

        if (runeWithCode){
            nextStep();
            showToast( getActivity().getResources().getString(R.string.success_msg) );
        } else {
            showToast( getActivity().getResources().getString(R.string.runes_not_match) );
        }

    }

    private void nextStep(){
        Map<String, Object> params = new HashMap<>();
        params.put("summoner", summoner);
        params.put("region", region);

        parent.replaceFragment(parent.SUCCES, params);
    }

    private void showToast(String message){
        Toast toast = Toast.makeText(getActivity(), message, Toast.LENGTH_LONG);
        toast.show();
    }

}
