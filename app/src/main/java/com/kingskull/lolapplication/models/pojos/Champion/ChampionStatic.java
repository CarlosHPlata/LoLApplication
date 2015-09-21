package com.kingskull.lolapplication.models.pojos.Champion;

import java.util.Map;

/**
 * Created by Usuario on 20/09/2015.
 */
public class ChampionStatic {
    private Map<Integer, Champion> data;
    private String format;
    private Map<String, String> keys;
    private String type;
    private String version;

    public Map<Integer, Champion> getData() {
        return data;
    }

    public void setData(Map<Integer, Champion> data) {
        this.data = data;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Map<String, String> getKeys() {
        return keys;
    }

    public void setKeys(Map<String, String> keys) {
        this.keys = keys;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
