package com.kingskull.lolapplication.views.summoner.data.activities;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kingskull.lolapplication.R;
import com.kingskull.lolapplication.api.observer.BusProvider;
import com.kingskull.lolapplication.api.restfull.connections.Errors.RiotApiError;
import com.kingskull.lolapplication.api.restfull.connections.responses.DragonVersionCached;
import com.kingskull.lolapplication.api.restfull.connections.responses.ListOfVersionsReady;
import com.kingskull.lolapplication.controllers.MainLoadController;
import com.kingskull.lolapplication.controllers.SummonerListController;
import com.kingskull.lolapplication.controllers.utils.SessionManager;
import com.kingskull.lolapplication.models.pojos.Summoner;
import com.kingskull.lolapplication.controllers.SummonerController;
import com.kingskull.lolapplication.models.preferences.SessionPreference;
import com.kingskull.lolapplication.views.loading.screen.activities.LoadingScreen;
import com.kingskull.lolapplication.views.summoner.data.widgets.CustomPagerAdapter;
import com.kingskull.lolapplication.views.summoner.data.widgets.SlidingTabLayout;
import com.kingskull.lolapplication.views.syncronize.summoner.activities.SummonerList;
import com.squareup.otto.Subscribe;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {
    private Summoner summoner;

    private SlidingTabLayout tabs;
    private ViewPager pager;
    private LinearLayout linlaHeaderProgress;
    private LinearLayout content;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        logoutButton = (Button) findViewById(R.id.logout);
        logoutButton.setOnClickListener(this);

        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        pager = (ViewPager) findViewById(R.id.pager);

        linlaHeaderProgress = (LinearLayout) findViewById(R.id.inlaHeaderProgress);
        content = (LinearLayout) findViewById(R.id.content_summoner);

        init();
    }

    public void init(){

        linlaHeaderProgress.setVisibility(View.VISIBLE);
        content.setVisibility(View.GONE);

        //Once you register to bus, this class can get responses from bus
        BusProvider.register(this);

        //Get dragon service
        getDragonVersion();

    }

    public void getDragonVersion(){
        MainLoadController loadController = new MainLoadController(this);

        SessionPreference session = SessionManager.getSession( getApplicationContext() );

        loadController.getActiveDragonVersion(session.getRegion().toLowerCase());
    }

    @Subscribe
    public void getDragonVersionsResponse(DragonVersionCached response){
        getAllDragonVersions();
    }

    public void getAllDragonVersions(){
        MainLoadController loadController = new MainLoadController(this);

        SessionPreference session = SessionManager.getSession( getApplicationContext() );

        loadController.getListOfDragonVersion(session.getRegion().toLowerCase());
    }

    @Subscribe
    public void getAllDragonVersionsResponse(ListOfVersionsReady response) {
        //getting data
          getSummonnerData();
    }

    public void finishActivity(){
        finish();
    }

    public void setTabsLayout(){
        pager.setAdapter(new CustomPagerAdapter(getSupportFragmentManager(), this));

        tabs.setDistributeEvenly(true);
        tabs.setCustomTabView(R.layout.custo_tab_view, R.id.tabText);

        tabs.setBackground( getResources().getDrawable(R.drawable.title) );

        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.golden_text);
            }
        });

        pager.setCurrentItem(1);

        tabs.setViewPager(pager);
    }


    public void getSummonnerData(){
        SummonerController controller = new SummonerController(this);
        controller.getSummonnerStat( getSummonerOnSession() );
    }

    public long getSummonerOnSession(){
        SessionPreference session = SessionManager.getSession( getApplicationContext() );
        return session.getId();
    }

    //expecting bus response with type Summoner
    @Subscribe
    public void getSummonnerData(Summoner summoners){
        this.summoner = summoners;

        BusProvider.unRegister(this);


        //set tabs
        setTabsLayout();

        fillVars();

        linlaHeaderProgress.setVisibility(View.GONE);
        content.setVisibility(View.VISIBLE);
    }

    //expecting bus response with type error
    @Subscribe
    public void getError(RiotApiError error){
        BusProvider.unRegister(this);
        Log.i("error api", error.getMessage());
        Toast.makeText(this, "ocurrio un error", Toast.LENGTH_LONG).show();
    }

    public void fillVars(){
        //filling title
        ((TextView) findViewById(R.id.summoner_name)).setText(this.summoner.getName().toUpperCase());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public void onClick(View view) {
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        sessionManager.logOut();

        Intent intent = new Intent(this, SummonerList.class);
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        BusProvider.unRegisterAllListeners();
        Intent intent = new Intent(this, LoadingScreen.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void onBackPressed() {
        BusProvider.unRegisterAllListeners();
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        sessionManager.logOut();

        Intent intent = new Intent(this, SummonerList.class);
        startActivity(intent);
        finish();
    }
}
