package com.kingskull.lolapplication.models.pojos.item;

import java.util.List;

/**
 * Created by Carlos on 06/02/2016.
 */
public class ItemTreeDto {

    private String header;
    private List<String> tags;

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
