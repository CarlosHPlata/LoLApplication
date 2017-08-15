package com.kingskull.lolapplication.api.restfull.connections.responses;

import com.kingskull.lolapplication.models.pojos.item.ItemDto;

import java.util.Map;

/**
 * Created by Carlos on 07/02/2016.
 */
public class ItemsReadyToRead {

    private boolean ready;
    private Map<String, ItemDto> items;

    public ItemsReadyToRead(boolean ready, Map<String, ItemDto> items) {
        this.ready = ready;
        this.items = items;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public Map<String, ItemDto> getItems() {
        return items;
    }

    public void setItems(Map<String, ItemDto> items) {
        this.items = items;
    }
}
