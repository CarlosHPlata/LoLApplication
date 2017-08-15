package com.kingskull.lolapplication.api.restfull.Utils;

import android.content.Context;

import com.kingskull.lolapplication.api.observer.BusProvider;
import com.kingskull.lolapplication.api.restfull.connections.StaticInfo;
import com.kingskull.lolapplication.api.restfull.connections.responses.ItemsReadyToRead;
import com.kingskull.lolapplication.api.restfull.connections.responses.ItemsResponse;
import com.kingskull.lolapplication.controllers.utils.SessionManager;
import com.kingskull.lolapplication.models.pojos.item.ItemDto;
import com.kingskull.lolapplication.models.pojos.item.ItemListDto;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.Map;

/**
 * Created by Carlos on 06/02/2016.
 */
public class ItemApiUtils {

    private Context context;

    public ItemApiUtils(Context context){
        this.context = context;
    }

    public void getAllItems(String region, String version){
        getAllItemsFromApi(region, version);
    }

    private void getAllItemsFromApi(String region, String version){
        //String region = SessionManager.getSession(context).getRegion();
        BusProvider.register(this);
        ItemApiCall itemApiCall = new ItemApiCall(region);
        itemApiCall.getItemInfo("gold", version, region);
    }

    @Subscribe
    public void getAllItemsFromApiResponse(ItemsResponse response){
        ItemListDto itemListDto = response.getItemList();
        Map<String, ItemDto> items = itemListDto.getData();
        StaticInfo.getInstance().setItems(items);
        BusProvider.unRegister(this);
        BusProvider.post(new ItemsReadyToRead(true, items));
    }

}
