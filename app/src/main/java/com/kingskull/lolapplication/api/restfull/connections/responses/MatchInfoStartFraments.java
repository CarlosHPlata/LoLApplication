package com.kingskull.lolapplication.api.restfull.connections.responses;

/**
 * Created by Carlos on 02/02/2016.
 */
public class MatchInfoStartFraments {

    private long idMatch;
    private boolean initialize;

    public MatchInfoStartFraments(long idMatch, boolean initialize) {
        this.idMatch = idMatch;
        this.initialize = initialize;
    }

    public long getIdMatch() {
        return idMatch;
    }

    public void setIdMatch(long idMatch) {
        this.idMatch = idMatch;
    }

    public boolean isInitialize() {
        return initialize;
    }

    public void setInitialize(boolean initialize) {
        this.initialize = initialize;
    }
}
