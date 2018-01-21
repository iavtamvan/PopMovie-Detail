package com.iavariav.root.popmovie;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;

/**
 * Created by root on 1/21/18.
 */

public class MyApplication extends Application{
    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        Stetho.initializeWithDefaults(this);
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(
                                Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(
                                Stetho.defaultInspectorModulesProvider(this))
                        .build());
    }

    public static Context getContext(){
        return mContext;
    }
}
