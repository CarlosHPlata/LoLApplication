package com.kingskull.lolapplication.summoner.data.views.activities;

import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kingskull.lolapplication.R;
import com.kingskull.lolapplication.api.observer.BusProvider;
import com.kingskull.lolapplication.api.restfull.connections.Errors.RiotApiError;
import com.kingskull.lolapplication.models.pojos.Summoner;
import com.kingskull.lolapplication.controllers.SummonerController;
import com.kingskull.lolapplication.summoner.data.views.widgets.CustomPagerAdapter;
import com.kingskull.lolapplication.summoner.data.views.widgets.SlidingTabLayout;
import com.squareup.otto.Subscribe;


public class MainActivity extends ActionBarActivity {
    private Summoner summoner;

    private SlidingTabLayout tabs;
    private ViewPager pager;
    private LinearLayout linlaHeaderProgress;
    private LinearLayout content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        pager = (ViewPager) findViewById(R.id.pager);

        linlaHeaderProgress = (LinearLayout) findViewById(R.id.inlaHeaderProgress);
        content = (LinearLayout) findViewById(R.id.content_summoner);
        //Once you register to bus, this class can get responses from bus
        BusProvider.register(this);
        init();
    }

    public void init(){

        linlaHeaderProgress.setVisibility(View.VISIBLE);
        content.setVisibility(View.GONE);

        //getting data
        getSummonnerData();

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
        controller.getSummonnerStat(2033911);
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
    protected void onResume() {
        super.onResume();
        init();
    }
}
