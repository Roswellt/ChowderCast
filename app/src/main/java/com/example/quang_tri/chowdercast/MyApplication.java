package com.example.quang_tri.chowdercast;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Quang-Tri on 04/02/2017.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().initialData(new Realm.Transaction(){
            @Override
            public void execute(Realm realm) {

                BufferedReader reader = null;
                try{
                    reader = new BufferedReader(
                            new InputStreamReader(getResources().openRawResource(R.raw.adblock))
                    );
                    String line;
                    while((line = reader.readLine()) != null){
                        Ad entry = new Ad(line);
                        realm.copyToRealm(entry);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if(reader != null){
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(config);
    }
}
