package com.kingskull.lolapplication.api.observer;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Created by Usuario on 07/09/2015.
 */
public final class BusProvider {
    /**
     * Maintains a singleton instance for obtaining the bus. Ideally this would be replaced with a more efficient means
     * such as through injection directly into interested classes.
     */

    private static Bus BUS;

    private BusProvider(){}

    public synchronized static Bus getInstance(){
        if (BUS == null){
            BUS = new MainBus();
        }
        return BUS;
    }

    public static void post(Object message){
        Bus instance = getInstance();
        instance.post(message);
    }

    public static void register(Object listener){
        Bus instance = getInstance();
        instance.register(listener);
    }

    public static void unRegister(Object listener){
        Bus instance = getInstance();
        instance.unregister(listener);
    }

}


