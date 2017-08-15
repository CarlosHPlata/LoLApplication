package com.kingskull.lolapplication.api.observer;

import android.os.Handler;
import android.os.Looper;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;


/**
 * Created by Usuario on 12/09/2015.
 */
public class MainBus extends Bus {
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    public MainBus(ThreadEnforcer enforcer) {
        super(enforcer, DEFAULT_IDENTIFIER);
    }

    @Override
    public void post(final Object event) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            super.post(event);
        } else {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    MainBus.super.post(event);
                }
            });
        }
    }
}
