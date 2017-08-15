package com.kingskull.lolapplication.views.loading.screen.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.kingskull.lolapplication.R;
import com.kingskull.lolapplication.api.observer.BusProvider;
import com.kingskull.lolapplication.api.restfull.Utils.SilverCodingApiCall;
import com.kingskull.lolapplication.api.restfull.connections.StaticInfo;
import com.kingskull.lolapplication.api.restfull.connections.responses.KeyResponse;
import com.kingskull.lolapplication.views.summoner.data.activities.MainActivity;
import com.kingskull.lolapplication.views.syncronize.summoner.activities.SummonerList;
import com.squareup.otto.Subscribe;

public class LoadingScreen extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);
        init();
    }

    public void init(){
        BusProvider.register(this);
        SilverCodingApiCall apiCall = new SilverCodingApiCall();

        apiCall.getKey("personal");
    }

    @Subscribe
    public void readKey(KeyResponse response){
        BusProvider.unRegister(this);

        String keycoded = response.getResponse();
        keycoded = keycoded.substring(210);
        String key = keycoded.substring(6,14) + "-" + keycoded.substring(20,24) + "-"
                + keycoded.substring(30,34) + "-" + keycoded.substring(40,44) + "-"
                + keycoded.substring(50,62);

        StaticInfo info = StaticInfo.getInstance();
        info.setApiKey(key);

        Intent intent = new Intent(getApplicationContext(), SummonerList.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        BusProvider.unRegisterAllListeners();
        super.onBackPressed();
    }
}
