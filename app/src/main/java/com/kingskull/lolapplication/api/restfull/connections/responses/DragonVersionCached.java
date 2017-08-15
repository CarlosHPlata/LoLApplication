package com.kingskull.lolapplication.api.restfull.connections.responses;

import com.kingskull.lolapplication.models.pojos.DragonVersion.Realm;

/**
 * Created by Carlos on 07/05/2016.
 */
public class DragonVersionCached {

    private Realm dragonVersion;

    public DragonVersionCached(Realm dragonVersion) {
        this.dragonVersion = dragonVersion;
    }

    public Realm getDragonVersion() {
        return dragonVersion;
    }

    public void setDragonVersion(Realm dragonVersion) {
        this.dragonVersion = dragonVersion;
    }
}
