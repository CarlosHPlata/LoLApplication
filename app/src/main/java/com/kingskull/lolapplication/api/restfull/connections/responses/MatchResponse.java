package com.kingskull.lolapplication.api.restfull.connections.responses;

import com.kingskull.lolapplication.models.pojos.matchinfo.MatchDetail;

/**
 * Created by cherrera on 2/2/2016.
 */
public class MatchResponse {

    private MatchDetail matchDetail;

    public MatchResponse(MatchDetail matchDetail) {
        this.matchDetail = matchDetail;
    }

    public MatchDetail getMatchDetail() {
        return matchDetail;
    }

    public void setMatchDetail(MatchDetail matchDetail) {
        this.matchDetail = matchDetail;
    }
}
