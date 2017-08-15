package com.kingskull.lolapplication.api.restfull.connections.responses;

import com.kingskull.lolapplication.models.pojos.item.ItemDto;
import com.kingskull.lolapplication.models.pojos.item.ItemListDto;

/**
 * Created by Carlos on 06/02/2016.
 */
public class ItemsResponse {

    private ItemListDto itemList;
    private ItemDto item;

    public ItemsResponse(ItemDto item){
        this.item = item;
    }

    public ItemsResponse(ItemListDto itemList){
        this.itemList = itemList;
    }

    public ItemListDto getItemList() {
        return itemList;
    }

    public void setItemList(ItemListDto itemList) {
        this.itemList = itemList;
    }

    public ItemDto getItem() {
        return item;
    }

    public void setItem(ItemDto item) {
        this.item = item;
    }
}
