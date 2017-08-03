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

        private static Realm realm = Realm.getDefaultInstance();

        public static boolean isAd(String url) {
            Uri httpUrl = Uri.parse(url);
            return isAdHost(httpUrl != null ? httpUrl.getHost() : "");
        }

        private static boolean isAdHost(String host) {
            if (TextUtils.isEmpty(host)) {
                return false;
            }
            Ad results = realm.where(Ad.class).equalTo("hostname", host).findFirst();
            int index = host.indexOf(".");
            return index >= 0 && (results!=null ||
                    index + 1 < host.length() && isAdHost(host.substring(index + 1)));
        }

        public static WebResourceResponse createEmptyResource() {
            return new WebResourceResponse("text/plain", "utf-8", new ByteArrayInputStream("".getBytes()));
        }
    }
}
