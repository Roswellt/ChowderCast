package com.example.quang_tri.chowdercast;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Quang-Tri on 25/02/2017.
 */

public class Ad extends RealmObject {

    @PrimaryKey
    String hostname;

    public Ad(){}

    public Ad(String hostname){
        this.hostname = hostname;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }
}
