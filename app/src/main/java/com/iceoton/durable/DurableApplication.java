package com.iceoton.durable;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

public class DurableApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
