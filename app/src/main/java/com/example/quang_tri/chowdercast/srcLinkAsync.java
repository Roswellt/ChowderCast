package com.example.quang_tri.chowdercast;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Quang-Tri on 09/02/2017.
 */

public class srcLinkAsync extends AsyncTask<ArrayList<String>, Void, ArrayList<String>> {

    private Document doc;
    private ArrayList<String> linkToSrc;
    private ArrayList<String> srcLink;

    @Override
    protected ArrayList<String> doInBackground(ArrayList<String>... params) {
        linkToSrc = params[0];
        Iterator<String> iter = linkToSrc.iterator();

        while(iter.hasNext()){
            String currentLink = iter.next();
            try {
                doc = Jsoup.connect(currentLink).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Element iframe = doc.select("div iframe").first();
            srcLink.add(iframe.attr("src"));
        }

        return srcLink;
    }
}
