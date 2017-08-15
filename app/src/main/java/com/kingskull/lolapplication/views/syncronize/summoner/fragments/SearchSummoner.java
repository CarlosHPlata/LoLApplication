package com.kingskull.lolapplication.views.syncronize.summoner.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.kingskull.lolapplication.R;
import com.kingskull.lolapplication.api.restfull.connections.RIOT;
import com.kingskull.lolapplication.api.restfull.connections.StaticInfo;
import com.kingskull.lolapplication.api.restfull.services.SummonerService;
import com.kingskull.lolapplication.controllers.SummonerListController;
import com.kingskull.lolapplication.models.database.entities.SummonerEntitie;
import com.kingskull.lolapplication.models.pojos.Summoner;
import com.kingskull.lolapplication.views.syncronize.summoner.activities.Syncronize;

import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SearchSummoner extends Fragment implements View.OnClickListener{

    private View fragment;

    private Syncronize parent;

    private EditText mEditSummonerName;
    private Spinner spinnerRegion;

    public static SearchSummoner newInstance() {
        SearchSummoner fragment = new SearchSummoner();
        Bundle args = new Bundle();
        
        fragment.setArguments(args);
        return fragment;
    }

    public SearchSummoner() {
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
        fragment = inflater.inflate(R.layout.fragment_search_summoner, container, false);

        spinnerRegion = (Spinner) fragment.findViewById(R.id.search_region_spinner);
        String[] regions = RIOT.REGIONS;
        ArrayAdapter<String> adapterRegion = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, regions);
        spinnerRegion.setAdapter(adapterRegion);

        mEditSummonerName = (EditText) fragment.findViewById(R.id.search_summoner_name);

        Button next = (Button) fragment.findViewById(R.id.next);
        next.setOnClickListener(this);

        parent = (Syncronize) getActivity();

        return fragment;
    }

    @Override
    public void onClick(View view) {
        parent.showProgressBar();

        if ( !isSummonerOnDB() ){
            summonerOnRiotApi();
        } else {
            parent.hideProgressBar();
            showToast( getActivity().getResources().getString( R.string.search_summoner_already_registered) );
        }
    }

    private void nextStep(Summoner summoner){
        Map<String, Object> params = new HashMap<>();
        params.put("summonerName", summoner);
        params.put("region", spinnerRegion.getSelectedItem().toString());

        parent.replaceFragment(Syncronize.CODE_RUNES, params);
    }

    private void summonerOnRiotApi(){
        String region = spinnerRegion.getSelectedItem().toString();

        String endpoint = StaticInfo.getInstance().getEndPoints().get(region);
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(endpoint)
                .build();

        SummonerService service = restAdapter.create(SummonerService.class);

        service.getSummonner(mEditSummonerName.getText().toString(), region, StaticInfo.getInstance().getApiKey(), new Callback<HashMap<String, Summoner>>() {
            @Override
            public void success(HashMap<String, Summoner> stringSummonerHashMap, Response response) {
                parent.hideProgressBar();
                showToast( getActivity().getResources().getString(R.string.success_msg) );
                String summonerName = mEditSummonerName.getText().toString();
                summonerName = summonerName.toLowerCase();
                summonerName = summonerName.replaceAll("\\s","");
                Summoner summoner = stringSummonerHashMap.get( summonerName );
                nextStep( summoner );
            }

            @Override
            public void failure(RetrofitError error) {
                parent.hideProgressBar();
                showToast(getActivity().getResources().getString(R.string.search_riot_api_error));
            }
        });
    }

    private boolean isSummonerOnDB(){
        SummonerListController controller = new SummonerListController(getActivity().getApplicationContext());
        SummonerEntitie entitie = controller.getSummonerEntitieByName( mEditSummonerName.getText().toString(), spinnerRegion.getSelectedItem().toString() );

        return entitie.getId() != -1;
    }

    private void showToast(String message){
        Toast toast = Toast.makeText(getActivity(), message, Toast.LENGTH_LONG);
        toast.show();
    }
}
