package com.example.quang_tri.chowdercast;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by Quang-Tri on 09/02/2017.
 */

public class JsoupAsync extends AsyncTask<String, Void, Episodes[]> {

    private Document doc;

    @Override
    protected Episodes[] doInBackground(String... params) {


        for(String x : params){
            try {
                doc = Jsoup.connect(x).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
