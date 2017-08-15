package com.kingskull.lolapplication.views.syncronize.summoner.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.kingskull.lolapplication.R;
import com.kingskull.lolapplication.models.pojos.Summoner;
import com.kingskull.lolapplication.views.syncronize.summoner.fragments.CodeRunes;
import com.kingskull.lolapplication.views.syncronize.summoner.fragments.SearchSummoner;
import com.kingskull.lolapplication.views.syncronize.summoner.fragments.Success;

import java.util.Map;

public class Syncronize extends ActionBarActivity {

    public static final int SEARCH_SUMMONER = 1, CODE_RUNES = 2, SUCCES = 3, FINALLY = 4;

    Fragment container;
    LinearLayout linlaHeaderProgress;
    FrameLayout fragmentContainer;
    private int currentPosition;
    private Map<String, Object> params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syncronize);

        linlaHeaderProgress = (LinearLayout) findViewById(R.id.inlaHeaderProgress);
        fragmentContainer = (FrameLayout) findViewById(R.id.frame_container);

        if (savedInstanceState == null) {
            currentPosition = SEARCH_SUMMONER;
            container = SearchSummoner.newInstance();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, container).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_syncronize, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void replaceFragment(int id, Map<String, Object> params){
        this.params = params;
        switch (id){
            case SEARCH_SUMMONER:
                break;
            case CODE_RUNES:
                Summoner summonerName = (Summoner) params.get("summonerName");
                String region = (String) params.get("region");
                container = CodeRunes.newInstance(summonerName, region);
                break;
            case SUCCES:
                Summoner summoner = (Summoner) params.get("summoner");
                region = (String) params.get("region");
                container = Success.newInstance(summoner, region);
                break;
            case FINALLY:
                Intent intent = new Intent(this, SummonerList.class);
                startActivity(intent);
                finish();
                break;
        }

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_container, container).commit();
    }

    public void showProgressBar(){
        fragmentContainer.setVisibility(View.GONE);
        linlaHeaderProgress.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar(){
        linlaHeaderProgress.setVisibility(View.GONE);
        fragmentContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        Intent intent;
        switch (currentPosition){
            case SEARCH_SUMMONER:
                intent = new Intent(this, SummonerList.class);
                startActivity(intent);
                finish();
                break;
            case CODE_RUNES:
                container = SearchSummoner.newInstance();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, container).commit();
                break;
            case SUCCES:
                intent = new Intent(this, SummonerList.class);
                startActivity(intent);
                finish();
                break;
            case FINALLY:
                intent = new Intent(this, SummonerList.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
