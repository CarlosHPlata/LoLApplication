package com.kingskull.lolapplication.api.restfull.Utils;

import android.content.Context;

import com.kingskull.lolapplication.api.observer.BusProvider;
import com.kingskull.lolapplication.api.restfull.connections.StaticInfo;
import com.kingskull.lolapplication.api.restfull.connections.responses.MatchResponse;
import com.kingskull.lolapplication.controllers.utils.SessionManager;
import com.kingskull.lolapplication.models.pojos.matchinfo.MatchDetail;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

/**
 * Created by cherrera on 2/2/2016.
 */
public class MatchApiUtils {

    private Context context;

    public MatchApiUtils(Context context){
        this.context = context;
    }

    public void getMatchInfo(long id, String region){
        MatchDetail matchDetail = getMatchFromMap(id);

        if (matchDetail != null){
            BusProvider.post(matchDetail);
        } else {
            getMatchFromApi(id, region);
        }
    }

    private MatchDetail getMatchFromMap(long id) {

        StaticInfo info = StaticInfo.getInstance();

        if (info.getMatchs().containsKey(id)){
            return info.getMatchs().get(id);
        } else {
            return null;
        }
    }

    private void getMatchFromApi(long id, String region) {
        //String region = SessionManager.getSession(context).getRegion();
        BusProvider.register(this);
        MatchApiCall matchApiCall = new MatchApiCall(region);
        matchApiCall.getMatchInfo(id, region);
    }

    @Subscribe
    public void getMatchFromApiResponse(MatchResponse matchResponse){
        MatchDetail matchDetail = matchResponse.getMatchDetail();
        StaticInfo.getInstance().getMatchs().put(matchDetail.getMatchId(), matchDetail);

        BusProvider.post( matchDetail );
    }

}
