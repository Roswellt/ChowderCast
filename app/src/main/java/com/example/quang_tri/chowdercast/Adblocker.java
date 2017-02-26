package com.example.quang_tri.chowdercast;


import android.net.Uri;
import android.text.TextUtils;
import android.webkit.WebResourceResponse;

import java.io.ByteArrayInputStream;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Quang-Tri on 26/02/2017.
 */

public class Adblocker {

    public static class AdBlocker {

        public static boolean isAd(String url, Realm realm) {
            Uri httpUrl = Uri.parse(url);
            return isAdHost(httpUrl != null ? httpUrl.getHost() : "", realm);
        }

        private static boolean isAdHost(String host, Realm realm) {
            if (TextUtils.isEmpty(host)) {
                return false;
            }
            int index = host.indexOf(".");
            RealmResults results = realm.where(Ad.class).equalTo("hostname", host).findAll();
            return index >= 0 && (results.size()!=0) ||
                    index + 1 < host.length() && isAdHost(host.substring(index + 1), realm);
        }

        public static WebResourceResponse createEmptyResource() {
            return new WebResourceResponse("text/plain", "utf-8", new ByteArrayInputStream("".getBytes()));
        }
    }
}
