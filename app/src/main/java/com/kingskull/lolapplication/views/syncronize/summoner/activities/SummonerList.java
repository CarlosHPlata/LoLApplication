package com.kingskull.lolapplication.views.syncronize.summoner.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.github.brnunes.swipeablerecyclerview.SwipeableRecyclerViewTouchListener;
import com.kingskull.lolapplication.R;
import com.kingskull.lolapplication.api.observer.BusProvider;
import com.kingskull.lolapplication.controllers.SummonerListController;
import com.kingskull.lolapplication.controllers.utils.SessionManager;
import com.kingskull.lolapplication.models.database.entities.SummonerEntitie;
import com.kingskull.lolapplication.views.loading.screen.activities.LoadingScreen;
import com.kingskull.lolapplication.views.summoner.data.activities.MainActivity;
import com.kingskull.lolapplication.views.syncronize.summoner.widgets.SummonerAdapter;

import java.util.List;

public class SummonerList extends ActionBarActivity {

    private RecyclerView recycler;

    private SummonerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summoner_list);

        searchForSession();

        recycler = (RecyclerView) findViewById(R.id.summonersList);
        adapter = new SummonerAdapter(this, getData());

        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        adapter.setOnItemCLickListener(new SummonerAdapter.ItemCLickListener() {
            @Override
            public void onItemClick(View v, SummonerEntitie summoner) {
                SessionManager sessionManager = new SessionManager(getApplicationContext());
                sessionManager.logIn(summoner.getId(), summoner.getRegion());

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        setSwipeEffect();


        Button syncButton = (Button) findViewById(R.id.sync_button);
        syncButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getApplicationContext(), Syncronize.class);
                startActivity(myIntent);
                finish();
            }
        });
    }

    private void setSwipeEffect() {
        SwipeableRecyclerViewTouchListener swipeTouchListener =
                new SwipeableRecyclerViewTouchListener(
                        recycler,
                        new SwipeableRecyclerViewTouchListener.SwipeListener(){

                            @Override
                            public boolean canSwipe(int i) {
                                return true;
                            }

                            @Override
                            public void onDismissedBySwipeLeft(RecyclerView recyclerView, int[] ints) {
                                for ( int position : ints ){
                                    //adapter.deleteItem(position);
                                    showConfirmationDialog(position);
                                }
                            }

                            @Override
                            public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] ints) {
                                for ( int position : ints ){
                                    //adapter.deleteItem(position);
                                    showConfirmationDialog(position);
                                }
                            }
                        }
                );
        recycler.addOnItemTouchListener(swipeTouchListener);
    }

    private void showConfirmationDialog(final int position){
        new AlertDialog.Builder(this)
                .setTitle( getResources().getString(R.string.dialog_header) )
                .setMessage( getResources().getString(R.string.dialog_body) )
                .setPositiveButton( getResources().getString(R.string.yes_msg), new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SummonerListController controller = new SummonerListController(getApplicationContext());
                        controller.deleteSummoner( adapter.getSummoners().get(position) );

                        adapter.deleteItem(position);
                    }

                })
                .setNegativeButton( getResources().getString(R.string.no_msg), null)
                .show();
    }

    private void searchForSession() {
        SessionManager sessionManager = new SessionManager(getApplicationContext());

        if (sessionManager.checkSession()){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
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

    @Override
    protected void onRestart() {
        super.onRestart();
        BusProvider.unRegisterAllListeners();
        Intent intent = new Intent(this, LoadingScreen.class);
        startActivity(intent);
        this.finish();
    }
}
