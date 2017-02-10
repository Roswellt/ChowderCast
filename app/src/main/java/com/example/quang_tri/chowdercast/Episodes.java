package com.example.quang_tri.chowdercast;

import io.realm.RealmObject;

/**
 * Created by Quang-Tri on 09/02/2017.
 */

public class Episodes extends RealmObject{

    private String link;
    private String season;
    private String epNum;

    public Episodes(){}

    public Episodes(String link){
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getEpNum() {
        return epNum;
    }

    public void setEpNum(String epNum) {
        this.epNum = epNum;
    }
}
