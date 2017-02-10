package com.example.quang_tri.chowdercast;

import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Quang-Tri on 09/02/2017.
 */

public class linkToSrcAsync extends AsyncTask<String, Void, ArrayList<String>> {

    private Document doc;
    private ArrayList<String> linkToSrc;

    @Override
    protected ArrayList<String> doInBackground(String... params) {
        for(String x : params){
            try {
                doc = Jsoup.connect(x).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        linkToSrc = new ArrayList<>();
        Elements episodes = doc.getElementsByTag("li");

        for(Element links : episodes){
            linkToSrc.add(links.attr("abs:href"));
        }

        return linkToSrc;
    }
}
