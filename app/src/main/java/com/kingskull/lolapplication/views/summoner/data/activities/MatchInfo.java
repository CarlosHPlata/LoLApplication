package com.kingskull.lolapplication.views.summoner.data.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.kingskull.lolapplication.R;
import com.kingskull.lolapplication.api.observer.BusProvider;
import com.kingskull.lolapplication.api.restfull.Utils.MatchApiUtils;
import com.kingskull.lolapplication.api.restfull.connections.Errors.RiotApiError;
import com.kingskull.lolapplication.api.restfull.connections.RIOT;
import com.kingskull.lolapplication.api.restfull.connections.responses.MatchInfoStartFraments;
import com.kingskull.lolapplication.controllers.utils.SessionManager;
import com.kingskull.lolapplication.models.pojos.matchinfo.Frame;
import com.kingskull.lolapplication.models.pojos.matchinfo.MatchDetail;
import com.kingskull.lolapplication.views.loading.screen.activities.LoadingScreen;
import com.kingskull.lolapplication.views.summoner.data.fragments.matchinfofragments.Advices;
import com.kingskull.lolapplication.views.summoner.data.fragments.matchinfofragments.CreepScore;
import com.kingskull.lolapplication.views.summoner.data.fragments.matchinfofragments.Items;
import com.kingskull.lolapplication.views.summoner.data.fragments.matchinfofragments.KdaFragment;
import com.kingskull.lolapplication.views.summoner.data.fragments.matchinfofragments.TeamsViewLarge;
import com.kingskull.lolapplication.views.summoner.data.fragments.matchinfofragments.TeamsViewSmall;
import com.kingskull.lolapplication.views.syncronize.summoner.activities.SummonerList;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class MatchInfo extends ActionBarActivity {

    private Fragment teams, build, creepscore, advices, kda;

    private FrameLayout teamsContainer, buildContainer, creepscoreContainer, advicesContainer, kdaContainer;
    private ImageView championCard;

    private long matchId;

    private boolean isRegistered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matc_info);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.matchId = extras.getLong("longMatchId");
        }


        if (!this.isRegistered){
            this.isRegistered = true;
            BusProvider.register(this);
        }


        teamsContainer = (FrameLayout) findViewById(R.id.teamsframe);
        buildContainer = (FrameLayout) findViewById(R.id.buildFrame);
        creepscoreContainer = (FrameLayout) findViewById(R.id.creepscoreFrame);
        advicesContainer = (FrameLayout) findViewById(R.id.advicesFrame);
        kdaContainer = (FrameLayout) findViewById(R.id.kdaFrame);


        instanceFrames();

        ((LinearLayout) findViewById(R.id.matchProgressBar)).setVisibility(View.VISIBLE);
        ((ScrollView) findViewById(R.id.matchInfoCard)).setVisibility(View.GONE);

        long id = this.matchId;

        findMatch(id, SessionManager.getSession(this).getRegion());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_matc_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void instanceFrames(){
        FragmentManager fragmentManager = getFragmentManager();

        teams = TeamsViewSmall.newInstance();
        fragmentManager.beginTransaction()
                .replace(R.id.teamsframe, teams).commit();

        build = Items.newInstance();
        fragmentManager.beginTransaction()
                .replace(R.id.buildFrame, build).commit();

        creepscore = CreepScore.newInstance();
        fragmentManager.beginTransaction()
                .replace(R.id.creepscoreFrame, creepscore).commit();

        /*
        advices = Advices.newInstance();
        fragmentManager.beginTransaction()
                .replace(R.id.advicesFrame, advices).commit();
        */

        kda = KdaFragment.newInstance();
        fragmentManager.beginTransaction()
                .replace(R.id.kdaFrame, kda).commit();
    }

    private void findMatch(long id, String region){
        MatchApiUtils utils = new MatchApiUtils(getApplicationContext());
        utils.getMatchInfo(id, region);
    }

    @Subscribe
    public void getMatchResponse(MatchDetail detail){

        ((LinearLayout) findViewById(R.id.matchProgressBar)).setVisibility(View.GONE);
        ((ScrollView) findViewById(R.id.matchInfoCard)).setVisibility(View.VISIBLE);

        Toast.makeText(this, "SYNERGY: "+ detail.getMatchId(), Toast.LENGTH_LONG).show();

        championCard = (ImageView) findViewById(R.id.card_image);
        //String url = RIOT.DRAGON_URL + RIOT.SPLASH_ARTS_URL + champion.getKey() + "_0.jpg";

        this.isRegistered =false;
        BusProvider.unRegister(this);
        BusProvider.post(new MatchInfoStartFraments(detail.getMatchId(), true));
    }

    @Subscribe
    public void getApiErrror(RiotApiError error){
        this.isRegistered =false;
        BusProvider.unRegister(this);
        Log.i("SYNERGY: error api", error.getMessage());
        Toast.makeText(this, "SYNERGY: ocurrio un error", Toast.LENGTH_LONG).show();
    }

    public long getMatchId() {
        return matchId;
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }

    @Override
    public void onBackPressed() {
        BusProvider.unRegisterAllListeners();
        super.onBackPressed();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        BusProvider.unRegisterAllListeners();
        Intent intent = new Intent(this, LoadingScreen.class);
        startActivity(intent);
        this.finish();
    }
}
