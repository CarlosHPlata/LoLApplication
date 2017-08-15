package com.kingskull.lolapplication.views.summoner.data.fragments.matchinfofragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kingskull.lolapplication.R;
import com.kingskull.lolapplication.api.observer.BusProvider;
import com.kingskull.lolapplication.api.restfull.Utils.ItemApiUtils;
import com.kingskull.lolapplication.api.restfull.connections.RIOT;
import com.kingskull.lolapplication.api.restfull.connections.StaticInfo;
import com.kingskull.lolapplication.api.restfull.connections.responses.ItemsReadyToRead;
import com.kingskull.lolapplication.api.restfull.connections.responses.MatchInfoStartFraments;
import com.kingskull.lolapplication.controllers.utils.DragonVersionsHandler;
import com.kingskull.lolapplication.controllers.utils.SessionManager;
import com.kingskull.lolapplication.models.pojos.item.ItemDto;
import com.kingskull.lolapplication.models.pojos.matchinfo.MatchDetail;
import com.kingskull.lolapplication.models.pojos.matchinfo.Participant;
import com.kingskull.lolapplication.models.pojos.matchinfo.ParticipantIdentity;
import com.kingskull.lolapplication.models.pojos.matchinfo.ParticipantStats;
import com.kingskull.lolapplication.views.summoner.data.activities.MatchInfo;
import com.koushikdutta.ion.Ion;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Items extends Fragment {

    private View fragment;

    private boolean isRegistered = false;

    private long matchId;

    private ImageView item1, item2, item3, item4, item5, item6, item7;
    private TextView buildcost;

    private MatchDetail matchDetail;
    private List<Long> itemsTemp;

    public static Items newInstance() {
        Items fragment = new Items();
        return fragment;
    }

    public Items() {

        if (!this.isRegistered){
            isRegistered = true;
            BusProvider.register(this);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.fragment = inflater.inflate(R.layout.fragment_items, container, false);

        this.item1 = (ImageView) fragment.findViewById(R.id.item1);
        this.item2 = (ImageView) fragment.findViewById(R.id.item2);
        this.item3 = (ImageView) fragment.findViewById(R.id.item3);
        this.item4 = (ImageView) fragment.findViewById(R.id.item4);
        this.item5 = (ImageView) fragment.findViewById(R.id.item5);
        this.item6 = (ImageView) fragment.findViewById(R.id.item6);
        this.item7 = (ImageView) fragment.findViewById(R.id.item7);

        this.buildcost = (TextView) fragment.findViewById(R.id.buildcost);
        this.buildcost.setText("CALCULATING...");

        return fragment;
    }

    @Subscribe
    public void readyToLoad(MatchInfoStartFraments ready){
        this.matchDetail = StaticInfo.getInstance().getMatchs().get(ready.getIdMatch());

        long idPlayer = SessionManager.getSession(getActivity().getApplicationContext()).getId();

        int idParticipant = findParticipantIdByPlayerId(idPlayer);

        this.itemsTemp = getItmes(idParticipant);

        setItemImages();

        findItems();
    }

    private void findItems() {
        ItemApiUtils utils = new ItemApiUtils(getActivity().getApplicationContext());
        DragonVersionsHandler versionsHandler = new DragonVersionsHandler();

        utils.getAllItems(
                SessionManager.getSession(getActivity().getApplicationContext()).getRegion(),
                versionsHandler.getClosestVersion(matchDetail.getMatchVersion())
        );
    }

    private int findParticipantIdByPlayerId(long id){
        int player = -1;

        for (ParticipantIdentity identity : matchDetail.getParticipantIdentities()){
            if (identity.getPlayer().getSummonerId() == id){
                player = identity.getParticipantId();
                break;
            }
        }

        return player;
    }

    private Participant findParticipant(int idPlayer){
        Participant result = null;
        for (Participant participant : matchDetail.getParticipants()){
            if (participant.getParticipantId() == idPlayer){
                result = participant;
            }
        }

        return result;
    }

    private List<Long> getItmes(int idParticipant){
        ParticipantStats stats = findParticipant(idParticipant).getStats();

        List<Long> items = new ArrayList<>();

        items.add(stats.getItem0());
        items.add(stats.getItem1());
        items.add(stats.getItem2());
        items.add(stats.getItem3());
        items.add(stats.getItem4());
        items.add(stats.getItem5());
        items.add(stats.getItem6());

        return items;
    }

    private int getIdItem(long idItemApp){
        int id = -1;
        for (int i = 0; i < itemsTemp.size(); i++){
            if ( itemsTemp.get(i) ==  idItemApp){
                id = i;
                break;
            }
        }

        return id;
    }

    private ImageView getItemImage(long idItemApp){
        int id = getIdItem(idItemApp);
        ImageView item = null;

        switch (id){
            case 0:
                item = item1;
                break;
            case 1:
                item = item2;
                break;
            case 2:
                item = item3;
                break;
            case 3:
                item = item4;
                break;
            case 4:
                item = item5;
                break;
            case 5:
                item = item6;
                break;
            case 6:
                item = item7;
                break;
            default:
                item = item1;
        }

        return item;
    }

    private void setItemImages(){

        for (long item : this.itemsTemp){

            ImageView itemImage = getItemImage(item);

            String url = new DragonVersionsHandler().urlBuilder(RIOT.ITEMS_IMAGES) + item + ".png";

            Ion.with(itemImage)
                    .placeholder(getActivity().getResources().getDrawable(R.drawable.itemdummie))
                    .error(getActivity().getResources().getDrawable(R.drawable.itemdummie))
                    .load(url);
        }

    }

    @Subscribe
    public void setBuildCost(ItemsReadyToRead ready){
        this.isRegistered = false;
        BusProvider.unRegister(this);
        int cost = 0;

        Map<String, ItemDto> items = StaticInfo.getInstance().getItems();

        for (long id : itemsTemp){
            String idTxt = String.valueOf(id);
            ItemDto item = items.get(idTxt);

            if (item != null)
                cost += item.getGold().getTotal();
        }

        this.buildcost.setText(cost +" G");
    }

}
