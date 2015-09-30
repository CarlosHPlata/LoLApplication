package com.kingskull.lolapplication.views.syncronize.summoner.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.kingskull.lolapplication.R;
import com.kingskull.lolapplication.controllers.SummonerListController;
import com.kingskull.lolapplication.models.database.entities.SummonerEntitie;
import com.kingskull.lolapplication.views.syncronize.summoner.widgets.SummonerAdapter;

import java.util.List;

public class SummonerList extends ActionBarActivity {

    private RecyclerView recycler;

    private SummonerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summoner_list);
        recycler = (RecyclerView) findViewById(R.id.summonersList);
        adapter = new SummonerAdapter(this, getData());

        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        adapter.setOnItemCLickListener(new SummonerAdapter.ItemCLickListener(){
            @Override
            public void onItemClick(View v, SummonerEntitie summoner) {
                Toast.makeText(getApplicationContext(), summoner.getId()+"", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public List<SummonerEntitie> getData(){
        SummonerListController controller = new SummonerListController(this);
        return controller.summonersSaved();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_summoner_list, menu);
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
}
