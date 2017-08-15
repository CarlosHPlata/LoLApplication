package com.kingskull.lolapplication.api.observer;

import android.util.Log;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Usuario on 07/09/2015.
 */
public final class BusProvider {
    /**
     * Maintains a singleton instance for obtaining the bus. Ideally this would be replaced with a more efficient means
     * such as through injection directly into interested classes.
     */
    private static Map<String, Object> registerItems;

    private static Bus BUS;

    private BusProvider(){}

    public synchronized static Bus getInstance(){
        if (BUS == null){
            BUS = new MainBus(ThreadEnforcer.ANY);
            registerItems = new HashMap<>();
        }
        return BUS;
    }

    public static void post(Object message){
        Bus instance = getInstance();
        instance.post(message);
    }

    public static void register(Object listener){
        Bus instance = getInstance();

        String name = listener.getClass().getSimpleName();
        if (registerItems.get(name) == null){
            registerItems.put(name, listener);
            instance.register(listener);
        }
    }

    public static void unRegister(Object listener){
        Bus instance = getInstance();

        String name = listener.getClass().getSimpleName();
        if (registerItems.get(name) != null){
            registerItems.remove(name);
        }

        instance.unregister(listener);
    }

    public static void unRegisterAllListeners(){
        Bus instance = getInstance();

        Iterator it = registerItems.entrySet().iterator();

        while (it.hasNext()){
            Map.Entry pair = (Map.Entry) it.next();

            Object listener = pair.getValue();
            instance.unregister(listener);

            it.remove();
        }
    }

}


